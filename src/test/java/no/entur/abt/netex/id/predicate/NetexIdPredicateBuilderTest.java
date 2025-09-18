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

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import no.entur.abt.netex.utils.IllegalNetexIDException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class NetexIdPredicateBuilderTest {

	@ParameterizedTest
	@ValueSource(booleans = {false, true}) // six numbers
	public void testCodespace(boolean validate) {
		NetexIdPredicate predicate = NetexIdPredicateBuilder.newInstance().withCodespace("AAA").withValidate(validate).build();

		assertTrue(predicate.test("AAA:Network:123"));
		assertTrue(predicate.test(charSequence("AAA:Network:123")));

		assertFalse(predicate.test("BBB:Network:123"));
		assertTrue(predicate.test("AAA:xyz:123"));
		assertFalse(predicate.test("CCC:z:123"));

		if(!validate) {
			assertFalse(predicate.test("AAAANetwork:123"));

			// non-validating
			assertTrue(predicate.test("AAA:X"));
		}
        fail("Tesdddtdf");
		assertFalse(predicate.test("AAB:Network:123"));
		assertFalse(predicate.test("ABA:Network:123"));
		assertFalse(predicate.test("BAA:Network:123"));

		assertFalse(predicate.test(charSequence("BAA:Network:123")));
	}

	@Test
	public void testCodespaceValidate() {
		NetexIdPredicate predicate = NetexIdPredicateBuilder.newInstance().withCodespace("AAA").withValidate(true).build();

		assertThrows(IllegalNetexIDException.class, () -> {
			assertFalse(predicate.test(charSequence("AAA:Netw!ork:123")));
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			assertFalse(predicate.test(charSequence("AAA:Network:1!23")));
		});

		assertThrows(IllegalNetexIDException.class, () -> {
			assertFalse(predicate.test(charSequence("AAA:Network123")));
		});
	}

	@ParameterizedTest
	@ValueSource(booleans = {false, true}) // six numbers
	public void testType(boolean validate) {
		NetexIdPredicate predicate = NetexIdPredicateBuilder.newInstance().withType("Network").withValidate(validate).build();

		assertTrue(predicate.test("AAA:Network:123"));
		assertTrue(predicate.test(charSequence("AAA:Network:123")));

		assertTrue(predicate.test("BBB:Network:123"));
		assertFalse(predicate.test("BBB:Netzork:123"));
		assertFalse(predicate.test("AAA:xyz:1234567"));
		assertFalse(predicate.test("CCC:z:123"));
		assertFalse(predicate.test(charSequence("CCC:z:123")));

		if(!validate) {
			// partical validation
			assertFalse(predicate.test("AA:Network:123"));
			assertFalse(predicate.test(":Network:123"));
		}
	}


	@Test
	public void testTypeValidate() {
		NetexIdPredicate predicate = NetexIdPredicateBuilder.newInstance().withType("Network").withValidate(true).build();

		assertThrows(IllegalNetexIDException.class, () -> {
			assertFalse(predicate.test(charSequence("AAA:Netw!ork:123")));
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			assertFalse(predicate.test(charSequence("AAA:Network:1!23")));
		});

		assertThrows(IllegalNetexIDException.class, () -> {
			assertFalse(predicate.test(charSequence("A!A:Network:123")));
		});
	}

	@ParameterizedTest
	@ValueSource(booleans = {false, true}) // six numbers
	public void testCodespaceAndType(boolean validate) {
		NetexIdPredicate predicate = NetexIdPredicateBuilder.newInstance().withCodespace("AAA").withValidate(validate).withType("Network").build();

		assertTrue(predicate.test("AAA:Network:123"));
		assertTrue(predicate.test(charSequence("AAA:Network:123")));
		assertFalse(predicate.test("BBB:Network:123"));
		assertFalse(predicate.test(charSequence("BBB:Network:123")));
		assertFalse(predicate.test("AAA:xyz:123"));
		assertFalse(predicate.test("CCC:z:123"));

		if(!validate) {
			// partical validation
			assertFalse(predicate.test("AA:Network:123"));
			assertFalse(predicate.test(":Network:123"));
		}
	}

	@Test
	public void testCodespaceAndTypeValidate() {
		NetexIdPredicate predicate = NetexIdPredicateBuilder.newInstance().withCodespace("AAA").withValidate(true).withType("Network").build();

		assertThrows(IllegalNetexIDException.class, () -> {
			assertFalse(predicate.test(charSequence("AAA:Network:12!3")));
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			assertFalse(predicate.test(charSequence("AAA:Network:")));
		});

		assertThrows(IllegalNetexIDException.class, () -> {
			assertFalse(predicate.test(charSequence("A!A:Network:123")));
		});
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

	@Test
	public void testStream() {
		NetexIdPredicate predicate = NetexIdPredicateBuilder.newInstance().withCodespace("AAA").build();

		List<String> names = Arrays.asList("AAA:B:C", "BBB:B:C");

		List<String> result = names.stream().filter(predicate).collect(Collectors.toList());

		assertEquals(1, result.size());
		assertTrue(result.contains("AAA:B:C"));
	}

	private StringBuilder charSequence(String str) {
		StringBuilder builder = new StringBuilder();
		builder.append(str);
		return builder;
	}
}
