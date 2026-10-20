# Validator performance

The type-character scan now folds ASCII case and checks the `a`–`z` range,
removing the lookup-table load and its bounds check from each iteration.
The unused protected type-character table has also been removed; subclasses
that reference that field must be updated.

Measured with OpenJDK 25.0.4.1, JMH 1.37, one worker, and CPU affinity 10–11:

| Version | Throughput (operations/ms) | JMH error (99.9% CI) |
| --- | ---: | ---: |
| Before | 5623.067 | ±6.565 |
| After | 6035.090 | ±33.798 |

Each operation validates the benchmark’s 14 valid IDs. This is a 7.3% throughput
increase on that dataset; it does not establish application-wide improvement.
Both versions allocate effectively zero bytes per operation. Two forks were used,
with three warmup/four measurement iterations before and four warmup/five
measurement iterations after, all one second long. An initial noisy baseline was
excluded and repeated after the machine settled.

To reproduce, build each revision and keep a copy of its assembled benchmark JAR:

```sh
taskset -c 10,11 mvn -o -Pjmh -DskipTests package
taskset -c 10,11 java -cp target/netex-utils-0.1-SNAPSHOT-perf-tests.jar \
  org.openjdk.jmh.Main 'NetexIdValidatorBenchmark.validate$' \
  -f 2 -wi 4 -i 5 -w 1s -r 1s -prof gc
```

The JMH CLI is invoked explicitly because the repository’s `BenchmarkMain`
constructs its own options and does not forward command-line arguments.
An exhaustive regression test checks all UTF-16 characters against the original
ASCII-letter contract, including StringBuilder input.
