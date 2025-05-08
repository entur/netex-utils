package no.entur.abt.netex.id.predicate;

/*-
 * #%L
 * Netex utils
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

import no.entur.abt.netex.id.DefaultNetexIdValidator;

/**
 *
 * Predicate for namespace (ignores type and value).. Does not validate input codespace.
 *
 */

public class NetexIdCodespacePredicate implements NetexIdPredicate {

	private final char[] prefix;

	public NetexIdCodespacePredicate(CharSequence codespace) {
		prefix = new char[] { codespace.charAt(0), codespace.charAt(1), codespace.charAt(2), DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR };
	}

	public boolean test(CharSequence t) {
		return t.length() > 4 && t.charAt(0) == prefix[0] && t.charAt(1) == prefix[1] && t.charAt(2) == prefix[2] && t.charAt(3) == prefix[3];
	}
}
