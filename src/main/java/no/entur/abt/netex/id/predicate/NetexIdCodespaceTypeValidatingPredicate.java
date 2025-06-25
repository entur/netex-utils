package no.entur.abt.netex.id.predicate;

import no.entur.abt.netex.id.DefaultNetexIdValidator;
import no.entur.abt.netex.id.NetexIdValidatingParser;
import no.entur.abt.netex.id.NetexIdValidator;

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
 * Predicate for codespace + type (ignores value).
 *
 * Does validate input codespace.
 *
 */
public class NetexIdCodespaceTypeValidatingPredicate extends NetexIdCodespaceTypePredicate implements NetexIdPredicate {

	private final static DefaultNetexIdValidator VALIDATOR = DefaultNetexIdValidator.getInstance();

	public NetexIdCodespaceTypeValidatingPredicate(CharSequence codespace, CharSequence type) {
		super(codespace, type);
	}

	public boolean test(CharSequence t) {

		if(super.test(t)) {
			// codespace + type is assumed valid
			// also validate value
			if(!VALIDATOR.validateValue(t, codespaceColonType.length + 1, t.length())) {
				throwException(t);
			}
			return true;
		}

		// validate whole id since we do not get at match on the above test
		if(!VALIDATOR.validate(t, 0, t.length())) {
			throwException(t);
		}
		return false;
	}

	// protected so that override in a subclass is possible
	protected void throwException(CharSequence t) {
		throw NetexIdValidatingParser.getException(t);
	}
}
