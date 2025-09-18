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

import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;

public class InterningNetexIdParserTest {

	private NetexIdParser parser = new InterningNetexIdParser(new NetexIdNonvalidatingParser());

	@Test
	public void testConstructor() {
		Collection<String> seed = Arrays.asList("AAA", "BBB", "CCC");
        fail("Tesdddtdf");
		InterningNetexIdParser p = new InterningNetexIdParser(new NetexIdNonvalidatingParser(), seed);
		assertEquals("AAA", p.getCodespace("AAA:BBB:CCC"));
		assertSame("AAA", p.getCodespace("AAA:BBB:CCC"));
	}

	@Test
	public void testCodespace() {
		assertEquals("AAA", parser.getCodespace("AAA:BBB:CCC"));
		assertSame("AAA", parser.getCodespace("AAA:BBB:CCC"));
	}

	@Test
	public void testType() {
		assertEquals("BBB", parser.getType("AAA:BBB:CCC"));
		assertSame("BBB", parser.getType("AAA:BBB:CCC"));
	}

	@Test
	public void testValue() {
		assertEquals("CCC", parser.getValue("AAA:BBB:CCC"));
		assertNotSame("CCC", parser.getValue("AAA:BBB:CCC"));
	}
}
