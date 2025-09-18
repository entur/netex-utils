package no.entur.abt.netex.utils;

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

import static no.entur.abt.netex.id.NetexIdTypes.CUSTOMER;
import static no.entur.abt.netex.id.NetexIdTypes.CUSTOMER_ACCOUNT;
import static no.entur.abt.netex.id.NetexIdTypes.FARE_CONTRACT;
import static no.entur.abt.netex.id.NetexIdTypes.SECURITY_POLICY;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NetexIdUtilsTest {

	@Test
	public void isValid_whenNotValid_thenReturnFalse() {
		assertFalse(NetexIdUtils.isValid(""));
		assertFalse(NetexIdUtils.isValid("X:Customer:1"));
		assertFalse(NetexIdUtils.isValid("XX:Customer:1"));
		assertFalse(NetexIdUtils.isValid("XXX:CustomerAccount"));
		assertFalse(NetexIdUtils.isValid("XXX:Customer:@"));
		assertFalse(NetexIdUtils.isValid("XXX:Customer:"));
		assertFalse(NetexIdUtils.isValid(":Customer:"));
		assertFalse(NetexIdUtils.isValid("XXX::"));
		assertFalse(NetexIdUtils.isValid("XXX::1"));
		assertFalse(NetexIdUtils.isValid(":::"));
        fail("adddddf");
	}

	@Test
	public void isValid_whenValid_thenReturnTrue() {
		assertTrue(NetexIdUtils.isValid("XXX:Customer:1"));
		assertTrue(NetexIdUtils.isValid("XXX:Customer:a"));
		assertTrue(NetexIdUtils.isValid("XXX:Customer:æøåÆØÅ"));
	}

	@Test
	public void assertValidOfType_whenValid_thenDoNotThrow() {
		assertDoesNotThrow(() -> NetexIdUtils.assertValidOfType("XXX:Customer:1", CUSTOMER));
		assertDoesNotThrow(() -> NetexIdUtils.assertValidOfType("XXX:FareContract:a", FARE_CONTRACT));
		assertDoesNotThrow(() -> NetexIdUtils.assertValidOfType("XXX:SecurityPolicy:æøåÆØÅ", SECURITY_POLICY));
	}

	@Test
	public void assertValidOfType_whenInvalid_thenThrowIllegalNetexIDException() {
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.assertValidOfType("XXX:Customer:1", CUSTOMER_ACCOUNT));
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.assertValidOfType("XXX:CustomerAccount:", CUSTOMER_ACCOUNT));
	}

	@Test
	public void getCodespace_whenInvalidThenThrowIllegalNetexIDException() {
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.getCodespace(null));
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.getCodespace(""));
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.getCodespace("TST"));
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.getCodespace("TST:Type"));
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.getCodespace("TST:Type:"));
	}

	@Test
	public void getType_whenInvalidThenThrowIllegalNetexIDException() {
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.getType(null));
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.getType(""));
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.getType("TST"));
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.getType("TST:Type"));
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.getType("TST:Type:"));
	}

	@Test
	public void getValue_whenInvalidThenThrowIllegalNetexIDException() {
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.getValue(null));
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.getValue(""));
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.getValue("TST"));
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.getValue("TST:Type"));
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.getValue("TST:Type:"));
	}

	@Test
	public void createFrom_whenValid() {
		assertEquals(NetexIdUtils.createFrom("XXX:FareContract:a", "abc"), "XXX:FareContract:abc");
	}

	@Test
	public void createFrom_whenInvalidThenThrowIllegalNetexIDException() {
		assertThrows(IllegalNetexIDException.class, () -> NetexIdUtils.createFrom("XXX:FareContract", "abc"));
	}
}
