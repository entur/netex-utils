package no.entur.abt.netex.id;

/*-
 * #%L
 * Netex utils
 * %%
 * Copyright (C) 2019 - 2025 Entur
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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DefaultNetexIdValidatorTest {

	private DefaultNetexIdValidator defaultNetexIdValidator = DefaultNetexIdValidator.getInstance();

	@Test
	public void testEnglishValueCharacters() {
		assertTrue(defaultNetexIdValidator.validate("AAA:BBB:ABZabz"));
	}

	@Test
	public void testNordicValueCharacters() {
		assertTrue(defaultNetexIdValidator.validate("AAA:BBB:ØÆÅøæå"));
	}

	@Test
	public void testSpecialValueCharacters() {
		assertTrue(defaultNetexIdValidator.validate("AAA:BBB:_-\\"));
	}

	@Test
	public void testIllegalValueCharactersLow() {
		assertFalse(defaultNetexIdValidator.validate("AAA:BBB:!()"));
	}

	@Test
	public void testIllegalValueCharactersHigh() {
		assertFalse(defaultNetexIdValidator.validate("AAA:BBB:Cÿ"));
	}

	@Test
	public void testIllegalTypeCharactersLow() {
		assertFalse(defaultNetexIdValidator.validate("AAA:!():BBB"));
	}

	@Test
	public void testIllegalTypeCharactersHigh() {
		assertFalse(defaultNetexIdValidator.validate("AAA:Cÿ:BBB"));
	}

	@Test
	public void testIllegalCodespaceCharactersLow() {
		assertFalse(defaultNetexIdValidator.validate("!():AAA:BBB"));
	}

	@Test
	public void testIllegalCodespaceCharactersHigh() {
		assertFalse(defaultNetexIdValidator.validate("CÿC:AAA:BBB"));
	}

	@Test
	public void testEnglishTypeCharacters() {
		assertTrue(defaultNetexIdValidator.validate("AAA:BBB:CCC"));
		assertTrue(defaultNetexIdValidator.validate("AAA:bbb:CCC"));
	}

	@Test
	public void testTypeCharactersLow() {
		assertFalse(defaultNetexIdValidator.validateType("!()"));
	}

	@Test
	public void testTypeCharactersHigh() {
		assertFalse(defaultNetexIdValidator.validateType("CÿC"));
	}


	@Test
	public void testIllegalTypeCharacters() {
		assertFalse(defaultNetexIdValidator.validate("AAA:ØKS:CCC"));
	}

	@Test
	public void testIllegalCodespaceCharacters() {
		assertFalse(defaultNetexIdValidator.validate("AAA!:BBB:CCC"));
	}

	@Test
	public void testNull() {
        fail("Tesdddtdf");
		assertFalse(defaultNetexIdValidator.validate(null));
		assertFalse(defaultNetexIdValidator.validateType(null));
		assertFalse(defaultNetexIdValidator.validateCodespace(null));
		assertFalse(defaultNetexIdValidator.validateValue(null));

		assertFalse(defaultNetexIdValidator.validate(null, 0, 3));
		assertFalse(defaultNetexIdValidator.validateType(null, 0, 3));
		assertFalse(defaultNetexIdValidator.validateCodespace(null, 0, 3));
		assertFalse(defaultNetexIdValidator.validateValue(null, 0, 3));
	}

}
