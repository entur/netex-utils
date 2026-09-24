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

import no.entur.abt.netex.id.NetexIdTypes;
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
public class NetexIdTypesIsTypeBenchmark {

	private static final String[] MATCHING_IDS = {
			"TST:Authority:1", "SJN:Authority:LineOperatorAuthority", "NSR:Authority:290", "XXX:Authority:æøåÆØÅ", "OST:Authority:CarFerry-CarLessThan6m",
	};

	private static final String[] NON_MATCHING_IDS = {
			"TST:DatedServiceJourney:1-2020-01-02", "TST:OperatingDay:2020-01-04", "TST:FareZone:1", "SJN:JourneyPattern:1_2058545094_1_1_1_1_1_1_79",
			"NSR:Quay:290",
	};

	@Benchmark
	public long isAuthorityMatching() {
		long count = 0;
		for (String id : MATCHING_IDS) {
			count += NetexIdUtils.isValid(id) && NetexIdUtils.getType(id).equals(NetexIdTypes.AUTHORITY) ? 1 : 0;
		}
		return count;
	}

	@Benchmark
	public long isTypeMatching() {
		long count = 0;
		for (String id : MATCHING_IDS) {
			count += NetexIdTypes.isType(id, NetexIdTypes.AUTHORITY) ? 1 : 0;
		}
		return count;
	}

	@Benchmark
	public long isAuthorityNonMatching() {
		long count = 0;
		for (String id : NON_MATCHING_IDS) {
			count += NetexIdUtils.isValid(id) && NetexIdUtils.getType(id).equals(NetexIdTypes.AUTHORITY) ? 1 : 0;
		}
		return count;
	}

	@Benchmark
	public long isTypeNonMatching() {
		long count = 0;
		for (String id : NON_MATCHING_IDS) {
			count += NetexIdTypes.isType(id, NetexIdTypes.AUTHORITY) ? 1 : 0;
		}
		return count;
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(NetexIdTypesIsTypeBenchmark.class.getSimpleName())
				.result("jmh-result-" + Instant.now().toEpochMilli() + ".json")
				.resultFormat(ResultFormatType.JSON)
				.build();
		new Runner(opt).run();
	}
}
