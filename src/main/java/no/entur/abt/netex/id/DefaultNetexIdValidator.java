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
	public static final int NETEX_ID_MINIMUM_LENGTH = 6;

	protected static final DefaultNetexIdValidator instance = new DefaultNetexIdValidator();

	public static DefaultNetexIdValidator getInstance() {
		return instance;
	}

	public boolean validate(CharSequence string, int offset, int length) {
		// minimum size is XXX:X:X
		if (length < NETEX_ID_MINIMUM_LENGTH) {
			return false;
		}
		if (string.charAt(offset + NETEX_ID_CODESPACE_LENGTH) != ':') {
			return false;
		}
		int last = getLastSeperatorIndex(string, NETEX_ID_CODESPACE_LENGTH + 1, length);
		if (last == -1) {
			return false;
		}
		return validateCodespace(string, 0, NETEX_ID_CODESPACE_LENGTH) && validateType(string, NETEX_ID_CODESPACE_LENGTH + 1, last)
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
		// not empty string
		// A-Z
		// a-z
		if (endIndex > startIndex) {
			for (int i = startIndex; i < endIndex; i++) {
				char c = type.charAt(i);
				if (!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	public boolean validateValue(CharSequence value, int startIndex, int endIndex) {
		if (endIndex > startIndex) {
			for (int i = startIndex; i < endIndex; i++) {
				char c = value.charAt(i);
				if (!isValueCharacter(c)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	protected static boolean isValueCharacter(char c) {
		if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')) {
			return true;
		}

		switch (c) {
		case 'Æ':
		case 'Ø':
		case 'Å':
		case 'æ':
		case 'ø':
		case 'å':
		case '_':
		case '\\':
		case '-':
			return true;
		default:
			return false;
		}
	}
}
