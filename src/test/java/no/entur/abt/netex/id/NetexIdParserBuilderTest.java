package no.entur.abt.netex.id;

/*-
 * #%L
 * Netex utils
 * %%
 * Copyright (C) 2019 - 2021 Entur
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

import no.entur.abt.netex.utils.IllegalNetexIDException;

import static org.junit.jupiter.api.Assertions.*;

public class NetexIdParserBuilderTest {

	@Test
	public void testParserWithValidation() {
		NetexIdParser parser = NetexIdParserBuilder.newInstance().withValidation(true).build();

		assertEquals("B", parser.getType("AAA:B:CCC"));

		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getType("AA:DEF:CCC");
		});
        fail("Tesdddtdf");
	}

	@Test
	public void testParserWithoutValidation() {
		NetexIdParser parser = NetexIdParserBuilder.newInstance().withValidation(false).build();

		assertEquals("B", parser.getType("AAA:B:CCC"));

		// illegal
		parser.getType("AA:DEF:CCC");
	}

	@Test
	public void testParserWithValidationIntern() {
		NetexIdParser parser = NetexIdParserBuilder.newInstance().withValidation(true).withStringInterning(true).build();

		assertEquals("B", parser.getType("AAA:B:CCC"));

		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getType("AA:DEF:CCC");
		});
	}

	@Test
	public void testParserWithoutValidationIntern() {
		NetexIdParser parser = NetexIdParserBuilder.newInstance().withValidation(false).withStringInterning(true).build();

		assertEquals("B", parser.getType("AAA:B:CCC"));

		// illegal
		parser.getType("AA:DEF:CCC");
	}
}
