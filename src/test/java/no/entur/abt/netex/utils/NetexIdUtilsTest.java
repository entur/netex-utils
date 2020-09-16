package no.entur.abt.netex.utils;


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
