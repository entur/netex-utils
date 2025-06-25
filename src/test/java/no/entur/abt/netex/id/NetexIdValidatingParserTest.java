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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import no.entur.abt.netex.utils.IllegalNetexIDException;

public class NetexIdValidatingParserTest {

	private static final String SURROGATE_PAIRS = "\uD83D\uDE00";

	private NetexIdValidatingParser parser = new NetexIdValidatingParser();

	@Test
	public void testCodespace() {
		assertEquals("AAA", parser.getCodespace("AAA:BBB:CCC"));

		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getCodespace("AA:BBB:CCC");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getCodespace("AAAA:BBB:CCC");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getCodespace("AAAA:BBB:CCC");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getCodespace("AAA:ABC:CCC!!");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getCodespace("AAA:ABC:");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getCodespace("AAA::");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getCodespace(SURROGATE_PAIRS + "A:CC:BB");
		});
	}

	@Test
	public void testType() {
		assertEquals("BBB", parser.getType("AAA:BBB:CCC"));
		assertEquals("B", parser.getType("AAA:B:CCC"));

		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getType("AAA::CCC");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getType("AAA:ABC!!:CCC");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getType("AA:DEF:CCC");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getType("AAAA:DEF:CCC");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getType("AAA:ABC:CCC!!");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getType("AAA:ABC:");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getType("AAA::");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getType("AAA:");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getType("AAA:B");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getType("AAA:B!");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getType("AAA:B!:CCC");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getType("AAA:B" + SURROGATE_PAIRS + ":CCC");
		});

	}

	@Test
	public void testValue() {
		assertEquals("CCC", parser.getValue("AAA:BBB:CCC"));
		assertEquals("C", parser.getValue("AAA:BBB:C"));

		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getValue("AAA:BBB:");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getValue("AAA:BBB:CCC!");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getValue("AA:DEF:CCC");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getValue("AAAA:DEF:CCC");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getValue("AAA:DEF:CCC:DDD");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getValue("AAA:ABC:CCC!!");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getValue("AAA:ABC:");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getValue("AAA::");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getValue("AAA:");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getValue("AAA:B");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getValue("AAA:B!");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getValue("AAA:B!:CCC");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getValue("AAA:B!:CCC");
		});
		assertThrows(IllegalNetexIDException.class, () -> {
			parser.getValue("AAA:B:CCC" + SURROGATE_PAIRS);
		});

	}
}
