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
package no.entur.abt.netex.id.teavm;

import no.entur.abt.netex.id.DefaultNetexIdValidator;
import no.entur.abt.netex.id.NetexIdValidatingParser;
import no.entur.abt.netex.id.predicate.NetexIdPredicateBuilder;
import no.entur.abt.netex.utils.IllegalNetexIDException;
import org.teavm.jso.JSExport;

/**
 * TeaVM entry point — exports NeTEx ID validation/parsing to JavaScript.
 * Compiled to ES2015 module; each static method becomes a named export.
 */
public class NetexValidatorModule {

	private static final DefaultNetexIdValidator VALIDATOR = DefaultNetexIdValidator.getInstance();
	private static final NetexIdValidatingParser PARSER = NetexIdValidatingParser.getInstance();

	@JSExport
	public static boolean validate(String id) {
		return VALIDATOR.validate(id);
	}

	@JSExport
	public static String getCodespace(String id) {
		return id.substring(0, DefaultNetexIdValidator.NETEX_ID_CODESPACE_LENGTH);
	}

	@JSExport
	public static String getType(String id) {
		return PARSER.getType(id);
	}

	@JSExport
	public static String getValue(String id) {
		return PARSER.getValue(id);
	}

	@JSExport
	public static boolean validateCodespace(String codespace) {
		return VALIDATOR.validateCodespace(codespace, 0, codespace == null ? 0 : codespace.length());
	}

	@JSExport
	public static boolean validateType(String type) {
		return VALIDATOR.validateType(type, 0, type == null ? 0 : type.length());
	}

	/**
	 * Returns true if {@code id} matches the given codespace and/or type predicate.
	 * Pass null or empty string to omit a filter. Requires at least one filter.
	 */
	@JSExport
	public static boolean testPredicate(String id, String codespace, String type) {
		boolean hasCodespace = codespace != null && !codespace.isEmpty();
		boolean hasType = type != null && !type.isEmpty();
		if (!hasCodespace && !hasType) {
			return false;
		}
		try {
			NetexIdPredicateBuilder builder = NetexIdPredicateBuilder.newInstance();
			if (hasCodespace) builder.withCodespace(codespace);
			if (hasType) builder.withType(type);
			return builder.build().test(id);
		} catch (IllegalNetexIDException e) {
			return false;
		}
	}
}
