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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SimdNetexIdValidator}.
 *
 * <p>The SIMD implementation must produce identical results to {@link DefaultNetexIdValidator}.
 * The test cases are intentionally aligned with {@link DefaultNetexIdValidatorTest} so any
 * divergence is immediately visible.</p>
 */
public class SimdNetexIdValidatorTest {

	private final SimdNetexIdValidator validator = SimdNetexIdValidator.getInstance();

	@Test
	public void testEnglishValueCharacters() {
		assertTrue(validator.validate("AAA:BBB:ABZabz"));
	}

	@Test
	public void testNordicValueCharacters() {
		assertTrue(validator.validate("AAA:BBB:ØÆÅøæå"));
	}

	@Test
	public void testSpecialValueCharacters() {
		assertTrue(validator.validate("AAA:BBB:_-\\"));
	}

	@Test
	public void testIllegalValueCharactersLow() {
		assertFalse(validator.validate("AAA:BBB:!()"));
	}

	@Test
	public void testIllegalValueCharactersHigh() {
		assertFalse(validator.validate("AAA:BBB:Cÿ"));
	}

	@Test
	public void testIllegalTypeCharactersLow() {
		assertFalse(validator.validate("AAA:!():BBB"));
	}

	@Test
	public void testIllegalTypeCharactersHigh() {
		assertFalse(validator.validate("AAA:Cÿ:BBB"));
	}

	@Test
	public void testIllegalCodespaceCharactersLow() {
		assertFalse(validator.validate("!():AAA:BBB"));
	}

	@Test
	public void testIllegalCodespaceCharactersHigh() {
		assertFalse(validator.validate("CÿC:AAA:BBB"));
	}

	@Test
	public void testEnglishTypeCharacters() {
		assertTrue(validator.validate("AAA:BBB:CCC"));
		assertTrue(validator.validate("AAA:bbb:CCC"));
	}

	@Test
	public void testTypeCharactersLow() {
		assertFalse(validator.validateType("!()"));
	}

	@Test
	public void testTypeCharactersHigh() {
		assertFalse(validator.validateType("CÿC"));
	}

	@Test
	public void testIllegalTypeCharacters() {
		assertFalse(validator.validate("AAA:ØKS:CCC"));
	}

	@Test
	public void testIllegalCodespaceCharacters() {
		assertFalse(validator.validate("AAA!:BBB:CCC"));
	}

	@Test
	public void testMinimumLengthValid() {
		assertTrue(validator.validate("AAA:B:C"));
	}

	@Test
	public void testMinimumLengthInvalid() {
		assertFalse(validator.validate("AA:B:C"));
	}

	@Test
	public void testDigitInType() {
		assertFalse(validator.validate("AAA:B1B:CCC"));
	}

	@Test
	public void testLowercaseCodespace() {
		assertFalse(validator.validate("aaa:Type:Val"));
		assertFalse(validator.validateCodespace("aaa"));
	}

	@Test
	public void testNull() {
		assertFalse(validator.validate(null));
		assertFalse(validator.validateType(null));
		assertFalse(validator.validateCodespace(null));
		assertFalse(validator.validateValue(null));

		assertFalse(validator.validate(null, 0, 3));
		assertFalse(validator.validateType(null, 0, 3));
		assertFalse(validator.validateCodespace(null, 0, 3));
		assertFalse(validator.validateValue(null, 0, 3));
	}

	@Test
	public void testDeprecatedValidateWithOffset() {
		assertTrue(validator.validate("XAAA:BBB:CCCY", 1, 11));
		assertFalse(validator.validate("XAAA:BBB:CCC!Y", 1, 12));
	}

	@Test
	public void testTypeWithNoSecondSeparator() {
		assertFalse(validator.validate("AAA:BBBxCCC"));
	}

	@Test
	public void testEmptyType() {
		assertFalse(validator.validate("AAA::XXXXX"));
	}

	@Test
	public void testValidateToValueIndex() {
		assertEquals(validator.validateToValueIndex(null), -1);
		assertEquals(validator.validateToValueIndex("ABC"), -1);
		assertEquals(validator.validateToValueIndex("ABC:DEF"), -1);
		assertEquals(validator.validateToValueIndex("ABC:DEF:GH"), 8);
		assertEquals(validator.validateToValueIndex("ABC:DEF!GH"), -1);
		assertEquals(validator.validateToValueIndex("ABC!DEF:GH"), -1);
		assertEquals(validator.validateToValueIndex("ABCD:DEF:GH"), -1);
		assertEquals(validator.validateToValueIndex("ABCD:123:GH"), -1);
		assertEquals(validator.validateToValueIndex("123:DEF:GH"), -1);
		assertEquals(validator.validateToValueIndex("ABC:DEF:"), -1);
		assertEquals(validator.validateToValueIndex("ABC:DEF:..."), -1);
		assertEquals(validator.validateToValueIndex("ABC::GH"), -1);
	}

	@Test
	public void testLongId() {
		// ID with a value field that exceeds the SIMD vector width to exercise the loopBound path
		assertTrue(validator.validate("SJN:JourneyPattern:1_2058545094_1_1_1_1_1_1_79"));
		assertTrue(validator.validate("TST:ServiceCalendarFrame:ServiceCalendar-2021"));
	}

	@Test
	public void testVeryLongValue() {
		// Value longer than the widest SIMD vector (512-bit = 64 bytes) to exercise the vectorised loop.
		String value64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789AB";
		assertTrue(validator.validate("AAA:BBB:" + value64));
		// invalid char in the long portion
		assertFalse(validator.validate("AAA:BBB:" + value64.substring(0, 32) + "!" + value64.substring(33)));
		// Norwegian char at the start of the long portion (falls back to scalar in mid-loop)
		assertTrue(validator.validate("AAA:BBB:" + value64 + "Æ"));
		// Norwegian char in middle of the long value to exercise the non-ASCII break-to-scalar path
		assertTrue(validator.validate("AAA:BBB:" + value64.substring(0, 32) + "Æ" + value64.substring(32)));
	}

	@Test
	public void testVeryLongType() {
		// Type longer than the widest SIMD vector to exercise the vectorised type-validation loop.
		String type64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzABCDEFGHIJKL";
		assertTrue(validator.validateType(type64));
		assertFalse(validator.validateType(type64.substring(0, 32) + "1" + type64.substring(33)));
	}

	@Test
	public void testNetexIdValidatorBuilder() {
		NetexIdValidator simd = NetexIdValidatorBuilder.newInstance().simd().build();
		assertTrue(simd instanceof SimdNetexIdValidator);
		assertTrue(simd.validate("AAA:BBB:CCC"));

		NetexIdValidator scalar = NetexIdValidatorBuilder.newInstance().build();
		assertTrue(scalar instanceof DefaultNetexIdValidator);
		assertTrue(scalar.validate("AAA:BBB:CCC"));
	}
}
