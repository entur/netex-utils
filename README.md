[![Maven Central](https://img.shields.io/maven-central/v/no.entur.abt/netex-utils.svg)](https://mvnrepository.com/artifact/no.entur.abt/netex-utils)
# Netex utils

Netex utility classes.

# Netex ids

Create, parse, validate and filter Netex ids. All utilities (excluding builders) are thread-safe and fast.

## NetexIdBuilder

```
String id = NetexIdBuilder.newInstance()
    .withCodespace("AAA")
    .withType("FareZone")
    .withValue("123")
    .build();
```

for id `AAA:FareZone:123`.

## NetexIdParser

Create a validating or non-validating parser using `NetexIdParserBuilder`:

```
NetexIdParser parser = NetexIdParserBuilder.newInstance()
    .withValidation(true) // defaults to true
    .build();

```

There is also an option to only return codespace and type as returned by `String.intern(..)`.

Parse id parts using

```
String codespace = parser.getCodespace("AAA:FareZone:123"); // AAA
```

or

```
String type = parser.getType("AAA:FareZone:123"); // FareZone
```

or

```
String value = parser.getValue("AAA:FareZone:123"); // 123
```

Illegal ids result in an `IllegalNetexIDException` being thrown.

## NetexIdPredicate

Create a predicate

```
NetexIdPredicate predicate = NetexIdPredicateBuilder.newInstance()
    .withCodespace("AAA")
    .build();
```

then test Netex ids, for examples

```
if(predicate.test("AAA:Network:123")) {
    // runs
}
```

or

```

if(predicate.test("BBB:Network:123")) {
    // does not run
}
```

Note that `NetexIdPredicate` is an instance of `Predicate<CharSequence>`. Thus it can be used in streams, i.e.

```
NetexIdPredicate predicate = NetexIdPredicateBuilder.newInstance()
    .withCodespace("AAA")
    .build();

List<String> onlyCodespaceAAA = myIds.stream()
    .filter(predicate)
    .collect(Collectors.toList());
```

## NetexIdValidator

Validate full Netex ids or individual parts (codespace, type, value). Ge the default instance using

```
NetexIdValidator validator = DefaultNetexIdValidator.getInstance();
```

then validate ids

```
if(!validator.validate("AAA:FareZone:123")) {
    throw new IllegalArgumentException(); 
}
```

or individual parts of an id:

```
if(!validator.validateCodespace("AAA")) {
    throw new IllegalArgumentException(); 
}
```

```
if(!validator.validateType("FareZone")) {
    throw new IllegalArgumentException(); 
}
```

```
if(!validator.validateValue("123")) {
    throw new IllegalArgumentException(); 
}
```

## NetexIdUtils

Legacy utility for getting id type, value and codespace. Always validates (using regexp).

## Performance
Includes a few JMH benchmarks. Run

> mvn package && java -jar target/netex-utils-*-perf-tests.jar
