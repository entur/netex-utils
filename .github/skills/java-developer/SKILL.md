---
name: java-developer
description: 'Best practices for developing Java code in the netex-utils library.'
---

# Java Developer — netex-utils

Your goal is to help develop high-quality Java code in the `netex-utils` library, a Java 17 utility library for working with the NeTEx public transport data exchange standard.

## Project Context

- **NeTEx** is a CEN European standard for exchanging public transport network data.
- The library is a zero-dependency utility jar, not a Spring Boot application.
- Package root: `no.entur.abt.netex`
- Build tool: Maven
- Java version: 17
- License: EUPL v1.1 (headers are enforced by the license Maven plugin)

## Java 17 Best Practices

- Prefer **records** for immutable data carriers instead of hand-rolled POJOs with getters/equals/hashCode.
- Use **sealed classes and interfaces** to model closed type hierarchies (e.g., parse results, variants of NeTEx IDs).
- Apply **pattern matching** for `instanceof` checks (`if (obj instanceof MyType t) { ... }`).
- Use **text blocks** for multi-line string literals in tests and documentation.
- Prefer `var` for local variables when the type is obvious from the right-hand side.
- Use `Optional` for return values that may be absent; never return `null` from a public method unless the contract explicitly documents it.

## Code Style

- Code formatting is enforced by **Spotless** — run `mvn spotless:apply` to auto-format before committing.
- POM ordering is enforced by **sortpom** — run `mvn sortpom:sort` if you modify `pom.xml`.
- Follow the existing package naming convention: `no.entur.abt.netex.<domain>[.<subdomain>]`.
- Keep classes small and focused. Prefer composition over inheritance.
- Declare fields and method parameters `final` wherever possible.

## License Headers

- All `.java` files must have an **EUPL v1.1** license header.
- The license Maven plugin (`initialize` phase) adds/updates headers automatically when running `mvn initialize` or any lifecycle phase that includes `initialize`.

## Testing

- Use **JUnit 5** (`junit-jupiter`) — no JUnit 4.
- Prefer `@ParameterizedTest` with `@MethodSource` or `@CsvSource` for data-driven tests.
- Test class names should end with `Test` (picked up by Surefire) or `IT` for integration tests (Failsafe).
- Write tests that assert behaviour through the public API; avoid testing internal implementation details.
- Keep tests fast; this is a utility library with no I/O dependencies.

## Benchmarking

- Performance-sensitive code should have **JMH** microbenchmarks in `src/jmh/java`.
- Annotate benchmark classes with `@BenchmarkMode(Mode.Throughput)` and `@OutputTimeUnit(TimeUnit.MILLISECONDS)` unless a different mode is more appropriate.
- Use `@State(Scope.Benchmark)` for shared benchmark state.
- Run benchmarks with `mvn verify -Pbenchmark` (or the equivalent JMH runner).

## Javadoc

- Follow the repository's existing conventions: Javadoc is encouraged for public APIs when it adds value, especially for non-obvious behaviour, but it is not required for every public or protected type/member.
- When Javadoc is present, the first sentence is the summary and should end with a period.
- When documenting methods, use `@param`, `@return`, and `@throws` tags consistently where they add useful information.
- Use `{@code}` for inline code and `<pre>{@code ... }</pre>` for multi-line code blocks.

## Maven & Dependencies

- This library has **no runtime dependencies** — keep it that way unless there is a strong reason.
- Only add `<scope>test</scope>` dependencies for testing utilities.
- Declare new properties for version numbers in the `<properties>` section of `pom.xml`.
- Run `mvn verify` before opening a pull request to ensure all checks pass (compile, test, license, Spotless, Javadoc).
