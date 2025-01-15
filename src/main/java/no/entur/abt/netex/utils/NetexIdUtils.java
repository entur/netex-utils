package no.entur.abt.netex.utils;

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

import java.util.Objects;

import no.entur.abt.netex.id.DefaultNetexIdValidator;
import no.entur.abt.netex.id.NetexIdNonvalidatingParser;
import no.entur.abt.netex.id.NetexIdValidatingParser;

public class NetexIdUtils {

	private static final DefaultNetexIdValidator VALIDATOR = DefaultNetexIdValidator.getInstance();
	private static final NetexIdValidatingParser VALIDATING_PARSER = new NetexIdValidatingParser();
	private static final NetexIdNonvalidatingParser NONVALIDATING_PARSER = new NetexIdNonvalidatingParser();

	public static String createId(String codespace, String type, String value) {
		return codespace + DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR + type + DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR + value;
	}

	public static String getCodespace(String id) {
		return VALIDATING_PARSER.getCodespace(id);
	}

	public static String getType(String id) {
		return VALIDATING_PARSER.getType(id);
	}

	public static String getValue(String id) {
		return VALIDATING_PARSER.getValue(id);
	}

	public static boolean isValid(String id) {
		return VALIDATOR.validate(id);
	}

	public static void assertValidOfType(String id, String expectedType) {
		if (!Objects.equals(VALIDATING_PARSER.getType(id), expectedType)) {
			throw new IllegalNetexIDException(String.format("Value '%s' is not of expected type '%s'", id, expectedType));
		}
	}

	public static void assertValid(String id) {
		if (!isValid(id)) {
			throw new IllegalNetexIDException(String.format(
					"Value '%s' is not a valid NeTEx id according to profile. ID should be in the format Codespace:Type:Val (ie XYZ:FareContract:1231)", id));
		}
	}

	/**
	 * Create a new NeTEx id of same codespace and type but with new value part
	 * 
	 * @param id        original netex id
	 * @param valuePart value part of id
	 * @return the newly constructed id
	 */
	public static String createFrom(String id, String valuePart) {
		assertValid(id);

		if (!VALIDATOR.validateValue(valuePart)) {
			throw new IllegalNetexIDException("Expected value (nonempty with characters A-Z, a-z, ø, Ø, æ, Æ, å, Å, underscore, \\ and -), found " + valuePart);
		}

		String codespace = NONVALIDATING_PARSER.getCodespace(id);
		String datatype = NONVALIDATING_PARSER.getType(id);

		return createId(codespace, datatype, valuePart);
	}
}
