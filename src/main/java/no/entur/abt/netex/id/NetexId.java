package no.entur.abt.netex.id;

import java.util.Objects;

import no.entur.abt.netex.utils.IllegalNetexIDException;

/*-
 * #%L
 * netex-utils
 * %%
 * Copyright (C) 2019 - 2026 Entur
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
 * Modern utility facade with legacy-like one-liner methods.
 */
public final class NetexId {

	private static final DefaultNetexIdValidator VALIDATOR = DefaultNetexIdValidator.getInstance();
	private static final NetexIdNonvalidatingParser NON_VALIDATING_PARSER = NetexIdNonvalidatingParser.getInstance();

	private NetexId() {
		// utility class
	}

	public static String createId(String codespace, String datatype, String value) {
		Objects.requireNonNull(codespace, "codespace must not be null");
		Objects.requireNonNull(datatype, "datatype must not be null");
		Objects.requireNonNull(value, "value must not be null");
		return codespace + DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR + datatype + DefaultNetexIdValidator.NETEX_ID_SEPARATOR_CHAR + value;
	}

	public static String createFrom(String id, String valuePart) {
		int index = VALIDATOR.validateToValueIndex(id);
		if(index == -1) {
			throw NetexIdValidatingParser.getException(id);
		}
		return id.substring(0, index) + valuePart;
	}

	public static String getCodespace(String id) {
		assertValid(id);
		return NON_VALIDATING_PARSER.getCodespace(id);
	}

	public static String getType(String id) {
		assertValid(id);
		return NON_VALIDATING_PARSER.getType(id);
	}

	public static String getValue(String id) {
		assertValid(id);
		return NON_VALIDATING_PARSER.getValue(id);
	}

	public static boolean isValid(String id) {
		return VALIDATOR.validate(id);
	}

	public static void assertValid(String id) {
		if (!isValid(id)) {
			throw NetexIdValidatingParser.getException(id);
		}
	}

	public static void assertValidOfType(String id, String expectedType) {
		assertValid(id);
		if (!Objects.equals(NON_VALIDATING_PARSER.getType(id), expectedType)) {
			throw new IllegalNetexIDException(String.format("Value '%s' is not of expected type '%s'", id, expectedType));
		}
	}
}

