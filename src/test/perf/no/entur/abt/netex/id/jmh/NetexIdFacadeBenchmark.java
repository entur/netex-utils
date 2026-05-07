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

import no.entur.abt.netex.utils.NetexIdUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
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
@Timeout(timeUnit = TimeUnit.SECONDS, time = 4)
@Fork(3)
public class NetexIdFacadeBenchmark {

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

    private static String[][] IDS_PARTS;

    static {
        IDS_PARTS = new String[IDS.length][];
        for(int i = 0; i < IDS.length; i++) {
            IDS_PARTS[i] = new String[]{
                    NetexIdUtils.getCodespace(IDS[i]),
                    NetexIdUtils.getType(IDS[i]),
                    NetexIdUtils.getValue(IDS[i])
            };
        }
    }

    @Benchmark
    public long createId() {
        long count = 0;
        for (String[] parts : IDS_PARTS) {
            count += NetexIdUtils.createId(parts[0], parts[1], parts[2]).length();
        }
        return count;
    }

    @Benchmark
    public long createIdRegexp() {
        long count = 0;
        for (String[] parts : IDS_PARTS) {
            count += no.entur.abt.netex.id.jmh.legacy.NetexIdUtils.createId(parts[0], parts[1], parts[2]).length();
        }
        return count;
    }

    @Benchmark
    public long createFrom() {
        long count = 0;
        for (String id : IDS) {
            count += NetexIdUtils.createFrom(id, "abc").length();
        }
        return count;
    }

    @Benchmark
    public long createFromRegexp() {
        long count = 0;
        for (String id : IDS) {
            count += no.entur.abt.netex.id.jmh.legacy.NetexIdUtils.createFrom(id, "abc").length();
        }
        return count;
    }

    @Benchmark
    public long getCodespace() {
        long count = 0;
        for (String id : IDS) {
            count += NetexIdUtils.getCodespace(id).length();
        }
        return count;
    }

    @Benchmark
    public long getCodespaceRegexp() {
        long count = 0;
        for (String id : IDS) {
            count += no.entur.abt.netex.id.jmh.legacy.NetexIdUtils.getCodespace(id).length();
        }
        return count;
    }

    @Benchmark
    public long getType() {
        long count = 0;
        for (String id : IDS) {
            count += NetexIdUtils.getType(id).length();
        }
        return count;
    }

    @Benchmark
    public long getTypeRegexp() {
        long count = 0;
        for (String id : IDS) {
            count += no.entur.abt.netex.id.jmh.legacy.NetexIdUtils.getType(id).length();
        }
        return count;
    }

    @Benchmark
    public long getValue() {
        long count = 0;
        for (String id : IDS) {
            count += NetexIdUtils.getValue(id).length();
        }
        return count;
    }

    @Benchmark
    public long getValueRegexp() {
        long count = 0;
        for (String id : IDS) {
            count += no.entur.abt.netex.id.jmh.legacy.NetexIdUtils.getValue(id).length();
        }
        return count;
    }

    @Benchmark
    public long isValid() {
        long count = 0;
        for (String id : IDS) {
            count += NetexIdUtils.isValid(id) ? 1 : 0;
        }
        return count;
    }

    @Benchmark
    public long isValidRegexp() {
        long count = 0;
        for (String id : IDS) {
            count += no.entur.abt.netex.id.jmh.legacy.NetexIdUtils.isValid(id) ? 1 : 0;
        }
        return count;
    }

    @Benchmark
    public long assertValidOfType() {
        long count = 0;
        for (int i = 0; i < IDS.length; i++) {
            NetexIdUtils.assertValidOfType(IDS[i], IDS_PARTS[i][1]);
            count++;
        }
        return count;
    }

    @Benchmark
    public long assertValidOfTypeRegexp() {
        long count = 0;
        for (int i = 0; i < IDS.length; i++) {
            no.entur.abt.netex.id.jmh.legacy.NetexIdUtils.assertValidOfType(IDS[i], IDS_PARTS[i][1]);
            count++;
        }
        return count;
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(NetexIdFacadeBenchmark.class.getSimpleName())
                .result("jmh-result-" + Instant.now().toEpochMilli() + ".json")
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(opt).run();
    }
}