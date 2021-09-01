package no.entur.abt.netex.id;

import java.util.Set;

public class NetexIdParserBuilder {

	private boolean validation = true;
	private boolean intern = false;
	private Set<String> internSeed;

	public NetexIdParserBuilder withValidation(boolean validation) {
		this.validation = validation;

		return this;
	}

	public NetexIdParserBuilder withStringInterning(boolean intern) {
		this.intern = intern;
		return this;
	}

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
