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

	protected static final boolean[] TYPE_CHARACTERS;
	protected static final boolean[] VALUE_CHARACTERS;

	static {
		// create lookup-table
		int uppercaseStart = 'A';
		int uppercaseEnd = 'Z';

		int lowercaseStart = 'a';
		int lowercaseEnd = 'z';

		// from regexp [A-Za-z]
		boolean[] types = new boolean[lowercaseEnd + 1];

		for (int i = uppercaseStart; i <= uppercaseEnd; i++) {
			types[i] = true;
		}
		for (int i = lowercaseStart; i <= lowercaseEnd; i++) {
			types[i] = true;
		}

		TYPE_CHARACTERS = types;

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
	 * Validate netex id type part, return index of first non-valid character
	 *
	 * @param type netex id
	 * @param startIndex start index (inclusive)
	 * @return index of first non-valid character, otherwise -1
	 */

	public static int validateTypeToIndex(CharSequence type, int startIndex) {
		// not empty string
		// A-Z
		// a-z
		for (int i = startIndex; i < type.length(); i++) {
			int c = type.charAt(i);

			if (c >= TYPE_CHARACTERS.length) {
				return i;
			}
			if (!TYPE_CHARACTERS[c]) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public boolean validate(CharSequence string) {
		if (string == null) {
			return false;
		}

		// minimum size is XXX:X:X
		if (string.length() < NETEX_ID_MINIMUM_LENGTH) {
			return false;
		}
		if (string.charAt(NETEX_ID_CODESPACE_LENGTH) != NETEX_ID_SEPARATOR_CHAR) {
			return false;
		}

		int last = validateTypeToIndex(string, NETEX_ID_CODESPACE_LENGTH + 1);
		return last != -1 && string.charAt(last) == NETEX_ID_SEPARATOR_CHAR && last > NETEX_ID_CODESPACE_LENGTH + 1 && validateCodespace(string, 0, NETEX_ID_CODESPACE_LENGTH)
				&& validateValue(string, last + 1, string.length());
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

	public boolean validateType(CharSequence type, int startIndex, int endIndex) {
		if (type == null || endIndex <= startIndex) {
			return false;
		}

		// not empty string
		// A-Z
		// a-z
		for (int i = startIndex; i < endIndex; i++) {
			int c = type.charAt(i);

			if (c >= TYPE_CHARACTERS.length) {
				return false;
			}
			if (!TYPE_CHARACTERS[c]) {
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
