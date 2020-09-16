package no.entur.abt.netex.utils;

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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NetexIdUtilsTest {

    @Test
    public void isValid_whenNotValid_thenReturnFalse() {
        assertFalse(NetexIdUtils.isValid(""));
        assertFalse(NetexIdUtils.isValid("X:Customer:1"));
        assertFalse(NetexIdUtils.isValid("XX:Customer:1"));
        assertFalse(NetexIdUtils.isValid("XXX:CustomerAccount"));
        assertFalse(NetexIdUtils.isValid("XXX:Customer:@"));
        assertFalse(NetexIdUtils.isValid("XXX:Customer:"));
        assertFalse(NetexIdUtils.isValid(":Customer:"));
        assertFalse(NetexIdUtils.isValid("XXX::"));
        assertFalse(NetexIdUtils.isValid("XXX::1"));
        assertFalse(NetexIdUtils.isValid(":::"));
    }

    @Test
    public void isValid_whenValid_thenReturnTrue() {
        assertTrue(NetexIdUtils.isValid("XXX:Customer:1"));
        assertTrue(NetexIdUtils.isValid("XXX:Customer:a"));
        assertTrue(NetexIdUtils.isValid("XXX:Customer:æøåÆØÅ"));
    }

}
