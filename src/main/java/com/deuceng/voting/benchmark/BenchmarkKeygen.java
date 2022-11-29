package com.deuceng.voting.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.openjdk.jmh.runner.options.WarmupMode;

import com.deuceng.voting.cypto.paillier.PaillierCryptoSystem;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BenchmarkKeygen {
  @Param({ "128", "256", "512", "1024", "2048", "4096" })
  private int keyBitLength;
  private static PaillierCryptoSystem paillierCryptoSystem;

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(BenchmarkKeygen.class.getSimpleName())
        .forks(1)
        .jvmArgs("-Xms4G", "-Xmx4G")
        .warmupMode(WarmupMode.INDI)
        .warmupIterations(5)
        .measurementIterations(5)
        .measurementTime(TimeValue.seconds(5))
        .warmupTime(TimeValue.seconds(5))
        .build();

    new Runner(opt).run();
  }

  @Setup(Level.Invocation)
  public void setup() {
    Random random = new Random(1);
    paillierCryptoSystem = new PaillierCryptoSystem(random);
  }

  @Benchmark
  public void generateKeyPair(Blackhole bh) {
    bh.consume(paillierCryptoSystem.generateKeyPair(keyBitLength));
  }

}
