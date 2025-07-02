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

import java.util.Set;

public class NetexIdParserBuilder {

	public static NetexIdParserBuilder newInstance() {
		return new NetexIdParserBuilder();
	}

	private boolean validation = true;
	private boolean intern = false;
	private Set<String> internSeed;

	public NetexIdParserBuilder withValidation(boolean validation) {
		this.validation = validation;

		return this;
	}

	/**
	 * Return values as from {@linkplain String#intern()}. <br>
	 * <br>
	 * Please also use a seed to prepare the parser during init; the underlying implementation uses synchronization to populate its internal cache (one by one).
	 *
	 * @param intern true to enable
	 * @return this builder
	 */

	public NetexIdParserBuilder withStringInterning(boolean intern) {
		this.intern = intern;
		return this;
	}

	/**
	 * Initial values for {@linkplain String#intern()} pool.
	 *
	 * @param values initial values
	 * @return this builder
	 */

	public NetexIdParserBuilder withStringInterningInitialValues(Set<String> values) {
		this.internSeed = values;
		return this;
	}

	public NetexIdParser build() {
		NetexIdParser parser;
		if (validation) {
			parser = new NetexIdValidatingParser();
		} else {
			parser = new NetexIdNonvalidatingParser();
		}

		if (intern) {
			if (internSeed != null) {
				parser = new InterningNetexIdParser(parser, internSeed);
			} else {
				parser = new InterningNetexIdParser(parser);
			}
		}

		return parser;
	}
}
