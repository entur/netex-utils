package no.entur.abt.netex.id.jmh;

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

import no.entur.abt.netex.id.NetexIdBuilder;
import no.entur.abt.netex.utils.NetexIdUtils;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.Throughput)
@Warmup(time = 3, timeUnit = TimeUnit.SECONDS, iterations = 1)
@Measurement(time = 3, timeUnit = TimeUnit.SECONDS, iterations = 1)
@Timeout(timeUnit = TimeUnit.SECONDS, time = 10)
public class NetexIdCreateBenchmark {

    @Benchmark
    public String createFromExistingIdNetexUtils() {
        return NetexIdUtils.createFrom("XXX:SecurityPolicy:æøåÆØÅ", "abc");
    }

    @Benchmark
    public String createIdNetexUtils() {
        return NetexIdUtils.createId("XXX", "SecurityPolicy", "æøåÆØÅ");
    }

    @Benchmark
    public String createFromExistingId() {
        return NetexIdBuilder.newInstance("XXX:SecurityPolicy:æøåÆØÅ").withValue("abc").build();
    }

    @Benchmark
    public String createId() {
        return NetexIdBuilder.newInstance().withCodespace("XXX").withType("SecurityPolicy").withValue("æøåÆØÅ").build();
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(NetexIdCreateBenchmark.class.getSimpleName())
                .result("jmh-result-" + Instant.now().toString() + ".json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opt).run();
    }
}
