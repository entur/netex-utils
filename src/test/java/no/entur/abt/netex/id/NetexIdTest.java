package no.entur.abt.netex.id;

import static no.entur.abt.netex.id.NetexIdTypes.CUSTOMER;
import static no.entur.abt.netex.id.NetexIdTypes.CUSTOMER_ACCOUNT;
import static no.entur.abt.netex.id.NetexIdTypes.FARE_CONTRACT;
import static no.entur.abt.netex.id.NetexIdTypes.SECURITY_POLICY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import no.entur.abt.netex.utils.IllegalNetexIDException;

/*-
 * #%L
 * Netex utils
 * %%
 * Copyright (C) 2019 - 2026 Entur
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

public class NetexIdTest {

	@Test
	public void isValid_whenNotValid_thenReturnFalse() {
		assertFalse(NetexId.isValid(""));
		assertFalse(NetexId.isValid("X:Customer:1"));
		assertFalse(NetexId.isValid("XX:Customer:1"));
		assertFalse(NetexId.isValid("XXX:CustomerAccount"));
		assertFalse(NetexId.isValid("XXX:Customer:@"));
		assertFalse(NetexId.isValid("XXX:Customer:"));
		assertFalse(NetexId.isValid(":Customer:"));
		assertFalse(NetexId.isValid("XXX::"));
		assertFalse(NetexId.isValid("XXX::1"));
		assertFalse(NetexId.isValid(":::"));
	}

	@Test
	public void isValid_whenValid_thenReturnTrue() {
		assertTrue(NetexId.isValid("XXX:Customer:1"));
		assertTrue(NetexId.isValid("XXX:Customer:a"));
		assertTrue(NetexId.isValid("XXX:Customer:æøåÆØÅ"));
	}

	@Test
	public void assertValidOfType_whenValid_thenDoNotThrow() {
		assertDoesNotThrow(() -> NetexId.assertValidOfType("XXX:Customer:1", CUSTOMER));
		assertDoesNotThrow(() -> NetexId.assertValidOfType("XXX:FareContract:a", FARE_CONTRACT));
		assertDoesNotThrow(() -> NetexId.assertValidOfType("XXX:SecurityPolicy:æøåÆØÅ", SECURITY_POLICY));
	}

	@Test
	public void assertValidOfType_whenInvalid_thenThrowIllegalNetexIDException() {
		assertThrows(IllegalNetexIDException.class, () -> NetexId.assertValidOfType("XXX:Customer:1", CUSTOMER_ACCOUNT));
		assertThrows(IllegalNetexIDException.class, () -> NetexId.assertValidOfType("XXX:CustomerAccount:", CUSTOMER_ACCOUNT));
	}

	@Test
	public void getCodespace_whenInvalidThenThrowIllegalNetexIDException() {
		assertThrows(IllegalNetexIDException.class, () -> NetexId.getCodespace(null));
		assertThrows(IllegalNetexIDException.class, () -> NetexId.getCodespace(""));
		assertThrows(IllegalNetexIDException.class, () -> NetexId.getCodespace("TST"));
		assertThrows(IllegalNetexIDException.class, () -> NetexId.getCodespace("TST:Type"));
		assertThrows(IllegalNetexIDException.class, () -> NetexId.getCodespace("TST:Type:"));
	}

	@Test
	public void getCodespace_whenValid_thenReturnCodespace() {
		assertEquals("TST", NetexId.getCodespace("TST:FareContract:abc"));
	}

	@Test
	public void getType_whenInvalidThenThrowIllegalNetexIDException() {
		assertThrows(IllegalNetexIDException.class, () -> NetexId.getType(null));
		assertThrows(IllegalNetexIDException.class, () -> NetexId.getType(""));
		assertThrows(IllegalNetexIDException.class, () -> NetexId.getType("TST"));
		assertThrows(IllegalNetexIDException.class, () -> NetexId.getType("TST:Type"));
		assertThrows(IllegalNetexIDException.class, () -> NetexId.getType("TST:Type:"));
	}

	@Test
	public void getType_whenValid_thenReturnType() {
		assertEquals("FareContract", NetexId.getType("TST:FareContract:abc"));
	}

	@Test
	public void getValue_whenInvalidThenThrowIllegalNetexIDException() {
		assertThrows(IllegalNetexIDException.class, () -> NetexId.getValue(null));
		assertThrows(IllegalNetexIDException.class, () -> NetexId.getValue(""));
		assertThrows(IllegalNetexIDException.class, () -> NetexId.getValue("TST"));
		assertThrows(IllegalNetexIDException.class, () -> NetexId.getValue("TST:Type"));
		assertThrows(IllegalNetexIDException.class, () -> NetexId.getValue("TST:Type:"));
	}

	@Test
	public void getValue_whenValid_thenReturnValue() {
		assertEquals("abc", NetexId.getValue("TST:FareContract:abc"));
	}

	@Test
	public void assertValid_whenValid_thenDoNotThrow() {
		assertDoesNotThrow(() -> NetexId.assertValid("TST:FareContract:abc"));
	}

	@Test
	public void assertValid_whenInvalid_thenThrowIllegalNetexIDException() {
		assertThrows(IllegalNetexIDException.class, () -> NetexId.assertValid("TST:FareContract"));
	}

	@Test
	public void createId_whenValid_thenReturnId() {
		assertEquals("XXX:FareContract:abc", NetexId.createId("XXX", "FareContract", "abc"));
	}

	@Test
	public void createFrom_whenValid_thenReturnId() {
		assertEquals("XXX:FareContract:abc", NetexId.createFrom("XXX:FareContract:a", "abc"));
	}

	@Test
	public void createFrom_whenNullValuePart_thenThrowNullPointerException() {
		assertThrows(NullPointerException.class, () -> NetexId.createFrom("XXX:FareContract:a", null));
	}

	@Test
	public void createFrom_whenInvalid_thenThrowIllegalNetexIDException() {
		assertThrows(IllegalNetexIDException.class, () -> NetexId.createFrom("XXX:FareContract", "abc"));
	}

}

