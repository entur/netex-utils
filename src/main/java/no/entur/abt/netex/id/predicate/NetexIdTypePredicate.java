package no.entur.abt.netex.id.predicate;

import no.entur.abt.netex.id.DefaultNetexIdValidator;

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

/**
 * 
 * Filter for Netex type (ignores codespace and value).
 *
 */

public class NetexIdTypePredicate implements NetexIdPredicate {

	// type, padded with 4 chars
	protected final char[] type;

	public NetexIdTypePredicate(CharSequence type) {
		// add 4 padding so that we can directly compare
		// indexes
		char[] chars = new char[DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH + 1 + type.length()];
		for (int i = 0; i < type.length(); i++) {
			chars[DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH + 1 + i] = type.charAt(i);
		}
		this.type = chars;
	}

	public boolean test(CharSequence t) {
		if (t.length() < type.length + 1) {
			return false;
		}
		if (t.charAt(DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH) != DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR) {
			return false;
		}

		// XXX:TYPE:
		if (t.charAt(type.length) != DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR) {
			return false;
		}

		for (int i = DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH + 1; i < type.length; i++) {
			if (type[i] != t.charAt(i)) {
				return false;
			}
		}

		return true;
	}
}
