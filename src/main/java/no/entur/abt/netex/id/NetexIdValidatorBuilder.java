package no.entur.abt.netex.id;

/*-
 * #%L
 * netex-utils
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Builder that produces a {@link NetexIdValidator}.
 *
 * <p>When SIMD support is explicitly requested (or when using {@link #simd()}), the builder
 * attempts to create a {@link SimdNetexIdValidator}. If the {@code jdk.incubator.vector} module is
 * not available at runtime the builder silently falls back to the standard
 * {@link DefaultNetexIdValidator}.</p>
 *
 * <p>Example usage:</p>
 * <pre>{@code
 * // Get the fastest available validator (SIMD if available, otherwise default):
 * NetexIdValidator validator = NetexIdValidatorBuilder.newInstance().simd().build();
 *
 * // Always use the default scalar validator:
 * NetexIdValidator validator = NetexIdValidatorBuilder.newInstance().build();
 * }</pre>
 */
public class NetexIdValidatorBuilder {

	private static final Logger log = LoggerFactory.getLogger(NetexIdValidatorBuilder.class);

	/** Cached availability flag so the check is only performed once per JVM. */
	private static final boolean SIMD_AVAILABLE = probeSimdAvailability();

	private boolean useSIMD = false;

	private static boolean probeSimdAvailability() {
		try {
			Class.forName("jdk.incubator.vector.ByteVector");
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

	public static NetexIdValidatorBuilder newInstance() {
		return new NetexIdValidatorBuilder();
	}

	/**
	 * Request that a SIMD-accelerated validator be used, if the Vector API is available at runtime.
	 * If SIMD is not available, {@link #build()} will return a {@link DefaultNetexIdValidator}
	 * instead and emit a warning.
	 */
	public NetexIdValidatorBuilder simd() {
		this.useSIMD = true;
		return this;
	}

	/**
	 * Build and return the configured {@link NetexIdValidator}.
	 */
	public NetexIdValidator build() {
		if (useSIMD) {
			if (SIMD_AVAILABLE) {
				return SimdNetexIdValidator.getInstance();
			} else {
				log.warn("SIMD (jdk.incubator.vector) is not available on this JVM. Falling back to DefaultNetexIdValidator.");
				return DefaultNetexIdValidator.getInstance();
			}
		}
		return DefaultNetexIdValidator.getInstance();
	}
}
