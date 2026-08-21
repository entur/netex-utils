package no.entur.abt.netex.id;

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

/**
 * SIMD-accelerated NeTEx ID validator using the Java Vector API ({@code jdk.incubator.vector}).
 *
 * <p>This implementation validates NeTEx IDs of the form {@code XXX:TYPE:VALUE} using vectorised
 * byte-range comparisons where possible, falling back to scalar comparisons for characters that do
 * not fit in the ASCII range or for short tail segments.</p>
 *
 * <p>Use {@link NetexIdValidatorBuilder} to obtain an instance; it will automatically fall back to
 * {@link DefaultNetexIdValidator} when the Vector API is unavailable at runtime.</p>
 */
public class SimdNetexIdValidator extends DefaultNetexIdValidator {

	private static final VectorSpecies<Byte> SPECIES = ByteVector.SPECIES_PREFERRED;

	private static final SimdNetexIdValidator SIMD_INSTANCE = new SimdNetexIdValidator();

	public static SimdNetexIdValidator getInstance() {
		return SIMD_INSTANCE;
	}

	/**
	 * Convert a CharSequence region to a byte array, returning {@code null} if any character is
	 * outside the range {@code [0, 127]} (i.e., not pure ASCII).
	 */
	private static byte[] toAsciiBytes(CharSequence s, int start, int end) {
		int len = end - start;
		byte[] buf = new byte[len];
		for (int i = 0; i < len; i++) {
			char c = s.charAt(start + i);
			if (c > 127) {
				return null; // non-ASCII: caller must use scalar fallback
			}
			buf[i] = (byte) c;
		}
		return buf;
	}

	/**
	 * SIMD-accelerated codespace validation.
	 *
	 * <p>Codespace must be exactly 3 uppercase ASCII letters ({@code [A-Z]}).</p>
	 */
	@Override
	public boolean validateCodespace(CharSequence codespace, int startIndex, int endIndex) {
		if (codespace == null) {
			return false;
		}
		if (endIndex - startIndex != NETEX_ID_CODESPACE_LENGTH) {
			return false;
		}
		// Codespace is always 3 chars - scalar path is fine here, no SIMD gain for 3 bytes.
		// We still keep the override so the call hierarchy uses a uniform method.
		for (int i = startIndex; i < endIndex; i++) {
			char c = codespace.charAt(i);
			if (c < 'A' || c > 'Z') {
				return false;
			}
		}
		return true;
	}

	/**
	 * SIMD-accelerated type validation.
	 *
	 * <p>All characters must be ASCII letters ({@code [A-Za-z]}).</p>
	 */
	@Override
	public boolean validateType(CharSequence type, int startIndex, int endIndex) {
		if (type == null || endIndex <= startIndex) {
			return false;
		}
		byte[] buf = toAsciiBytes(type, startIndex, endIndex);
		if (buf == null) {
			// non-ASCII character found – cannot be a valid type char
			return false;
		}
		// [A-Za-z]: upper [65,90], lower [97,122].
		// Strategy: mask off bit 5 (case bit) → maps both ranges to [65,90].
		int i = 0;
		int upperBound = SPECIES.loopBound(buf.length);
		for (; i < upperBound; i += SPECIES.length()) {
			ByteVector v = ByteVector.fromArray(SPECIES, buf, i);
			// clear bit 5 (0x20) to normalise lower-case to upper-case
			ByteVector upper = v.and((byte) 0xDF);
			VectorMask<Byte> gteLo = upper.compare(VectorOperators.UNSIGNED_GE, (byte) 'A');
			VectorMask<Byte> lteHi = upper.compare(VectorOperators.UNSIGNED_LE, (byte) 'Z');
			if (!gteLo.and(lteHi).allTrue()) {
				return false;
			}
		}
		// scalar tail
		for (; i < buf.length; i++) {
			int c = buf[i] & 0xFF;
			int cu = c & 0xDF; // clear bit 5
			if (cu < 'A' || cu > 'Z') {
				return false;
			}
		}
		return true;
	}

	/**
	 * SIMD-accelerated value validation.
	 *
	 * <p>Valid value characters are {@code [0-9A-Za-z_\\-ÆØÅæøå]}. Characters outside the ASCII
	 * range (Norwegian letters) are handled by the scalar fallback in the parent class.</p>
	 */
	@Override
	public boolean validateValue(CharSequence value, int startIndex, int endIndex) {
		if (value == null || endIndex <= startIndex) {
			return false;
		}
		// For value validation we may encounter non-ASCII characters (Norwegian letters).
		// Process the ASCII prefix with SIMD; fall back to the scalar lookup table for
		// any characters that are out of range.
		int i = startIndex;
		int upperBound = startIndex + SPECIES.loopBound(endIndex - startIndex);
		byte[] lane = upperBound > startIndex ? new byte[SPECIES.length()] : null;
		for (; i < upperBound; i += SPECIES.length()) {
			// Check all chars in this lane are ASCII; if any is not, break and use scalar.
			boolean hasNonAscii = false;
			for (int j = 0; j < SPECIES.length(); j++) {
				char c = value.charAt(i + j);
				if (c > 127) {
					hasNonAscii = true;
					break;
				}
				lane[j] = (byte) c;
			}
			if (hasNonAscii) {
				break; // fall through to scalar
			}
			ByteVector v = ByteVector.fromArray(SPECIES, lane, 0);
			// Valid ASCII value chars: digits [48,57], uppercase [65,90], lowercase [97,122],
			// underscore [95], backslash [92], hyphen [45].
			// Build a combined mask: digit | upper | lower | special
			VectorMask<Byte> digit = v.compare(VectorOperators.UNSIGNED_GE, (byte) '0').and(v.compare(VectorOperators.UNSIGNED_LE, (byte) '9'));
			VectorMask<Byte> upper = v.compare(VectorOperators.UNSIGNED_GE, (byte) 'A').and(v.compare(VectorOperators.UNSIGNED_LE, (byte) 'Z'));
			VectorMask<Byte> lower = v.compare(VectorOperators.UNSIGNED_GE, (byte) 'a').and(v.compare(VectorOperators.UNSIGNED_LE, (byte) 'z'));
			VectorMask<Byte> underscore = v.compare(VectorOperators.EQ, (byte) '_');
			VectorMask<Byte> backslash = v.compare(VectorOperators.EQ, (byte) '\\');
			VectorMask<Byte> hyphen = v.compare(VectorOperators.EQ, (byte) '-');
			VectorMask<Byte> valid = digit.or(upper).or(lower).or(underscore).or(backslash).or(hyphen);
			if (!valid.allTrue()) {
				return false;
			}
		}
		// scalar tail (handles non-ASCII Norwegian chars too)
		for (; i < endIndex; i++) {
			int c = value.charAt(i);
			if (c >= VALUE_CHARACTERS.length || !VALUE_CHARACTERS[c]) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected int validateToValueIndex(CharSequence string) {
		if (string == null) {
			return -1;
		}
		// minimum size is XXX:X:X
		if (string.length() < NETEX_ID_MINIMUM_LENGTH) {
			return -1;
		}
		if (string.charAt(NETEX_ID_CODESPACE_LENGTH) != NETEX_ID_SEPARATOR_CHAR) {
			return -1;
		}
		int typeFirstNonValidCharIndex = validateTypeToIndex(string, NETEX_ID_CODESPACE_LENGTH + 1);
		if (typeFirstNonValidCharIndex == -1 || string.charAt(typeFirstNonValidCharIndex) != NETEX_ID_SEPARATOR_CHAR || typeFirstNonValidCharIndex <= NETEX_ID_CODESPACE_LENGTH + 1) {
			return -1;
		}
		if (!validateCodespace(string, 0, NETEX_ID_CODESPACE_LENGTH)) {
			return -1;
		}
		typeFirstNonValidCharIndex++;
		if (!validateValue(string, typeFirstNonValidCharIndex, string.length())) {
			return -1;
		}
		return typeFirstNonValidCharIndex;
	}
}
