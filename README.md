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

> mvn package && java -jar target/netex-utils-*-perf-tests.jar IdBenchmark -rf json

Results indicate use of regexp in `NetexIdUtils` is considerably (20x) slower than manually typed equivalent. Non-validating is 4-5x times faster than validating.

<details>
  <summary>Result details</summary>

```
Benchmark                                Mode  Cnt    Score    Error   Units
IdBenchmark.codespaceClassic            thrpt    5    1,786 ±  0,300  ops/us
IdBenchmark.codespaceModernNonValidate  thrpt    5  248,182 ± 33,158  ops/us
IdBenchmark.codespaceModernValidate     thrpt    5   48,716 ±  4,590  ops/us
IdBenchmark.typeClassic                 thrpt    5    1,834 ±  0,227  ops/us
IdBenchmark.typeModernNonValidate       thrpt    5  192,897 ± 14,495  ops/us
IdBenchmark.typeModernValidate          thrpt    5   42,474 ±  2,951  ops/us
IdBenchmark.validateClassic             thrpt    5    2,273 ±  0,197  ops/us
IdBenchmark.validateModern              thrpt    5  101,749 ± 13,740  ops/us
IdBenchmark.valueClassic                thrpt    5    1,796 ±  0,222  ops/us
IdBenchmark.valueModernNonValidate      thrpt    5  230,751 ±  7,537  ops/us
IdBenchmark.valueModernValidate         thrpt    5   41,830 ±  3,041  ops/us
```
</details>