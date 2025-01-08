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

import java.util.concurrent.TimeUnit;

import no.entur.abt.netex.id.DefaultNetexIdValidator;
import no.entur.abt.netex.id.NetexIdNonvalidatingParser;
import no.entur.abt.netex.id.NetexIdValidatingParser;
import no.entur.abt.netex.id.NetexIdValidator;
import no.entur.abt.netex.id.predicate.NetexIdCodespaceTypePredicate;
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

@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@BenchmarkMode(Mode.Throughput)
@Warmup(time=3, timeUnit=TimeUnit.SECONDS, iterations=1)
@Measurement(time=3, timeUnit=TimeUnit.SECONDS, iterations=1)
@Timeout(timeUnit=TimeUnit.SECONDS, time=10)
public class IdBenchmark {

  private static final NetexIdValidator validator = new DefaultNetexIdValidator();
  private static final NetexIdValidatingParser validatingParser = new NetexIdValidatingParser();
  private static final NetexIdNonvalidatingParser nonvalidatingParser = new NetexIdNonvalidatingParser();
  private static final NetexIdCodespaceTypePredicate predicate = new NetexIdCodespaceTypePredicate("ABC", "FareZone");

  @Benchmark
  public boolean validateNetexUtils() {
    return NetexIdUtils.isValid("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public boolean validateNetexUtilsLegacy() {
    return LegacyNetexIdUtils.isValid("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public boolean validate() {
    return validator.validate("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String typeNetexIdUtils() {
    return NetexIdUtils.getType("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String typeNetexIdUtilsLegacy() {
    return LegacyNetexIdUtils.getType("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String typeValidate() {
    return validatingParser.getType("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String typeNonValidate() {
    return nonvalidatingParser.getType("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String codespaceNetexIdUtils() {
    return NetexIdUtils.getCodespace("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String codespaceNetexIdUtilsLegacy() {
    return LegacyNetexIdUtils.getCodespace("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String codespaceValidate() {
    return validatingParser.getCodespace("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String codespaceNonValidate() {
    return nonvalidatingParser.getCodespace("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String valueNetexIdUtils() {
    return NetexIdUtils.getValue("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String valueNetexIdUtilsLegacy() {
    return LegacyNetexIdUtils.getValue("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String valueValidate() {
    return validatingParser.getValue("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String valueNonValidate() {
    return nonvalidatingParser.getValue("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String createFromNetexUtils() {
    return NetexIdUtils.createFrom("XXX:SecurityPolicy:æøåÆØÅ", "abc");
  }

  @Benchmark
  public String createFromNetexUtilsLegacy() {
    return LegacyNetexIdUtils.createFrom("XXX:SecurityPolicy:æøåÆØÅ", "abc");
  }

  @Benchmark
  public String createIdNetexUtils() {
    return NetexIdUtils.createId("XXX", "SecurityPolicy", "æøåÆØÅ");
  }

  @Benchmark
  public String createIdNetexUtilsLegacy() {
    return LegacyNetexIdUtils.createId("XXX", "SecurityPolicy", "æøåÆØÅ");
  }

  @Benchmark
  public boolean codespaceTypePredicate() {
    return predicate.test("ABC:FareZone:123");
  }

}
