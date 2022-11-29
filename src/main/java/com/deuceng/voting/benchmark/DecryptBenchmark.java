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
import com.deuceng.voting.cypto.paillier.PaillierKeyPair;
import com.deuceng.voting.cypto.paillier.PaillierPrivateKey;
import com.deuceng.voting.cypto.paillier.PaillierPublicKey;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class DecryptBenchmark {
  @Param({ "128", "256", "512", "1024", "2048", "4096" })
  private int keyBitLength;
  private static Random random = new Random(1);
  private PaillierCryptoSystem paillierCryptoSystem = new PaillierCryptoSystem(random);
  private PaillierPublicKey publicKey;
  private PaillierPrivateKey privateKey;
  private BigInteger plaintext = BigInteger.valueOf(1);
  private BigInteger ciphertext;

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(DecryptBenchmark.class.getSimpleName())
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
    PaillierKeyPair keyPair = paillierCryptoSystem.generateKeyPair(keyBitLength);
    publicKey = keyPair.getPublicKey();
    privateKey = keyPair.getPrivateKey();
    ciphertext = paillierCryptoSystem.encrypt(plaintext, publicKey);
  }

  @Benchmark
  public void decrypt(Blackhole bh) {
    paillierCryptoSystem.decrypt(ciphertext, privateKey, publicKey);
  }
}
