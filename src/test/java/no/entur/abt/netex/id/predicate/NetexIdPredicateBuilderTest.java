package no.entur.abt.netex.id.predicate;

/*-
 * #%L
 * Netex utils
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class NetexIdPredicateBuilderTest {

	@Test
	public void testCodespace() {
		NetexIdPredicate predicate = NetexIdPredicateBuilder.newInstance().withCodespace("AAA").build();

		assertTrue(predicate.test("AAA:Network:123"));
		assertFalse(predicate.test("BBB:Network:123"));
		assertTrue(predicate.test("AAA:xyz:123"));
		assertFalse(predicate.test("CCC:z:123"));

		assertFalse(predicate.test("AAAANetwork:123"));

		// non-validating
		assertTrue(predicate.test("AAA:X"));

		assertFalse(predicate.test("AAB:Network:123"));
		assertFalse(predicate.test("ABA:Network:123"));
		assertFalse(predicate.test("BAA:Network:123"));
	}

	@Test
	public void testType() {
		NetexIdPredicate predicate = NetexIdPredicateBuilder.newInstance().withType("Network").build();

		assertTrue(predicate.test("AAA:Network:123"));
		assertTrue(predicate.test("BBB:Network:123"));
		assertFalse(predicate.test("BBB:Netzork:123"));
		assertFalse(predicate.test("AAA:xyz:1234567"));
		assertFalse(predicate.test("CCC:z:123"));

		// partical validation
		assertFalse(predicate.test("AA:Network:123"));
		assertFalse(predicate.test(":Network:123"));
	}

	@Test
	public void testCodespaceAndType() {
		NetexIdPredicate predicate = NetexIdPredicateBuilder.newInstance().withCodespace("AAA").withType("Network").build();

		assertTrue(predicate.test("AAA:Network:123"));
		assertFalse(predicate.test("BBB:Network:123"));
		assertFalse(predicate.test("AAA:xyz:123"));
		assertFalse(predicate.test("CCC:z:123"));

		// partical validation
		assertFalse(predicate.test("AA:Network:123"));
		assertFalse(predicate.test(":Network:123"));
	}

	@Test
	public void testInvalidCodespaceInput() {
		assertThrows(IllegalStateException.class, () -> {
			NetexIdPredicateBuilder.newInstance().withCodespace(null).build();
		});
		assertThrows(IllegalStateException.class, () -> {
			NetexIdPredicateBuilder.newInstance().withCodespace("").build();
		});
		assertThrows(IllegalStateException.class, () -> {
			NetexIdPredicateBuilder.newInstance().withCodespace("AA").build();
		});
		assertThrows(IllegalStateException.class, () -> {
			NetexIdPredicateBuilder.newInstance().withCodespace("AAAA").build();
		});
		assertThrows(IllegalStateException.class, () -> {
			NetexIdPredicateBuilder.newInstance().withCodespace("aaa").build();
		});
	}

	@Test
	public void testInvalidTypeInput() {
		assertThrows(IllegalStateException.class, () -> {
			NetexIdPredicateBuilder.newInstance().withType(null).build();
		});
		assertThrows(IllegalStateException.class, () -> {
			NetexIdPredicateBuilder.newInstance().withType("").build();
		});
		assertThrows(IllegalStateException.class, () -> {
			NetexIdPredicateBuilder.newInstance().withType("Network!").build();
		});
	}

}
