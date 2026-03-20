package no.entur.abt.netex.id.jmh;

/*-
 * #%L
 * Netex utils
 * %%
 * Copyright (C) 2019 - 2026 Entur
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

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import no.entur.abt.netex.id.NetexId;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.Throughput)
@Warmup(time = 3, timeUnit = TimeUnit.SECONDS, iterations = 1)
@Measurement(time = 3, timeUnit = TimeUnit.SECONDS, iterations = 1)
@Timeout(timeUnit = TimeUnit.SECONDS, time = 10)
public class NetexIdFacadeBenchmark {

    private static final String ID = "XXX:SecurityPolicy:aeoAEO";

    @Benchmark
    public String modernCreateId() {
        return NetexId.createId("XXX", "SecurityPolicy", "aeoAEO");
    }

    @Benchmark
    public String legacyCreateId() {
        return no.entur.abt.netex.utils.NetexIdUtils.createId("XXX", "SecurityPolicy", "aeoAEO");
    }

    @Benchmark
    public String modernCreateFrom() {
        return NetexId.createFrom(ID, "abc");
    }

    @Benchmark
    public String legacyCreateFrom() {
        return no.entur.abt.netex.utils.NetexIdUtils.createFrom(ID, "abc");
    }

    @Benchmark
    public String modernGetCodespace() {
        return NetexId.getCodespace(ID);
    }

    @Benchmark
    public String legacyGetCodespace() {
        return no.entur.abt.netex.utils.NetexIdUtils.getCodespace(ID);
    }

    @Benchmark
    public String modernGetType() {
        return NetexId.getType(ID);
    }

    @Benchmark
    public String legacyGetType() {
        return no.entur.abt.netex.utils.NetexIdUtils.getType(ID);
    }

    @Benchmark
    public String modernGetValue() {
        return NetexId.getValue(ID);
    }

    @Benchmark
    public String legacyGetValue() {
        return no.entur.abt.netex.utils.NetexIdUtils.getValue(ID);
    }

    @Benchmark
    public boolean modernIsValid() {
        return NetexId.isValid(ID);
    }

    @Benchmark
    public boolean legacyIsValid() {
        return no.entur.abt.netex.utils.NetexIdUtils.isValid(ID);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(NetexIdFacadeBenchmark.class.getSimpleName())
                .result("jmh-result-" + Instant.now().toString() + ".json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opt).run();
    }
}

