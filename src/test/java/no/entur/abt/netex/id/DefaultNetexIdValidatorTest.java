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
	public void testMinimumLengthValid() {
		// minimum valid NeTEx ID is XXX:X:X (length 7)
		assertTrue(defaultNetexIdValidator.validate("AAA:B:C"));
	}

	@Test
	public void testMinimumLengthInvalid() {
		// length 6 (one short of minimum) should be invalid
		assertFalse(defaultNetexIdValidator.validate("AA:B:C"));
	}

	@Test
	public void testDigitInType() {
		assertFalse(defaultNetexIdValidator.validate("AAA:B1B:CCC"));
	}

	@Test
	public void testLowercaseCodespace() {
		assertFalse(defaultNetexIdValidator.validate("aaa:Type:Val"));
		assertFalse(defaultNetexIdValidator.validateCodespace("aaa"));
	}

	@Test
	public void testNull() {
		assertFalse(defaultNetexIdValidator.validateType(null));
		assertFalse(defaultNetexIdValidator.validateCodespace(null));
		assertFalse(defaultNetexIdValidator.validateValue(null));

		assertFalse(defaultNetexIdValidator.validate(null, 0, 3));
		assertFalse(defaultNetexIdValidator.validateType(null, 0, 3));
		assertFalse(defaultNetexIdValidator.validateCodespace(null, 0, 3));
		assertFalse(defaultNetexIdValidator.validateValue(null, 0, 3));
	}

	@Test
	public void testDeprecatedValidateWithOffset() {
		// non-null path: extracts subsequence and validates it
		assertTrue(defaultNetexIdValidator.validate("XAAA:BBB:CCCY", 1, 11));
		assertFalse(defaultNetexIdValidator.validate("XAAA:BBB:CCC!Y", 1, 12));
	}

	@Test
	public void testTypeWithNoSecondSeparator() {
		// type scan reaches end of string without finding ':', so last == -1 (condition 1 in line 133)
		assertFalse(defaultNetexIdValidator.validate("AAA:BBBxCCC"));
	}

	@Test
	public void testEmptyType() {
		// empty type: validateTypeToIndex returns 4 with charAt(4)==':'; last > NETEX_ID_CODESPACE_LENGTH+1 is false (condition 3 in line 133)
		assertFalse(defaultNetexIdValidator.validate("AAA::XXXXX"));
	}

}
