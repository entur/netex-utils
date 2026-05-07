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


import no.entur.abt.netex.id.NetexIdValidatingParser;
import no.entur.abt.netex.id.NetexIdNonvalidatingParser;
import no.entur.abt.netex.id.NetexIdBuilder;
import no.entur.abt.netex.id.DefaultNetexIdValidator;
import no.entur.abt.netex.id.NetexIdNonValidatingBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class NetexIdUtils {

	private static final NetexIdValidatingParser PARSER = NetexIdValidatingParser.getInstance();
	private static final NetexIdNonvalidatingParser NON_VALIDATING_PARSER = NetexIdNonvalidatingParser.getInstance();
	private static final DefaultNetexIdValidator VALIDATOR = DefaultNetexIdValidator.getInstance();

	private static final Logger LOGGER = LoggerFactory.getLogger(NetexIdUtils.class);

	private NetexIdUtils() {
		// utility class
	}

	public static String createId(String codespace, String datatype, String value) {

		boolean validCodespace = VALIDATOR.validateCodespace(codespace);
		boolean validType = VALIDATOR.validateType(datatype);
		boolean validValue = VALIDATOR.validateValue(value);

		if(!validCodespace || !validType || !validValue) {
			LOGGER.warn("Creating id from one or more invalid parts: Codespace '" + codespace + "', type '" + datatype + "' and value '" + value + "'.");
		}

		return NetexIdNonValidatingBuilder.createId(codespace, datatype, value);
	}

	public static String getCodespace(String id) {
		return PARSER.getCodespace(id);
	}

	public static String getType(String id) {
		return PARSER.getType(id);
	}

	public static String getValue(String id) {
		return PARSER.getValue(id);
	}

	public static boolean isValid(String id) {
		return PARSER.validate(id);
	}

	public static void assertValidOfType(String id, String expectedType) {
		assertValid(id);
		if (!Objects.equals(NON_VALIDATING_PARSER.getType(id), expectedType)) {
			throw new IllegalNetexIDException(String.format("Value '%s' is not of expected type '%s'", id, expectedType));
		}
	}

	public static void assertValid(String id) {
		if (!isValid(id)) {
			throw NetexIdValidatingParser.getException(id);
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
		return NetexIdBuilder.createIdFrom(id, valuePart);
	}
}
