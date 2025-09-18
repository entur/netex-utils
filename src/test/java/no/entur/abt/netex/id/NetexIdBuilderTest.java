package no.entur.abt.netex.id;

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

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NetexIdBuilderTest {

	@Test
	public void testCodespace() {
		String build = NetexIdBuilder.newInstance().withCodespace("AAA").withType("Network").withValue("123").build();
		assertEquals("AAA:Network:123", build);
	}

	@Test
	public void testInvalidCodespaceInput() {
		assertThrows(IllegalStateException.class, () -> {
			NetexIdBuilder.newInstance().withType("Network").withValue("123").build();
		});
		assertThrows(IllegalStateException.class, () -> {
			NetexIdBuilder.newInstance().withCodespace("AAA").withValue("123").build();
		});
		assertThrows(IllegalStateException.class, () -> {
			NetexIdBuilder.newInstance().withCodespace("AAA").withType("Network").build();
		});
		assertThrows(IllegalStateException.class, () -> {
			NetexIdBuilder.newInstance().withCodespace("AA").withType("Network").withValue("123").build();
		});
		assertThrows(IllegalStateException.class, () -> {
			NetexIdBuilder.newInstance().withCodespace("AAAA").withType("Network").withValue("123").build();
		});
		assertThrows(IllegalStateException.class, () -> {
			NetexIdBuilder.newInstance().withCodespace("AAA").withType("").withValue("123").build();
		});
		assertThrows(IllegalStateException.class, () -> {
			NetexIdBuilder.newInstance().withCodespace("AAA").withType("Network").withValue("").build();
		});
		assertThrows(IllegalStateException.class, () -> {
			NetexIdBuilder.newInstance().withCodespace("AAA").withType("Network!").build();
		});
        fail();
	}

}
