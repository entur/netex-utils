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
		assertFalse(defaultNetexIdValidator.validate(null));
		assertFalse(defaultNetexIdValidator.validateType(null));
		assertFalse(defaultNetexIdValidator.validateCodespace(null));
		assertFalse(defaultNetexIdValidator.validateValue(null));

		assertFalse(defaultNetexIdValidator.validate(null, 0, 3));
		assertFalse(defaultNetexIdValidator.validateType(null, 0, 3));
		assertFalse(defaultNetexIdValidator.validateCodespace(null, 0, 3));
		assertFalse(defaultNetexIdValidator.validateValue(null, 0, 3));
	}

	@Test
	public void testValidateToValueIndex_whenValid_thenReturnValueStartIndex() {
		String id = "AAA:BBB:CCC";
		assertEquals(8, defaultNetexIdValidator.validateToValueIndex(id));

		String nordic = "AAA:Type:ØÆÅ";
		assertEquals(9, defaultNetexIdValidator.validateToValueIndex(nordic));
	}

	@Test
	public void testValidateToValueIndex_whenInvalid_thenReturnMinusOne() {
		assertEquals(-1, defaultNetexIdValidator.validateToValueIndex(null));
		assertEquals(-1, defaultNetexIdValidator.validateToValueIndex("AAA:BBB"));
		assertEquals(-1, defaultNetexIdValidator.validateToValueIndex("AAA::BBB"));
		assertEquals(-1, defaultNetexIdValidator.validateToValueIndex("AAA:BBB:@"));
		assertEquals(-1, defaultNetexIdValidator.validateToValueIndex("AAA!:BBB:CCC"));
	}

	@Test
	public void testValidateWithOffset_whenValidSubsequence_thenReturnTrue() {
		String withPrefix = "XXAAA:BBB:CCC";
		assertTrue(defaultNetexIdValidator.validate(withPrefix, 2, withPrefix.length() - 2));

		String withSuffix = "AAA:BBB:CCCZZ";
		assertTrue(defaultNetexIdValidator.validate(withSuffix, 0, withSuffix.length()));
	}

	@Test
	public void testValidateWithOffset_whenTrailingCharactersOutsideEndIndex_thenIgnoreThem() {
		String bounded = "XXAAA:BBB:CCC!!";
		assertTrue(defaultNetexIdValidator.validate(bounded, 2, bounded.length() - 4));
	}

	@Test
	public void testValidateWithOffset_whenTooShortSubsequence_thenReturnFalse() {
		String id = "AAA:BBB:CCC";
		assertFalse(defaultNetexIdValidator.validate(id, 0, 5));
		assertFalse(defaultNetexIdValidator.validate("XXAAA:BBB:CCC", 2, 7));
	}

	@Test
	public void testValidateWithOffset_whenMissingTypeSeparator_thenReturnFalse() {
		String invalid = "XXXAABBB:CCC";
		assertFalse(defaultNetexIdValidator.validate(invalid, 2, invalid.length()));
	}

	@Test
	public void testValidateWithOffset_whenCodespaceInvalid_thenReturnFalse() {
		String invalidCodespace = "XXA1A:BBB:CCC";
		assertFalse(defaultNetexIdValidator.validate(invalidCodespace, 2, invalidCodespace.length()));
	}

}
