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

import no.entur.abt.netex.utils.IllegalNetexIDException;

/**
 * 
 * Build a Netex id. Does not validate inputs; the caller is responsible for ensuring that
 * the codespace, type and value parts are valid before calling {@link #build()}.
 *
 */

public class NetexIdNonValidatingBuilder {

	public static NetexIdNonValidatingBuilder newInstance() {
		return new NetexIdNonValidatingBuilder();
	}

	/**
	 * Create new id from an existing id + value part.
	 *
	 * @param id input id. codespace and type will be copied from this id.
	 * @param valuePart new value part
	 * @return an id with codespace and type from the id argument, value from the valuePart argument.
	 */

	public static String createIdFrom(String id, String valuePart) {
		if(id == null) {
			throw new IllegalArgumentException("Expected id");
		}
		if(valuePart == null) {
			throw new IllegalArgumentException("Expected value");
		}
		int index = id.lastIndexOf(DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR);
		if(index == -1) {
			throw NetexIdValidatingParser.getException(id);
		}
		return id.substring(0, index + 1) + valuePart;
	}

	public static String createId(String codespace, String type, String value) {
		if(codespace == null) {
			throw new IllegalArgumentException("Codespace must not be null");
		}
		if(type == null) {
			throw new IllegalArgumentException("Type must not be null");
		}
		if(value == null) {
			throw new IllegalArgumentException("Value must not be null");
		}
		return codespace + DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR + type + DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR + value;
	}

	public static NetexIdNonValidatingBuilder newInstance(String id) {
		return new NetexIdNonValidatingBuilder(id);
	}

	protected String codespace;
	protected String type;
	protected String value;

	public NetexIdNonValidatingBuilder(String id) {
		if(id == null) {
			throw new IllegalArgumentException("Expected id");
		}
		// use id as template
		NetexIdNonvalidatingParser parser = NetexIdNonvalidatingParser.getInstance();
		codespace = parser.getCodespace(id);
		type = parser.getType(id);
		value = parser.getValue(id);
	}

	public NetexIdNonValidatingBuilder() {
	}

	public NetexIdNonValidatingBuilder withCodespace(String codespace) {
		this.codespace = codespace;
		return this;
	}

	public NetexIdNonValidatingBuilder withType(String type) {
		this.type = type;
		return this;
	}

	public NetexIdNonValidatingBuilder withValue(String value) {
		this.value = value;

		return this;
	}

	public String build() {
		if (codespace == null) {
			throw new IllegalStateException("Expected codespace (size 3 with characters A-Z)");
		}
		if (type == null) {
			throw new IllegalStateException("Expected type (nonempty with characters A-Z or a-z)");
		}
		if (value == null) {
			throw new IllegalStateException("Expected value (nonempty with characters A-Z, a-z, ø, Ø, æ, Æ, å, Å, underscore, \\ and -)");
		}
		return codespace + DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR + type + DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR + value;
	}

}
