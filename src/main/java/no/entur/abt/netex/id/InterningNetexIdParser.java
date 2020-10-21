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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InterningNetexIdParser implements NetexIdParser {

	// assume frequent read, seldom write
	private final Map<String, String> intern = new HashMap<>(1024);

	private final NetexIdParser delegate;

	public InterningNetexIdParser(NetexIdParser delegate, Collection<String> seed) {
		this.delegate = delegate;
		for (String string : seed) {
			String interned = string.intern();
			intern.put(interned, interned);
		}
	}

	public InterningNetexIdParser(NetexIdParser delegate) {
		this.delegate = delegate;
	}

	@Override
	public String getCodespace(CharSequence id) {
		return intern(delegate.getCodespace(id));
	}

	@Override
	public String getType(CharSequence id) {
		return intern(delegate.getType(id));
	}

	@Override
	public String getValue(CharSequence id) {
		return intern(delegate.getValue(id));
	}

	private String intern(String codespace) {
		String interned = intern.get(codespace);
		if (interned == null) {
			// compete for lock
			synchronized (intern) {
				// now that have lock, check again
				interned = intern.get(codespace);
				if (interned == null) {
					codespace = codespace.intern();

					intern.put(codespace, codespace);
					interned = codespace;
				}
			}
		}
		return interned;
	}

}
