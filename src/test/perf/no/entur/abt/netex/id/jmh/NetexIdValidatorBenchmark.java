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

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import no.entur.abt.netex.id.*;
import no.entur.abt.netex.utils.NetexIdUtils;
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
public class NetexIdValidatorBenchmark {

    private static String[] IDS = {
            "TST:DatedServiceJourney:1-2020-01-02",
            "TST:OperatingDay:2020-01-04",
            "TST:FareZone:1",
            "TST:UicOperatingPeriod:SchoolDays",
            "TST:ServiceCalendarFrame:ServiceCalendar-2021",
            "SJN:JourneyPattern:1_2058545094_1_1_1_1_1_1_79",
            "XXX:SecurityPolicy:æøåÆØÅ",
            "SJN:Operator:LineOperator",
            "SJN:Line:79",
            "SJN:ServiceJourney:SJN_1787_2021_12_07_NORD",
            "NSR:Quay:290",
            "TST:StopPointInJourneyPattern:1",
            "OPP:DistanceMatrixElement:TariffZone320to321",
            "OST:ValidableElement:CarFerry-CarLessThan6m"
    };

    private static final DefaultNetexIdValidator validator = new DefaultNetexIdValidator();

    @Benchmark
    public long validateNetexUtils() {
        long count = 0;
        for (String id : IDS) {
            if (NetexIdUtils.isValid(id)) {
                count++;
            }
        }
        return count;
    }

    @Benchmark
    public long validateNetexUtilsLegacy() {
        long count = 0;
        for (String id : IDS) {
            if (LegacyNetexIdUtils.isValid(id)) {
                count++;
            }
        }
        return count;
    }

    @Benchmark
    public long validate() {
        long count = 0;
        for (String id : IDS) {
            if (validator.validate(id)) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(NetexIdValidatorBenchmark.class.getSimpleName())
                .result("jmh-result-" + Instant.now().toString() + ".json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opt).run();
    }
}
