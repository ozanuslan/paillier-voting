package com.deuceng.voting.cypto.paillier;

import java.math.BigInteger;
import java.security.PrivateKey;

public class PaillierPrivateKey implements PrivateKey {
  private static final long serialVersionUID = 1L;
  private BigInteger lambda;
  private BigInteger mu;

  public PaillierPrivateKey(BigInteger lambda, BigInteger mu) {
    this.lambda = lambda;
    this.mu = mu;
  }

  public BigInteger getLambda() {
    return lambda;
  }

  public BigInteger getMu() {
    return mu;
  }

  @Override
  public String getAlgorithm() {
    return "Paillier";
  }

  @Override
  public String getFormat() {
    return "X.509";
  }

  @Override
  public byte[] getEncoded() {
    return new byte[0];
  }

  @Override
  public String toString() {
    return "PaillierPrivateKey{" +
        "lambda=" + lambda +
        ", mu=" + mu +
        '}';
  }
}
