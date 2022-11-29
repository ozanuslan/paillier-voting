package com.deuceng.voting.benchmark;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.openjdk.jmh.runner.options.WarmupMode;

import com.deuceng.voting.cypto.paillier.PaillierCryptoSystem;
import com.deuceng.voting.cypto.paillier.PaillierPublicKey;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class BenchmarkEncrypt {
  @Param({ "128", "256", "512", "1024", "2048", "4096" })
  private int keyBitLength;
  private static PaillierCryptoSystem paillierCryptoSystem;
  private static PaillierPublicKey publicKey;
  private static BigInteger message = BigInteger.valueOf(1);

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(BenchmarkEncrypt.class.getSimpleName())
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

  @Setup
  public void setup() {
    Random random = new Random(1);
    paillierCryptoSystem = new PaillierCryptoSystem(random);
    publicKey = paillierCryptoSystem.generateKeyPair(keyBitLength).getPublicKey();
  }

  @Benchmark
  public void encrypt(Blackhole bh) {
    bh.consume(paillierCryptoSystem.encrypt(message, publicKey));
  }
}
