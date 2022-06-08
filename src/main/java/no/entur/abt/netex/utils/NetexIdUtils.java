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

public class NetexIdUtils {
	private static final String NETEX_ID_SEPARATOR_CHAR = ":";

	private static final String ID_PATTERN = "^([A-Z]{3}):([A-Za-z]+):([0-9ÆØÅæøåA-Za-z_\\\\-]+)$";

	public static String createId(String codespace, String datatype, String value) {
		return String.join(NETEX_ID_SEPARATOR_CHAR, codespace, datatype, value);
	}

	public static String getCodespace(String id) {
		return getField(id, 0);
	}

	public static String getType(String id) {
		return getField(id, 1);
	}

	public static String getValue(String id) {
		return getField(id, 2);
	}

	public static boolean isValid(String id) {
		return id != null && id.matches(ID_PATTERN);
	}

	public static void assertValidOfType(String id, String expectedType) {
		if (!isValid(id)) {
			throw new IllegalNetexIDException(String.format(
					"Value '%s' is not a valid NeTEx id according to profile. ID should be in the format Codespace:Type:Val (ie XYZ:FareContract:1231)", id));
		}
		if (!Objects.equals(getType(id), expectedType)) {
			throw new IllegalNetexIDException(String.format("Value '%s' is not of expected type :'%s'", id, expectedType));
		}
	}

	private static String getField(String id, int index) {
		try {
			return id.split(NETEX_ID_SEPARATOR_CHAR)[index];
		} catch (Exception e) {
			throw new IllegalNetexIDException(String.format(
					"Value '%s' is not a valid NeTEx id according to profile. ID should be in the format Codespace:Type:Val (ie XYZ:FareContract:1231)", id),
					e);
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
		return createId(getCodespace(id), getType(id), valuePart);

	}
}
