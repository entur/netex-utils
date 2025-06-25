package no.entur.abt.netex.id;

import no.entur.abt.netex.utils.IllegalNetexIDException;

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
public class NetexIdValidatingParser extends DefaultNetexIdValidator implements NetexIdParser {

	private final DefaultNetexIdValidator validator = DefaultNetexIdValidator.getInstance();

	public String getCodespace(CharSequence id) {
		assertValid(id);
		CharSequence result = id.subSequence(0, DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH);
		return result.toString();
	}


	public String getType(CharSequence string) {
		// inline validation
		if (string == null || string.length() < NETEX_ID_MINIMUM_LENGTH || string.charAt(NETEX_ID_CODESPACE_LENGTH) != ':') {
			throw getException(string);
		}

		int last = validateTypeToIndex(string, NETEX_ID_CODESPACE_LENGTH + 1);
		if(last != -1 && string.charAt(last) == ':' && last > NETEX_ID_CODESPACE_LENGTH + 1
				&& validateCodespace(string, 0, NETEX_ID_CODESPACE_LENGTH)
				&& validateValue(string, last + 1, string.length())
		) {
			return string.subSequence(DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH + 1, last).toString();
		}
		throw getException(string);
	}

	public String getValue(CharSequence string) {
		// inline validation
		if (string == null || string.length() < NETEX_ID_MINIMUM_LENGTH || string.charAt(NETEX_ID_CODESPACE_LENGTH) != ':') {
			throw getException(string);
		}
		int last = validateTypeToIndex(string, NETEX_ID_CODESPACE_LENGTH + 1);
		if(last != -1 && string.charAt(last) == ':' && last > NETEX_ID_CODESPACE_LENGTH + 1
				&& validateCodespace(string, 0, NETEX_ID_CODESPACE_LENGTH)
				&& validateValue(string, last + 1, string.length())
		) {
			return string.subSequence(last + 1, string.length()).toString();
		}
		throw getException(string);
	}

	private void assertValid(CharSequence id) {
		if (!validator.validate(id)) {
			throw getException(id);
		}
	}

	public static IllegalNetexIDException getException(CharSequence id) {
		return new IllegalNetexIDException(String.format(
				"Value '%s' is not a valid NeTEx id according to profile. ID should be in the format Codespace:Type:Val (ie XYZ:FareContract:1231)", id));
	}
}
