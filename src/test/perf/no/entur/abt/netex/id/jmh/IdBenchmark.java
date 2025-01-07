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
@Warmup(time=5, timeUnit=TimeUnit.SECONDS, iterations=1)
@Measurement(time=5, timeUnit=TimeUnit.SECONDS, iterations=1)
@Timeout(timeUnit=TimeUnit.SECONDS, time=10)
public class IdBenchmark {

  private static final NetexIdValidator validator = new DefaultNetexIdValidator();
  private static final NetexIdValidatingParser validatingParser = new NetexIdValidatingParser();
  private static final NetexIdNonvalidatingParser nonvalidatingParser = new NetexIdNonvalidatingParser();
  @Benchmark
  public boolean validateClassic() {
    return NetexIdUtils.isValid("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public boolean validateModern() {
    return validator.validate("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String typeClassic() {
    return NetexIdUtils.getType("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String typeModernValidate() {
    return validatingParser.getType("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String typeModernNonValidate() {
    return nonvalidatingParser.getType("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String codespaceClassic() {
    return NetexIdUtils.getCodespace("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String codespaceModernValidate() {
    return validatingParser.getCodespace("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String codespaceModernNonValidate() {
    return nonvalidatingParser.getCodespace("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String valueClassic() {
    return NetexIdUtils.getValue("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String valueModernValidate() {
    return validatingParser.getValue("XXX:SecurityPolicy:æøåÆØÅ");
  }

  @Benchmark
  public String valueModernNonValidate() {
    return nonvalidatingParser.getValue("XXX:SecurityPolicy:æøåÆØÅ");
  }

}
