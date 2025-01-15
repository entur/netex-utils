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
public interface NetexIdValidator {

	default boolean validate(CharSequence id) {
		if (id == null) {
			return false;
		}
		return validate(id, 0, id.length());
	}

	boolean validate(CharSequence id, int offset, int length);

	default boolean validateCodespace(CharSequence codespace) {
		if (codespace == null) {
			return false;
		}
		return validateCodespace(codespace, 0, codespace.length());
	}

	boolean validateCodespace(CharSequence codespace, int offset, int length);

	default boolean validateType(CharSequence type) {
		return validateType(type, 0, type.length());
	}

	boolean validateType(CharSequence type, int offset, int length);

	default boolean validateValue(CharSequence value) {
		return validateValue(value, 0, value.length());
	}

	boolean validateValue(CharSequence value, int offset, int length);

}
