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

/**
 * SIMD-accelerated predicate for a specific NeTEx type (ignores codespace and value).
 *
 * <p>Uses the Java Vector API to compare the {@code :TYPE:} portion of a NeTEx ID against the
 * expected type in a single vectorised operation when the type fits within one SIMD vector lane.</p>
 */
public class SimdNetexIdTypePredicate implements NetexIdPredicate {

	private static final VectorSpecies<Byte> SPECIES = ByteVector.SPECIES_PREFERRED;

	/**
	 * Zero-padded byte array of length equal to the vector lane count, holding
	 * {@code \0\0\0:TYPE:} at offsets 0..prefixLength-1.
	 */
	private final byte[] patternPadded;

	/**
	 * The total number of characters to compare: {@code codespaceLength + 1 + typeLength + 1}.
	 * Includes the leading three placeholder bytes and both colons.
	 */
	private final int patternLength;

	/**
	 * Number of bytes in the SIMD comparison region: {@code ":TYPE:".length() = typeLength + 2}.
	 * The input is read starting from position {@code NETEX_ID_CODESPACE_LENGTH}.
	 */
	private final int compareLen;

	/** Whether the pattern fits within a single SIMD vector (fast path). */
	private final boolean simdFastPath;

	/** Scalar fallback for long type names. */
	private final char[] patternChars;

	public SimdNetexIdTypePredicate(CharSequence type) {
		// For scalar: pattern = "\0\0\0:TYPE:"  (first 3 bytes are don't-cares / codespace)
		patternLength = DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH + 1 + type.length() + 1;
		patternChars = new char[patternLength];
		// first 3 chars are ignored (any codespace is accepted) - left as \0
		patternChars[DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH] = DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR;
		for (int i = 0; i < type.length(); i++) {
			patternChars[DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH + 1 + i] = type.charAt(i);
		}
		patternChars[patternLength - 1] = DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR;

		// For SIMD: compare input[3..patternLength-1] against ":TYPE:" (compareLen bytes from index 0).
		compareLen = type.length() + 2; // ":TYPE:".length()
		simdFastPath = compareLen <= SPECIES.length();
		if (simdFastPath) {
			patternPadded = new byte[SPECIES.length()];
			patternPadded[0] = (byte) DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR;
			for (int i = 0; i < type.length(); i++) {
				patternPadded[1 + i] = (byte) type.charAt(i);
			}
			patternPadded[1 + type.length()] = (byte) DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR;
		} else {
			patternPadded = null;
		}
	}

	@Override
	public boolean test(CharSequence t) {
		if (t == null) {
			return false;
		}
		if (t.length() < patternLength) {
			return false;
		}
		if (simdFastPath && t instanceof String) {
			return testSimd((String) t);
		}
		return testScalar(t);
	}

	private boolean testSimd(String t) {
		// Load input starting at NETEX_ID_CODESPACE_LENGTH (skip the codespace) into inputBytes[0..].
		byte[] inputBytes = new byte[SPECIES.length()];
		int start = DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH;
		for (int i = 0; i < compareLen; i++) {
			char c = t.charAt(start + i);
			if (c > 127) {
				return testScalar(t);
			}
			inputBytes[i] = (byte) c;
		}
		ByteVector vInput = ByteVector.fromArray(SPECIES, inputBytes, 0);
		ByteVector vPattern = ByteVector.fromArray(SPECIES, patternPadded, 0);
		// Compare the first compareLen lanes; indexInRange(0, n) means lanes 0..n-1 are active.
		VectorMask<Byte> active = SPECIES.indexInRange(0, compareLen);
		VectorMask<Byte> eq = vInput.compare(VectorOperators.EQ, vPattern);
		return eq.and(active).equals(active);
	}

	private boolean testScalar(CharSequence t) {
		// check both colons and the type characters
		if (t.charAt(DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH) != DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR) {
			return false;
		}
		if (t.charAt(patternLength - 1) != DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR) {
			return false;
		}
		for (int i = DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH + 1; i < patternLength - 1; i++) {
			if (patternChars[i] != t.charAt(i)) {
				return false;
			}
		}
		return true;
	}
}
