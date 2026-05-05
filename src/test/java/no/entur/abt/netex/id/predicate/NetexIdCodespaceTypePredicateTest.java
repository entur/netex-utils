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

import no.entur.abt.netex.utils.IllegalNetexIDException;
import org.junit.jupiter.api.Test;

public class NetexIdCodespaceTypePredicateTest {

	@Test
	public void testConstructor() {
		assertThrows(IllegalArgumentException.class, () -> {
			new NetexIdCodespaceTypePredicate("AB", "x");
		});
		assertThrows(IllegalArgumentException.class, () -> {
			new NetexIdCodespaceTypePredicate("ABC", "");
		});
	}

	// NetexIdCodespaceTypeValidatingPredicate — matching codespace+type with an invalid value
	@Test
	public void testCodespaceTypeValidating_matchingPrefixInvalidValue_throwsException() {
		NetexIdCodespaceTypeValidatingPredicate predicate = new NetexIdCodespaceTypeValidatingPredicate("AAA", "Network");
		assertThrows(IllegalNetexIDException.class, () -> predicate.test("AAA:Network:12!3"));
	}

	// NetexIdCodespaceTypeValidatingPredicate — non-matching codespace with an invalid id
	@Test
	public void testCodespaceTypeValidating_nonMatchingCodespaceInvalidId_throwsException() {
		NetexIdCodespaceTypeValidatingPredicate predicate = new NetexIdCodespaceTypeValidatingPredicate("AAA", "Network");
		assertThrows(IllegalNetexIDException.class, () -> predicate.test("BBB:Network:12!3"));
	}

	// NetexIdTypeValidatingPredicate — matching type with an invalid codespace
	@Test
	public void testTypeValidating_matchingTypeInvalidCodespace_throwsException() {
		NetexIdTypeValidatingPredicate predicate = new NetexIdTypeValidatingPredicate("Network");
		assertThrows(IllegalNetexIDException.class, () -> predicate.test("aa!:Network:123"));
	}

	// NetexIdTypeValidatingPredicate — non-matching type with an invalid id
	@Test
	public void testTypeValidating_nonMatchingTypeInvalidId_throwsException() {
		NetexIdTypeValidatingPredicate predicate = new NetexIdTypeValidatingPredicate("Network");
		assertThrows(IllegalNetexIDException.class, () -> predicate.test("AAA:OtherType:12!3"));
	}

	// NetexIdCodespaceValidatingPredicate — invalid id
	@Test
	public void testCodespaceValidating_invalidId_throwsException() {
		NetexIdCodespaceValidatingPredicate predicate = new NetexIdCodespaceValidatingPredicate("AAA");
		assertThrows(IllegalNetexIDException.class, () -> predicate.test("AAA:Network:12!3"));
	}

	@Test
	public void testCodespacePredicate_shortInput_returnsFalse() {
		NetexIdCodespacePredicate predicate = new NetexIdCodespacePredicate("AAA");
		assertFalse(predicate.test("AAA"));
	}

	// NetexIdCodespaceTypePredicate — null input returns false (not NPE)
	@Test
	public void testCodespaceTypePredicate_null_returnsFalse() {
		NetexIdCodespaceTypePredicate predicate = new NetexIdCodespaceTypePredicate("AAA", "Network");
		assertFalse(predicate.test(null));
	}

	@Test
	public void testCodespacePredicate_nullInput_returnsFalse() {
		NetexIdCodespacePredicate predicate = new NetexIdCodespacePredicate("AAA");
		assertFalse(predicate.test(null));
	}

	// NetexIdTypePredicate — null input returns false (not NPE)
	@Test
	public void testTypePredicate_null_returnsFalse() {
		NetexIdTypePredicate predicate = new NetexIdTypePredicate("Network");
		assertFalse(predicate.test(null));
	}
}
