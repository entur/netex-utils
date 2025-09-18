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

import static no.entur.abt.netex.id.NetexIdTypes.CUSTOMER;
import static no.entur.abt.netex.id.NetexIdTypes.CUSTOMER_ACCOUNT;
import static no.entur.abt.netex.id.NetexIdTypes.FARE_CONTRACT;
import static no.entur.abt.netex.id.NetexIdTypes.SECURITY_POLICY;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import no.entur.abt.netex.id.predicate.NetexIdPredicate;
import no.entur.abt.netex.id.predicate.NetexIdTypePredicate;
import no.entur.abt.netex.utils.IllegalNetexIDException;

/**
 *
 * Put the new validators/parsers through the same tests as the legacy netex utils.
 *
 */

public class NetexIdUtilsTest {

	private DefaultNetexIdValidator validator = new DefaultNetexIdValidator();
	private NetexIdValidatingParser parser = new NetexIdValidatingParser();

	@Test
	public void isValid_whenNotValid_thenReturnFalse() {
		assertFalse(validator.validate(""));
		assertFalse(validator.validate("X:Customer:1"));
		assertFalse(validator.validate("XX:Customer:1"));
		assertFalse(validator.validate("XXX:CustomerAccount"));
		assertFalse(validator.validate("XXX:Customer:@"));
		assertFalse(validator.validate("XXX:Customer:"));
		assertFalse(validator.validate(":Customer:"));
		assertFalse(validator.validate("XXX::"));
		assertFalse(validator.validate("XXX::1"));
		assertFalse(validator.validate(":::"));
        fail("Tesdddtdf");
	}

	@Test
	public void isValid_whenValid_thenReturnTrue() {
		assertTrue(validator.validate("XXX:Customer:1"));
		assertTrue(validator.validate("XXX:Customer:a"));
		assertTrue(validator.validate("XXX:Customer:æøåÆØÅ"));
	}

	@Test
	public void assertValidOfType_whenValid_thenDoNotThrow() {
		assertDoesNotThrow(() -> assertValidOfType("XXX:Customer:1", CUSTOMER));
		assertDoesNotThrow(() -> assertValidOfType("XXX:FareContract:a", FARE_CONTRACT));
		assertDoesNotThrow(() -> assertValidOfType("XXX:SecurityPolicy:æøåÆØÅ", SECURITY_POLICY));
	}

	@Test
	public void assertValidOfType_whenInvalid_thenThrowIllegalNetexIDException() {
		assertThrows(IllegalNetexIDException.class, () -> assertValidOfType("XXX:Customer:1", CUSTOMER_ACCOUNT));
		assertThrows(IllegalNetexIDException.class, () -> assertValidOfType("XXX:CustomerAccount:", CUSTOMER_ACCOUNT));
	}

	private void assertValidOfType(String id, String type) {
		if (!validator.validate(id)) {
			throw new IllegalNetexIDException(id);
		}
		NetexIdPredicate build = new NetexIdTypePredicate(type);

		if (!build.test(id)) {
			throw new IllegalNetexIDException(id);
		}
	}

	@Test
	public void getCodespace_whenInvalidThenThrowIllegalNetexIDException() {
		assertThrows(IllegalNetexIDException.class, () -> parser.getCodespace(null));
		assertThrows(IllegalNetexIDException.class, () -> parser.getCodespace(""));
		assertThrows(IllegalNetexIDException.class, () -> parser.getCodespace("TST"));
		assertThrows(IllegalNetexIDException.class, () -> parser.getCodespace("TST:Type"));
		assertThrows(IllegalNetexIDException.class, () -> parser.getCodespace("TST:Type:"));
	}

	@Test
	public void getType_whenInvalidThenThrowIllegalNetexIDException() {
		assertThrows(IllegalNetexIDException.class, () -> parser.getType(null));
		assertThrows(IllegalNetexIDException.class, () -> parser.getType(""));
		assertThrows(IllegalNetexIDException.class, () -> parser.getType("TST"));
		assertThrows(IllegalNetexIDException.class, () -> parser.getType("TST:Type"));
		assertThrows(IllegalNetexIDException.class, () -> parser.getType("TST:Type:"));
	}

	@Test
	public void getValue_whenInvalidThenThrowIllegalNetexIDException() {
		assertThrows(IllegalNetexIDException.class, () -> parser.getValue(null));
		assertThrows(IllegalNetexIDException.class, () -> parser.getValue(""));
		assertThrows(IllegalNetexIDException.class, () -> parser.getValue("TST"));
		assertThrows(IllegalNetexIDException.class, () -> parser.getValue("TST:Type"));
		assertThrows(IllegalNetexIDException.class, () -> parser.getValue("TST:Type:"));
	}

}
