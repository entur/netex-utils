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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
	public void testAsMapContainsTypeWithMatchingPredicate(Arguments args) throws Exception {
		Map<String, Predicate<CharSequence>> byType = NetexIdTypes.Predicates.asMap();

		assertTrue(byType.containsKey(args.typeValue), () -> "Expected asMap() to contain key " + args.typeValue);

		Predicate<CharSequence> predicate = byType.get(args.typeValue);
		assertNotNull(predicate);

		String matchingId = CODESPACE + ":" + args.typeValue + ":123";
		String nonMatchingId = CODESPACE + ":SomeOtherTypeNotInList:123";

		assertTrue(predicate.test(matchingId), () -> "Expected asMap() predicate for " + args.typeValue + " to match " + matchingId);
		assertFalse(predicate.test(nonMatchingId), () -> "Expected asMap() predicate for " + args.typeValue + " to not match " + nonMatchingId);
		assertFalse(predicate.test(null), () -> "Expected asMap() predicate for " + args.typeValue + " to return false, not throw, for null");
	}

	@Test
	public void testAsMapHasOneEntryPerType() {
		Map<String, Predicate<CharSequence>> byType = NetexIdTypes.Predicates.asMap();

		long typeCount = types().count();
		assertEquals(typeCount, byType.size(), "Expected one asMap() entry per NetexIdTypes constant");
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
