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
package no.entur.abt.netex.id;

public class DefaultNetexIdValidator implements NetexIdValidator {

	public static final char NETEX_ID_SEPARATOR_CHAR = ':';
	public static final int NETEX_ID_CODESPACE_LENGTH = 3;
	public static final int NETEX_ID_MINIMUM_LENGTH = 7;

	protected static final boolean[] VALUE_CHARACTERS;

	static {
		// create lookup-table
		int uppercaseStart = 'A';
		int uppercaseEnd = 'Z';

		int lowercaseStart = 'a';
		int lowercaseEnd = 'z';

		int digitStart = '0';
		int digitEnd = '9';

		// from chars [0-9ÆØÅæøåA-Za-z_\\-]
		boolean[] values = new boolean[248 + 1];

		for (int i = uppercaseStart; i <= uppercaseEnd; i++) {
			values[i] = true;
		}
		for (int i = lowercaseStart; i <= lowercaseEnd; i++) {
			values[i] = true;
		}
		for (int i = digitStart; i <= digitEnd; i++) {
			values[i] = true;
		}

		// norwegian chars
		values['Æ'] = true;
		values['æ'] = true;
		values['Å'] = true;
		values['å'] = true;
		values['Ø'] = true;
		values['ø'] = true;

		// special chars
		values['_'] = true;
		values['\\'] = true;
		values['-'] = true;

		VALUE_CHARACTERS = values;
	}

	protected static final DefaultNetexIdValidator INSTANCE = new DefaultNetexIdValidator();

	public static DefaultNetexIdValidator getInstance() {
		return INSTANCE;
	}


	/**
	 * Scans from {@code startIndex} to the end for the first character outside
	 * {@code A-Z} and {@code a-z}. A colon therefore ends the scan when this
	 * method is used to locate the separator after an ID's type part.
	 *
	 * <p>The check {@code (c | 0x20)} sets the ASCII case bit: uppercase letters
	 * become lowercase (for example, {@code 'A'} becomes {@code 'a'}), while
	 * lowercase letters stay unchanged. Testing the result against {@code a-z}
	 * accepts exactly the ASCII letters without a lookup-table access. Other
	 * ASCII characters remain outside that range, and non-ASCII characters retain
	 * their higher bits, so they cannot become ASCII letters. This is not a
	 * general Unicode case conversion.
	 *
	 * @param type character sequence containing the type or complete ID
	 * @param startIndex start index (inclusive)
	 * @return index of the first non-letter, or {@code -1} if the remaining
	 *         sequence contains only ASCII letters or is empty
	 */

	public static int validateTypeToIndex(CharSequence type, int startIndex) {
		// not empty string
		// A-Z
		// a-z
		for (int i = startIndex; i < type.length(); i++) {
			int c = type.charAt(i);

			// ASCII uppercase and lowercase letters differ only by the 0x20 bit:
			// 'A' (0100 0001) | 0x20 (0010 0000) = 'a' (0110 0001).
			// OR sets that bit, leaving lowercase letters unchanged. Only A-Z/a-z
			// map into a-z; non-ASCII characters retain their higher bits.
			// This accepts either case without a lookup-table access.
			if ((c | 0x20) < 'a' || (c | 0x20) > 'z') {
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean validate(CharSequence string) {
		return validateToValueIndex(string) != -1;
	}

	protected static int getLastSeperatorIndex(CharSequence string, int startIndex, int endIndex) {
		for (int i = endIndex - 1; i >= startIndex; i--) {
			if (string.charAt(i) == NETEX_ID_SEPARATOR_CHAR) {
				return i;
			}
		}
		return -1;
	}

	public boolean validateCodespace(CharSequence codespace, int startIndex, int endIndex) {
		if (codespace == null) {
			return false;
		}

		// length 3
		// A-Z
		if (endIndex - startIndex == NETEX_ID_CODESPACE_LENGTH) {
			for (int i = startIndex; i < endIndex; i++) {
				char c = codespace.charAt(i);
				if (c < 'A' || c > 'Z') {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Validates a non-empty range containing only ASCII letters {@code A-Z}
	 * and {@code a-z}, using the case-bit check described in
	 * {@link #validateTypeToIndex(CharSequence, int)}.
	 *
	 * @param type character sequence containing the type
	 * @param startIndex start index (inclusive)
	 * @param endIndex end index (exclusive)
	 * @return {@code true} if the range contains only ASCII letters;
	 *         {@code false} for null input, an empty or reversed range, or a non-letter
	 */
	public boolean validateType(CharSequence type, int startIndex, int endIndex) {
		if (type == null || endIndex <= startIndex) {
			return false;
		}

		// not empty string
		// A-Z
		// a-z
		for (int i = startIndex; i < endIndex; i++) {
			int c = type.charAt(i);

			// ASCII uppercase and lowercase letters differ only by the 0x20 bit:
			// 'A' (0100 0001) | 0x20 (0010 0000) = 'a' (0110 0001).
			// OR sets that bit, leaving lowercase letters unchanged. Only A-Z/a-z
			// map into a-z; non-ASCII characters retain their higher bits.
			// This accepts either case without a lookup-table access.
			if ((c | 0x20) < 'a' || (c | 0x20) > 'z') {
				return false;
			}
		}
		return true;
	}

	public boolean validateValue(CharSequence value, int startIndex, int endIndex) {
		if (value == null || endIndex <= startIndex) {
			return false;
		}
		for (int i = startIndex; i < endIndex; i++) {
			int c = value.charAt(i);
			if (c >= VALUE_CHARACTERS.length) {
				return false;
			}
			if (!VALUE_CHARACTERS[c]) {
				return false;
			}
		}
		return true;
	}

	/**
	 *
	 * Validate id, return index of value part (the character index after the second colon) if valid, otherwise -1.
	 *
	 * @param string netex id
	 * @return -1 if the id is invalid, otherwise the index of the value part within the id (the character index after the second colon).
	 */

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

		// XXX:AB:CDE
		// 0123456789
		//
		// scan from A at index 4.
		// Outcomes:
		// Valid id: For a valid id the first non-valid char should be the second colon (at index 6 in example), with at least one char between it and the colon at index 3.
		// Invalid id: No non-valid char found, or a non-valid char that is not colon.
		int typeFirstNonValidCharIndex = validateTypeToIndex(string, NETEX_ID_CODESPACE_LENGTH + 1);
		if (typeFirstNonValidCharIndex == -1 || string.charAt(typeFirstNonValidCharIndex) != NETEX_ID_SEPARATOR_CHAR || typeFirstNonValidCharIndex <= NETEX_ID_CODESPACE_LENGTH + 1) {
			return -1;
		}

		if (!validateCodespace(string, 0, NETEX_ID_CODESPACE_LENGTH)) {
			return -1;
		}

		// skip the colon. index is now the first char of the value part.
		typeFirstNonValidCharIndex++;
		if (!validateValue(string, typeFirstNonValidCharIndex, string.length())) {
			return -1;
		}

		return typeFirstNonValidCharIndex;
	}
}
