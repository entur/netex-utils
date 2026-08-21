package no.entur.abt.netex.id.predicate;

/*-
 * #%L
 * netex-utils
 * %%
 * Copyright (C) 2019 - 2020 Entur
 * %%
 * Licensed under the EUPL, Version 1.1 or – as soon they will be
 * approved by the European Commission - subsequent versions of the
 * EUPL (the "Licence");
 *
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 *
 * http://ec.europa.eu/idabc/eupl5
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and
 * limitations under the Licence.
 * #L%
 */

import jdk.incubator.vector.ByteVector;
import jdk.incubator.vector.VectorMask;
import jdk.incubator.vector.VectorOperators;
import jdk.incubator.vector.VectorSpecies;
import no.entur.abt.netex.id.DefaultNetexIdValidator;
import no.entur.abt.netex.utils.IllegalNetexIDException;

/**
 * SIMD-accelerated predicate for codespace + type (does not validate the value part).
 *
 * <p>Uses the Java Vector API to compare the {@code CODESPACE:TYPE:} prefix against the input in
 * a single vectorised operation when the prefix fits within one SIMD vector lane.</p>
 *
 * <p>Use {@link no.entur.abt.netex.id.predicate.NetexIdPredicateBuilder} to obtain an instance.
 * When the prefix is longer than the preferred vector width, or when the input contains non-ASCII
 * characters, falls back to scalar comparison.</p>
 */
public class SimdNetexIdCodespaceTypePredicate implements NetexIdPredicate {

	private static final VectorSpecies<Byte> SPECIES = ByteVector.SPECIES_PREFERRED;

	/** The prefix bytes for {@code CODESPACE:TYPE:}, zero-padded to the vector length. */
	private final byte[] prefixPadded;

	/** The actual number of meaningful bytes in {@code prefixPadded} ({@code codespace + ":" + type + ":"}). */
	private final int prefixLength;

	/** Whether the prefix fits within a single SIMD vector (fast path). */
	private final boolean simdFastPath;

	/** Scalar fallback for long prefixes. */
	private final char[] prefixChars;

	public SimdNetexIdCodespaceTypePredicate(CharSequence codespace, CharSequence type) {
		if (codespace == null || codespace.length() != DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH) {
			throw new IllegalNetexIDException("'" + codespace + "' is not a valid codespace");
		}
		if (type == null || type.length() == 0) {
			throw new IllegalNetexIDException("'" + type + "' is not a valid type");
		}

		// prefix = "CODESPACE:TYPE:"
		prefixLength = DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH + 1 + type.length() + 1;
		prefixChars = new char[prefixLength];
		for (int i = 0; i < codespace.length(); i++) {
			prefixChars[i] = codespace.charAt(i);
		}
		prefixChars[DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH] = DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR;
		for (int i = 0; i < type.length(); i++) {
			prefixChars[DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH + 1 + i] = type.charAt(i);
		}
		prefixChars[prefixLength - 1] = DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR;

		simdFastPath = prefixLength <= SPECIES.length();
		if (simdFastPath) {
			prefixPadded = new byte[SPECIES.length()];
			for (int i = 0; i < prefixLength; i++) {
				prefixPadded[i] = (byte) prefixChars[i];
			}
			// remaining bytes stay 0 – they are masked out by the lane count mask below
		} else {
			prefixPadded = null;
		}
	}

	@Override
	public boolean test(CharSequence t) {
		if (t == null) {
			return false;
		}
		if (t.length() < prefixLength) {
			return false;
		}
		if (simdFastPath && t instanceof String) {
			return testSimd((String) t);
		}
		return testScalar(t);
	}

	private boolean testSimd(String t) {
		// Extract the first prefixLength chars as bytes; bail out if any is non-ASCII.
		byte[] inputBytes = new byte[SPECIES.length()];
		for (int i = 0; i < prefixLength; i++) {
			char c = t.charAt(i);
			if (c > 127) {
				return testScalar(t);
			}
			inputBytes[i] = (byte) c;
		}
		ByteVector vInput = ByteVector.fromArray(SPECIES, inputBytes, 0);
		ByteVector vPrefix = ByteVector.fromArray(SPECIES, prefixPadded, 0);
		// Compare all lanes, then check that every active lane (0..prefixLength-1) is equal.
		VectorMask<Byte> active = SPECIES.indexInRange(0, prefixLength);
		VectorMask<Byte> eq = vInput.compare(VectorOperators.EQ, vPrefix);
		return eq.and(active).equals(active);
	}

	private boolean testScalar(CharSequence t) {
		for (int i = 0; i < prefixLength; i++) {
			if (prefixChars[i] != t.charAt(i)) {
				return false;
			}
		}
		return true;
	}
}
