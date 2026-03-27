[![Maven Central](https://img.shields.io/maven-central/v/no.entur.abt/netex-utils.svg)](https://mvnrepository.com/artifact/no.entur.abt/netex-utils)

# netex-utils

Utility classes for working with [NeTEx](https://netex-cen.eu/) IDs on the format `Codespace:Type:Value` (e.g. `AAA:FareZone:123`).

## Installation

```xml
<dependency>
    <groupId>no.entur.abt</groupId>
    <artifactId>netex-utils</artifactId>
    <version>x.x.x</version>
</dependency>
```

## Netex IDs

Create, parse, validate, and filter NeTEx IDs. All utilities (excluding builders) are thread-safe.

| Need | Use |
|---|---|
| Create a validated ID from parts | `NetexIdBuilder` |
| Extract codespace / type / value | `NetexIdParser` + `NetexIdParserBuilder` |
| Filter a stream of IDs | `NetexIdPredicate` + `NetexIdPredicateBuilder` |
| Validate an ID or its parts | `NetexIdValidator` / `DefaultNetexIdValidator` |
| Legacy one-liner helpers | `NetexIdUtils` _(slower, see below)_ |

### Quick example

```java
// Build
String id = NetexIdBuilder.newInstance()
    .withCodespace("AAA")
    .withType("FareZone")
    .withValue("123")
    .build(); // "AAA:FareZone:123"

// Validate
NetexIdValidator validator = DefaultNetexIdValidator.getInstance();
if (!validator.validate(id)) {
    throw new IllegalArgumentException("Invalid NeTEx ID");
}

// Parse
NetexIdParser parser = NetexIdParserBuilder.newInstance().build();
String codespace = parser.getCodespace(id); // AAA
String type      = parser.getType(id);      // FareZone
String value     = parser.getValue(id);     // 123

// Filter
NetexIdPredicate fareZonesInAAA = NetexIdPredicateBuilder.newInstance()
    .withCodespace("AAA")
    .withType("FareZone")
    .build();

List<String> fareZones = ids.stream()
    .filter(fareZonesInAAA)
    .toList();
```

## NetexIdBuilder

Build a validated ID from its parts. Throws `IllegalStateException` on invalid input.

```java
String id = NetexIdBuilder.newInstance()
    .withCodespace("AAA")   // exactly 3 uppercase letters
    .withType("FareZone")   // non-empty, letters only
    .withValue("123")       // non-empty, alphanumeric + - _ \ and Nordic letters
    .build();

// id == "AAA:FareZone:123"
```

Invalid input fails fast:

```java
NetexIdBuilder.newInstance()
    .withCodespace("AA")    // too short
    .withType("FareZone")
    .withValue("123")
    .build();               // throws IllegalStateException
```

## NetexIdParser

Create a validating or non-validating parser with `NetexIdParserBuilder`:

```java
// Validating (default) — throws IllegalNetexIDException for malformed IDs
NetexIdParser parser = NetexIdParserBuilder.newInstance()
    .withValidation(true)
    .build();

// Non-validating — fastest, no input checks
NetexIdParser lenient = NetexIdParserBuilder.newInstance()
    .withValidation(false)
    .build();
```

Extract parts:

```java
String codespace = parser.getCodespace("AAA:FareZone:123"); // AAA
String type      = parser.getType("AAA:FareZone:123");      // FareZone
String value     = parser.getValue("AAA:FareZone:123");     // 123
```

### String interning

When parsing large volumes of IDs with repeated codespace/type values, enable interning to reduce allocations and allow identity (`==`) comparisons:

```java
Set<String> seed = Set.of("AAA", "BBB", "FareZone", "Network");

NetexIdParser parser = NetexIdParserBuilder.newInstance()
    .withValidation(false)
    .withStringInterning(true)
    .withStringInterningInitialValues(seed) // pre-warms the intern cache
    .build();

String a = parser.getCodespace("AAA:FareZone:1");
String b = parser.getCodespace("AAA:FareZone:2");
assert a == b; // same interned instance
```

## NetexIdPredicate

`NetexIdPredicate` implements `Predicate<CharSequence>` and can filter by codespace, type, or both.

```java
// Match by codespace only
NetexIdPredicate byCodespace = NetexIdPredicateBuilder.newInstance()
    .withCodespace("AAA")
    .build();

// Match by type only
NetexIdPredicate byType = NetexIdPredicateBuilder.newInstance()
    .withType("Network")
    .build();

// Match by both (most selective)
NetexIdPredicate byBoth = NetexIdPredicateBuilder.newInstance()
    .withCodespace("AAA")
    .withType("Network")
    .build();
```

Use directly or in streams:

```java
byCodespace.test("AAA:Network:1"); // true
byCodespace.test("BBB:Network:1"); // false

List<String> result = ids.stream()
    .filter(byBoth)
    .toList();
```

Invalid codespace or type strings throw `IllegalStateException` at build time.

## NetexIdValidator

Validate complete IDs or individual parts. Get the singleton default instance:

```java
NetexIdValidator validator = DefaultNetexIdValidator.getInstance();

validator.validate("AAA:FareZone:123");   // true
validator.validateCodespace("AAA");        // true
validator.validateType("FareZone");        // true
validator.validateValue("123");            // true
```

## NetexIdUtils (legacy)

> ⚠️ **`NetexIdUtils` is considerably slower than the modern APIs** — benchmarks show parsing and validation to be ~25–33× slower due to regexp-based validation. Prefer `NetexIdParser`, `NetexIdValidator`, and `NetexIdBuilder` for any performance-sensitive code.

```java
String id = NetexIdUtils.createId("AAA", "FareZone", "123");
// "AAA:FareZone:123"

String derived = NetexIdUtils.createFrom(id, "456");
// "AAA:FareZone:456"

String codespace = NetexIdUtils.getCodespace(id); // AAA
String type      = NetexIdUtils.getType(id);      // FareZone
String value     = NetexIdUtils.getValue(id);     // 123

NetexIdUtils.assertValid(id);
NetexIdUtils.assertValidOfType(id, "FareZone");
```

## Performance

JMH benchmarks are included. Run them with:

```bash
mvn package && java -jar target/netex-utils-*-perf-tests.jar
```

Results from the included benchmark runs (ops/ms, higher is better):

| Operation | Modern API | `NetexIdUtils` | Ratio |
|---|---|---|---|
| Get codespace | 6 005 | 236 | ~25× |
| Get type | 7 615 | 228 | ~33× |
| Get value | 5 797 | 226 | ~26× |
| Validate ID | 7 707 | 247 | ~31× |
| Create ID from existing | 27 496 | 1 575 | ~17× |
| Create ID from parts | 63 906 | 51 561 | ~1.2× |

Measured on a single machine; results will vary by JVM and hardware.
