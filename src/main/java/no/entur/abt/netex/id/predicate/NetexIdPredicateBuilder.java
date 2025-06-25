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
import no.entur.abt.netex.id.NetexIdValidator;

/**
 * 
 * Validating {@linkplain NetexIdPredicate} builder.
 *
 */

public class NetexIdPredicateBuilder {

	private final static NetexIdValidator validator = DefaultNetexIdValidator.getInstance();

	public static NetexIdPredicateBuilder newInstance() {
		return new NetexIdPredicateBuilder();
	}

	protected String codespace;
	protected String type;
	protected boolean validate = false;

	public NetexIdPredicateBuilder withCodespace(String codespace) {
		this.codespace = codespace;
		return this;
	}

	public NetexIdPredicateBuilder withType(String type) {
		this.type = type;
		return this;
	}

	public NetexIdPredicateBuilder withValidate(boolean validate) {
		this.validate = validate;
		return this;
	}

	public NetexIdPredicate build() {
		if (codespace != null && !validator.validateCodespace(codespace)) {
			throw new IllegalStateException("Expected codespace (size 3 with characters A-Z), found " + codespace);
		}
		if (type != null && !validator.validateType(type)) {
			throw new IllegalStateException("Expected type (nonempty with characters A-Z), found " + type);
		}

		if(validate) {
			if (codespace != null && type != null) {
				return new NetexIdCodespaceTypeValidatingPredicate(codespace, type);
			} else if (codespace != null) {
				return new NetexIdCodespaceValidatingPredicate(codespace);
			} else if (type != null) {
				return new NetexIdTypeValidatingPredicate(type);
			} else {
				throw new IllegalStateException("Expected codespace and/or type");
			}
		}

		if (codespace != null && type != null) {
			return new NetexIdCodespaceTypePredicate(codespace, type);
		} else if (codespace != null) {
			return new NetexIdCodespacePredicate(codespace);
		} else if (type != null) {
			return new NetexIdTypePredicate(type);
		} else {
			throw new IllegalStateException("Expected codespace and/or type");
		}

	}

}
