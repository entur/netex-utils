package no.entur.abt.netex.id;

/*-
 * #%L
 * Netex utils
 * %%
 * Copyright (C) 2019 - 2025 Entur
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Verifies, for every {@linkplain NetexIdTypes} constant, that {@link NetexIdTypes}
 * exposes both a matching {@code isXxx(CharSequence)} method and a matching no-arg
 * {@code isXxx()} method returning a usable {@link Predicate}.
 */
public class NetexIdTypesPredicatesTest {

	private static final String CODESPACE = "AAA";

	/**
	 * All (fieldName, typeValue) pairs declared in {@link NetexIdTypes}.
	 */
	static Stream<Arguments> types() {
		List<Arguments> arguments = new ArrayList<>();
		for (Field field : NetexIdTypes.class.getDeclaredFields()) {
			if (Modifier.isStatic(field.getModifiers()) && Modifier.isFinal(field.getModifiers()) && field.getType() == String.class) {
				try {
					String value = (String) field.get(null);
					arguments.add(new Arguments(field.getName(), value));
				} catch (IllegalAccessException e) {
					fail(e);
				}
			}
		}
		assertTrue(arguments.size() > 0, "Expected to find NetexIdTypes constants");
		return arguments.stream();
	}

	private static String toMethodSuffix(String fieldName) {
		StringBuilder sb = new StringBuilder();
		for (String part : fieldName.split("_")) {
			sb.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1).toLowerCase());
		}
		return sb.toString();
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("types")
	public void testIsMethodWithMatchingId(Arguments args) throws Exception {
		Method method = NetexIdTypes.class.getMethod("is" + toMethodSuffix(args.fieldName), CharSequence.class);

		String matchingId = CODESPACE + ":" + args.typeValue + ":123";
		assertTrue((Boolean) method.invoke(null, matchingId), () -> "Expected " + method.getName() + " to match " + matchingId);
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("types")
	public void testIsMethodWithNonMatchingId(Arguments args) throws Exception {
		Method method = NetexIdTypes.class.getMethod("is" + toMethodSuffix(args.fieldName), CharSequence.class);

		String nonMatchingId = CODESPACE + ":SomeOtherTypeNotInList:123";
		assertFalse((Boolean) method.invoke(null, nonMatchingId), () -> "Expected " + method.getName() + " to not match " + nonMatchingId);
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("types")
	public void testIsMethodWithNull(Arguments args) throws Exception {
		Method method = NetexIdTypes.class.getMethod("is" + toMethodSuffix(args.fieldName), CharSequence.class);

		assertFalse((Boolean) method.invoke(null, (Object) null), () -> "Expected " + method.getName() + "(null) to return false, not throw");
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("types")
	@SuppressWarnings("unchecked")
	public void testNoArgPredicateMethod(Arguments args) throws Exception {
		Method method = NetexIdTypes.class.getMethod("is" + toMethodSuffix(args.fieldName));

		Object result = method.invoke(null);
		assertNotNull(result);
		assertTrue(result instanceof Predicate, () -> method.getName() + "() should return a Predicate");

		Predicate<CharSequence> predicate = (Predicate<CharSequence>) result;

		String matchingId = CODESPACE + ":" + args.typeValue + ":123";
		String nonMatchingId = CODESPACE + ":SomeOtherTypeNotInList:123";

		assertTrue(predicate.test(matchingId), () -> "Expected " + method.getName() + "() predicate to match " + matchingId);
		assertFalse(predicate.test(nonMatchingId), () -> "Expected " + method.getName() + "() predicate to not match " + nonMatchingId);
		assertFalse(predicate.test(null), () -> "Expected " + method.getName() + "() predicate to return false, not throw, for null");
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("types")
	public void testNoArgPredicateMethodUsableInStreamFilter(Arguments args) throws Exception {
		Method method = NetexIdTypes.class.getMethod("is" + toMethodSuffix(args.fieldName));

		@SuppressWarnings("unchecked")
		Predicate<CharSequence> predicate = (Predicate<CharSequence>) method.invoke(null);

		String matchingId = CODESPACE + ":" + args.typeValue + ":123";
		String nonMatchingId = CODESPACE + ":SomeOtherTypeNotInList:123";

		List<String> ids = List.of(matchingId, nonMatchingId);
		List<String> filtered = ids.stream().filter(predicate).collect(Collectors.toList());

		assertEquals(1, filtered.size());
		assertTrue(filtered.contains(matchingId));
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("types")
	public void testIsTypeWithMatchingId(Arguments args) {
		String matchingId = CODESPACE + ":" + args.typeValue + ":123";
		assertTrue(NetexIdTypes.isType(matchingId, args.typeValue), () -> "Expected isType(" + matchingId + ", " + args.typeValue + ") to be true");
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("types")
	public void testIsTypeWithNonMatchingId(Arguments args) {
		String nonMatchingId = CODESPACE + ":SomeOtherTypeNotInList:123";
		assertFalse(NetexIdTypes.isType(nonMatchingId, args.typeValue), () -> "Expected isType(" + nonMatchingId + ", " + args.typeValue + ") to be false");
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("types")
	public void testIsTypeWithMatchingIdAsNonStringCharSequence(Arguments args) {
		StringBuilder matchingId = new StringBuilder(CODESPACE).append(':').append(args.typeValue).append(":123");
		assertTrue(NetexIdTypes.isType(matchingId, args.typeValue), () -> "Expected isType(" + matchingId + ", " + args.typeValue + ") to be true");
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("types")
	public void testIsTypeWithNonMatchingIdAsNonStringCharSequence(Arguments args) {
		StringBuilder nonMatchingId = new StringBuilder(CODESPACE).append(":SomeOtherTypeNotInList:123");
		assertFalse(NetexIdTypes.isType(nonMatchingId, args.typeValue), () -> "Expected isType(" + nonMatchingId + ", " + args.typeValue + ") to be false");
	}

	@Test
	public void testIsTypeWithInvalidId() {
		assertFalse(NetexIdTypes.isType("not-a-valid-id", NetexIdTypes.AUTHORITY));
	}

	@Test
	public void testIsTypeWithNullId() {
		assertFalse(NetexIdTypes.isType(null, NetexIdTypes.AUTHORITY));
	}

	@Test
	public void testIsTypeWithNullType() {
		assertFalse(NetexIdTypes.isType(CODESPACE + ":" + NetexIdTypes.AUTHORITY + ":123", null));
	}

	@Test
	public void testIsTypeWithEmptyId() {
		assertFalse(NetexIdTypes.isType("", NetexIdTypes.AUTHORITY));
	}

	@Test
	public void testIsTypeWithEmptyType() {
		assertFalse(NetexIdTypes.isType(CODESPACE + ":" + NetexIdTypes.AUTHORITY + ":123", ""));
	}

	@Test
	public void testIsTypeWithEmptyTypeAndEmptyTypePartInId() {
		// "AAA::123" has an empty type part, which is never a valid NeTEx id, even against an empty type argument.
		assertFalse(NetexIdTypes.isType(CODESPACE + "::123", ""));
	}

	@Test
	public void testIsTypeWithInvalidCodespace() {
		// lowercase codespace is invalid, even though the type part matches exactly.
		assertFalse(NetexIdTypes.isType("aaa:" + NetexIdTypes.AUTHORITY + ":123", NetexIdTypes.AUTHORITY));
	}

	@Test
	public void testIsTypeWithInvalidCodespaceAsNonStringCharSequence() {
		StringBuilder id = new StringBuilder("aaa:").append(NetexIdTypes.AUTHORITY).append(":123");
		assertFalse(NetexIdTypes.isType(id, NetexIdTypes.AUTHORITY));
	}

	@Test
	public void testIsTypeWithInvalidValueCharacters() {
		// space is not a valid value character, even though the type part matches exactly.
		assertFalse(NetexIdTypes.isType(CODESPACE + ":" + NetexIdTypes.AUTHORITY + ": ", NetexIdTypes.AUTHORITY));
	}

	@Test
	public void testIsTypeWithInvalidValueCharactersAsNonStringCharSequence() {
		StringBuilder id = new StringBuilder(CODESPACE).append(':').append(NetexIdTypes.AUTHORITY).append(": ");
		assertFalse(NetexIdTypes.isType(id, NetexIdTypes.AUTHORITY));
	}

	@Test
	public void testIsTypeWithEmptyValue() {
		assertFalse(NetexIdTypes.isType(CODESPACE + ":" + NetexIdTypes.AUTHORITY + ":", NetexIdTypes.AUTHORITY));
	}

	@Test
	public void testIsTypeWithActualTypeAsProperPrefixOfExpectedType() {
		// actual type "Author" is shorter than, and a prefix of, the expected type "Authority".
		assertFalse(NetexIdTypes.isType(CODESPACE + ":Author:123", NetexIdTypes.AUTHORITY));
	}

	@Test
	public void testIsTypeWithExpectedTypeAsProperPrefixOfActualType() {
		// actual type "AuthorityX" is longer than, and has the expected type "Authority" as a prefix.
		assertFalse(NetexIdTypes.isType(CODESPACE + ":AuthorityX:123", NetexIdTypes.AUTHORITY));
	}

	@Test
	public void testIsTypeWithMinimumLengthValidId() {
		assertTrue(NetexIdTypes.isType(CODESPACE + ":X:1", "X"));
	}

	@Test
	public void testIsTypeIsCaseSensitive() {
		assertFalse(NetexIdTypes.isType(CODESPACE + ":" + NetexIdTypes.AUTHORITY.toLowerCase() + ":123", NetexIdTypes.AUTHORITY));
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("types")
	public void testGetTypeWithValidId(Arguments args) {
		String id = CODESPACE + ":" + args.typeValue + ":123";
		assertEquals(args.typeValue, NetexIdTypes.getType(id), () -> "Expected getType(" + id + ") to return " + args.typeValue);
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("types")
	public void testGetTypeWithValidIdAsNonStringCharSequence(Arguments args) {
		StringBuilder id = new StringBuilder(CODESPACE).append(':').append(args.typeValue).append(":123");
		assertEquals(args.typeValue, NetexIdTypes.getType(id), () -> "Expected getType(" + id + ") to return " + args.typeValue);
	}

	@Test
	public void testGetTypeWithNullId() {
		assertNull(NetexIdTypes.getType(null));
	}

	@Test
	public void testGetTypeWithEmptyId() {
		assertNull(NetexIdTypes.getType(""));
	}

	@Test
	public void testGetTypeWithInvalidId() {
		assertNull(NetexIdTypes.getType("not-a-valid-id"));
	}

	@Test
	public void testGetTypeWithInvalidCodespace() {
		// lowercase codespace is invalid, even though the rest of the id is otherwise well-formed.
		assertNull(NetexIdTypes.getType("aaa:" + NetexIdTypes.AUTHORITY + ":123"));
	}

	@Test
	public void testGetTypeWithInvalidCodespaceAsNonStringCharSequence() {
		StringBuilder id = new StringBuilder("aaa:").append(NetexIdTypes.AUTHORITY).append(":123");
		assertNull(NetexIdTypes.getType(id));
	}

	@Test
	public void testGetTypeWithInvalidValueCharacters() {
		// space is not a valid value character.
		assertNull(NetexIdTypes.getType(CODESPACE + ":" + NetexIdTypes.AUTHORITY + ": "));
	}

	@Test
	public void testGetTypeWithInvalidValueCharactersAsNonStringCharSequence() {
		StringBuilder id = new StringBuilder(CODESPACE).append(':').append(NetexIdTypes.AUTHORITY).append(": ");
		assertNull(NetexIdTypes.getType(id));
	}

	@Test
	public void testGetTypeWithEmptyValue() {
		assertNull(NetexIdTypes.getType(CODESPACE + ":" + NetexIdTypes.AUTHORITY + ":"));
	}

	@Test
	public void testGetTypeWithEmptyTypePart() {
		assertNull(NetexIdTypes.getType(CODESPACE + "::123"));
	}

	@Test
	public void testGetTypeWithMinimumLengthValidId() {
		assertEquals("X", NetexIdTypes.getType(CODESPACE + ":X:1"));
	}

	@Test
	public void testGetTypeWithTypeNotInList() {
		// getType does not restrict the type to a known NetexIdTypes constant; it returns whatever
		// well-formed type part is present in the id.
		assertEquals("SomeOtherTypeNotInList", NetexIdTypes.getType(CODESPACE + ":SomeOtherTypeNotInList:123"));
	}

	@Test
	public void testGetTypePreservesCase() {
		String id = CODESPACE + ":" + NetexIdTypes.AUTHORITY.toLowerCase() + ":123";
		assertEquals(NetexIdTypes.AUTHORITY.toLowerCase(), NetexIdTypes.getType(id));
	}

	/**
	 * Simple holder so the parameterized test name shows the constant field name.
	 */
	public static class Arguments {
		private final String fieldName;
		private final String typeValue;

		public Arguments(String fieldName, String typeValue) {
			this.fieldName = fieldName;
			this.typeValue = typeValue;
		}

		@Override
		public String toString() {
			return fieldName;
		}
	}
}
