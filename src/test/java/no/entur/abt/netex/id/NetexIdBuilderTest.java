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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import no.entur.abt.netex.utils.IllegalNetexIDException;
import org.junit.jupiter.api.Test;

public class NetexIdBuilderTest {

	@Test
	public void testCodespace() {
		String build = NetexIdBuilder.newInstance().withCodespace("AAA").withType("Network").withValue("123").build();
		assertEquals("AAA:Network:123", build);
	}

	@Test
	public void testInvalidCodespaceInput() {
		assertThrows(IllegalNetexIDException.class, () -> {
			NetexIdBuilder.newInstance().withType("Network").withValue("123").build();
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			NetexIdBuilder.newInstance().withCodespace("AAA").withValue("123").build();
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			NetexIdBuilder.newInstance().withCodespace("AAA").withType("Network").build();
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			NetexIdBuilder.newInstance().withCodespace("AA").withType("Network").withValue("123").build();
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			NetexIdBuilder.newInstance().withCodespace("AAAA").withType("Network").withValue("123").build();
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			NetexIdBuilder.newInstance().withCodespace("AAA").withType("").withValue("123").build();
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			NetexIdBuilder.newInstance().withCodespace("AAA").withType("Network").withValue("").build();
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			NetexIdBuilder.newInstance().withCodespace("AAA").withType("Network!").build();
		});
	}

    @Test
    public void testBuilderFromExistingId() {
        assertEquals("AAA:Network:123", NetexIdBuilder.newInstance("AAA:Network:123").build());
    }

    @Test
    public void testBuilderFromExistingIdValue() {
        assertEquals("AAA:Network:456", NetexIdBuilder.newInstance("AAA:Network:123").withValue("456").build());
    }

    @Test
    public void testBuilderFromNullId() {
        assertThrows(IllegalNetexIDException.class, () -> NetexIdBuilder.newInstance(null));
    }

    @Test
    public void testBuilderFromInvalidId() {
        assertThrows(IllegalNetexIDException.class, () -> NetexIdBuilder.newInstance("AA:B"));
    }

    @Test
    public void testCreateCodespace() {
        String build = NetexIdBuilder.createId("AAA", "Network", "123");
        assertEquals("AAA:Network:123", build);
    }

    @Test
    public void testCreateInvalidCodespaceInput() {
        assertThrows(IllegalNetexIDException.class, () -> {
            NetexIdBuilder.createId(null,"Network", "123");
        });
        assertThrows(IllegalNetexIDException.class, () -> {
            NetexIdBuilder.createId("AAA", null, "123");
        });
        assertThrows(IllegalNetexIDException.class, () -> {
            NetexIdBuilder.createId("AAA", "Network", null);
        });
        assertThrows(IllegalNetexIDException.class, () -> {
            NetexIdBuilder.createId("AA", "Network", "123");
        });
        assertThrows(IllegalNetexIDException.class, () -> {
            NetexIdBuilder.createId("AAAA", "Network", "123");
        });
        assertThrows(IllegalNetexIDException.class, () -> {
            NetexIdBuilder.createId("AAA", "", "123");
        });
        assertThrows(IllegalNetexIDException.class, () -> {
            NetexIdBuilder.createId("AAA", "Network", "");
        });
        assertThrows(IllegalNetexIDException.class, () -> {
            NetexIdBuilder.createId("AAA", "Network!", null);
        });
    }

    @Test
    public void testCreateFromExistingId() {
        assertEquals("AAA:Network:456", NetexIdBuilder.createIdFrom("AAA:Network:123", "456"));
    }

    @Test
    public void testCreateFromExistingIdValue() {
        assertEquals("AAA:Network:456", NetexIdBuilder.newInstance("AAA:Network:123").withValue("456").build());
    }

    @Test
    public void testCreateFromNullId() {
        assertThrows(IllegalNetexIDException.class, () -> NetexIdBuilder.createIdFrom(null, "adf"));
    }

    @Test
    public void testCreateFromInvalidId() {
        assertThrows(IllegalNetexIDException.class, () -> NetexIdBuilder.createIdFrom("AA:B", "12345"));
    }

    @Test
    public void testCreateFromInvalidValue() {
        assertThrows(IllegalNetexIDException.class, () -> NetexIdBuilder.createIdFrom("AAA:Network:123", ".."));
    }

    @Test
    public void testCreateFromNullValue() {
        assertThrows(IllegalNetexIDException.class, () -> NetexIdBuilder.createIdFrom("AAA:Network:123", null));
    }

    @Test
    public void testCreateFromInvalidIdPart() {
        assertThrows(IllegalNetexIDException.class, () -> NetexIdBuilder.createIdFrom("AAA:Network:..", "12345"));
    }

}
