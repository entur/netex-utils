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

/**
 * 
 * Build a Netex id. Includes validation.
 *
 */

public class NetexIdBuilder {

	public static NetexIdBuilder newInstance() {
		return new NetexIdBuilder();
	}

	protected static final String NETEX_ID_SEPARATOR_CHAR = ":";

	private static final NetexIdValidator defaultValidator = DefaultNetexIdValidator.getInstance();

	private final NetexIdValidator validator;

	protected String codespace;
	protected String type;
	protected String value;

	public NetexIdBuilder() {
		this(defaultValidator);
	}

	public NetexIdBuilder(NetexIdValidator validator) {
		this.validator = validator;
	}

	public NetexIdBuilder withCodespace(String codespace) {
		this.codespace = codespace;
		return this;
	}

	public NetexIdBuilder withType(String type) {
		this.type = type;
		return this;
	}

	public NetexIdBuilder withValue(String value) {
		this.value = value;

		return this;
	}

	public String build() {
		if (codespace == null || !validator.validateCodespace(codespace)) {
			throw new IllegalStateException("Expected codespace (size 3 with characters A-Z), found " + codespace);
		}
		if (type == null || !validator.validateType(type)) {
			throw new IllegalStateException("Expected type (nonempty with characters A-Z), found " + type);
		}
		if (value == null || !validator.validateValue(value)) {
			throw new IllegalStateException("Expected value (nonempty with characters A-Z, a-z, ø, Ø, æ, Æ, å, Å, underscore, \\ and -), found " + value);
		}
		return String.join(NETEX_ID_SEPARATOR_CHAR, codespace, type, value);
	}

}
