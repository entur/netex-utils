package no.entur.abt.netex.id.predicate;

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

import no.entur.abt.netex.utils.IllegalNetexIDException;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link SimdNetexIdCodespaceTypePredicate} and {@link SimdNetexIdTypePredicate}.
 *
 * <p>Results must be identical to the scalar predicates {@link NetexIdCodespaceTypePredicate} and
 * {@link NetexIdTypePredicate}.</p>
 */
public class SimdNetexIdPredicateTest {

	// -------------------------------------------------------------------------
	// SimdNetexIdCodespaceTypePredicate
	// -------------------------------------------------------------------------

	@Test
	public void testCodespaceTypeMatch() {
		SimdNetexIdCodespaceTypePredicate p = new SimdNetexIdCodespaceTypePredicate("TST", "FareZone");
		assertTrue(p.test("TST:FareZone:1"));
		assertTrue(p.test("TST:FareZone:SomeLongerValue"));
	}

	@Test
	public void testCodespaceTypeNoMatch() {
		SimdNetexIdCodespaceTypePredicate p = new SimdNetexIdCodespaceTypePredicate("TST", "FareZone");
		assertFalse(p.test("TST:OtherType:1"));
		assertFalse(p.test("SJN:FareZone:1"));
		assertFalse(p.test("TST:FareZon:1"));
		assertFalse(p.test("TST:FareZoneX:1"));
	}

	@Test
	public void testCodespaceTypeNull() {
		SimdNetexIdCodespaceTypePredicate p = new SimdNetexIdCodespaceTypePredicate("TST", "FareZone");
		assertFalse(p.test(null));
	}

	@Test
	public void testCodespaceTypeTooShort() {
		SimdNetexIdCodespaceTypePredicate p = new SimdNetexIdCodespaceTypePredicate("TST", "FareZone");
		assertFalse(p.test("TST:Fare:1"));
		assertFalse(p.test("TST:FareZone"));
	}

	@Test
	public void testCodespaceTypeWithCharSequence() {
		SimdNetexIdCodespaceTypePredicate p = new SimdNetexIdCodespaceTypePredicate("TST", "FareZone");
		// Exercise the non-String CharSequence code path (scalar fallback in testSimd guard)
		assertTrue(p.test(new StringBuilder("TST:FareZone:1")));
		assertFalse(p.test(new StringBuilder("SJN:FareZone:1")));
	}

	@Test
	public void testCodespaceTypeNonAsciiInput() {
		SimdNetexIdCodespaceTypePredicate p = new SimdNetexIdCodespaceTypePredicate("TST", "FareZone");
		// non-ASCII char in prefix should not match
		assertFalse(p.test("TØT:FareZone:1"));
	}

	@Test
	public void testCodespaceTypeLongTypeName() {
		// Type name longer than a single SIMD vector width - exercises long-prefix code path.
		// Create a type of length 40 to exceed common vector widths (16/32 bytes).
		String longType = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSs";
		SimdNetexIdCodespaceTypePredicate p = new SimdNetexIdCodespaceTypePredicate("TST", longType);
		String id = "TST:" + longType + ":value";
		assertTrue(p.test(id));
		assertFalse(p.test("TST:NotTheRightType:value"));
	}

	@Test
	public void testCodespaceTypeInvalidInputThrows() {
		assertThrows(IllegalNetexIDException.class, () -> new SimdNetexIdCodespaceTypePredicate(null, "FareZone"));
		assertThrows(IllegalNetexIDException.class, () -> new SimdNetexIdCodespaceTypePredicate("AB", "FareZone"));
		assertThrows(IllegalNetexIDException.class, () -> new SimdNetexIdCodespaceTypePredicate("TST", null));
		assertThrows(IllegalNetexIDException.class, () -> new SimdNetexIdCodespaceTypePredicate("TST", ""));
	}

	// -------------------------------------------------------------------------
	// SimdNetexIdTypePredicate
	// -------------------------------------------------------------------------

	@Test
	public void testTypeMatch() {
		SimdNetexIdTypePredicate p = new SimdNetexIdTypePredicate("FareZone");
		assertTrue(p.test("TST:FareZone:1"));
		assertTrue(p.test("SJN:FareZone:123"));
		assertTrue(p.test("NSR:FareZone:SomeLongerValue"));
	}

	@Test
	public void testTypeNoMatch() {
		SimdNetexIdTypePredicate p = new SimdNetexIdTypePredicate("FareZone");
		assertFalse(p.test("TST:OtherType:1"));
		assertFalse(p.test("TST:FareZon:1"));
		assertFalse(p.test("TST:FareZoneX:1"));
	}

	@Test
	public void testTypeNull() {
		SimdNetexIdTypePredicate p = new SimdNetexIdTypePredicate("FareZone");
		assertFalse(p.test(null));
	}

	@Test
	public void testTypeTooShort() {
		SimdNetexIdTypePredicate p = new SimdNetexIdTypePredicate("FareZone");
		assertFalse(p.test("TST:FareZone"));
		assertFalse(p.test("TST:Fare:1"));
	}

	@Test
	public void testTypeWithCharSequence() {
		SimdNetexIdTypePredicate p = new SimdNetexIdTypePredicate("FareZone");
		assertTrue(p.test(new StringBuilder("TST:FareZone:1")));
		assertFalse(p.test(new StringBuilder("TST:OtherType:1")));
	}

	@Test
	public void testTypeNonAsciiInput() {
		SimdNetexIdTypePredicate p = new SimdNetexIdTypePredicate("FareZone");
		// Non-ASCII char in the type region should not match
		assertFalse(p.test("TST:FæreZone:1"));
	}

	@Test
	public void testTypeLongTypeName() {
		String longType = "AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpQqRrSs";
		SimdNetexIdTypePredicate p = new SimdNetexIdTypePredicate(longType);
		String id = "TST:" + longType + ":value";
		assertTrue(p.test(id));
		assertFalse(p.test("TST:NotTheRightType:value"));
	}

	@Test
	public void testTypeNoFirstColon() {
		SimdNetexIdTypePredicate p = new SimdNetexIdTypePredicate("FareZone");
		// Force scalar path with wrong first colon
		assertFalse(p.test(new StringBuilder("TSTXFareZone:1")));
	}
}
