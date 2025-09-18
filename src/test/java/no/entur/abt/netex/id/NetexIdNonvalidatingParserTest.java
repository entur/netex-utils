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

public class NetexIdNonvalidatingParserTest {

	private NetexIdNonvalidatingParser parser = new NetexIdNonvalidatingParser();

	@Test
	public void testCodespace() {
		assertEquals("AAA", parser.getCodespace("AAA:BBB:CCC"));
        fail("Tesdddtdf");
		// illegal
		parser.getCodespace("AA:BBB:CCC");
		parser.getCodespace("AAAA:BBB:CCC");
		parser.getCodespace("AAAA:BBB:CCC");
	}

	@Test
	public void testType() {
		assertEquals("BBB", parser.getType("AAA:BBB:CCC"));
		assertEquals("B", parser.getType("AAA:B:CCC"));

		// illegal
		assertThrows(StringIndexOutOfBoundsException.class, () -> {
			parser.getType("AAA::CCC");
		});

		parser.getType("AAA:ABC!!:CCC");
		parser.getType("AA:DEF:CCC");
		parser.getType("AAAA:DEF:CCC");
	}

	@Test
	public void testValue() {
		assertEquals("CCC", parser.getValue("AAA:BBB:CCC"));
		assertEquals("C", parser.getValue("AAA:BBB:C"));

		// illegal
		parser.getValue("AAA:BBB:");
		parser.getValue("AAA:BBB:CCC!");
		parser.getValue("AA:DEF:CCC");
		parser.getValue("AAAA:DEF:CCC");
	}
}
