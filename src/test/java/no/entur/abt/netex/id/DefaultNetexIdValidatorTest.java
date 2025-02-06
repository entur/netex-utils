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
	public void testIllegalValueCharacters() {
		assertFalse(defaultNetexIdValidator.validate("AAA:BBB:!()"));
	}

	@Test
	public void testEnglishTypeCharacters() {
		assertTrue(defaultNetexIdValidator.validate("AAA:BBB:CCC"));
		assertTrue(defaultNetexIdValidator.validate("AAA:bbb:CCC"));
	}

	@Test
	public void testIllegalTypeCharacters() {
		assertFalse(defaultNetexIdValidator.validate("AAA:ØKS:CCC"));
	}

	@Test
	public void testIllegalCodespaceCharacters() {
		assertFalse(defaultNetexIdValidator.validate("AAA!:BBB:CCC"));
	}

}
