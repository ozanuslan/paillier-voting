package com.deuceng.voting.cypto.paillier;

import java.math.BigInteger;
import java.security.PublicKey;
import java.util.Random;

public class PaillierPublicKey implements PublicKey {
  private static final long serialVersionUID = 1L;
  private BigInteger n;
  private BigInteger g;
  private BigInteger n2;
  private Random random;

  public PaillierPublicKey(BigInteger n, BigInteger g, Random random) {
    this.n = n;
    this.g = g;
    this.n2 = n.multiply(n);
    this.random = random;
  }

  public BigInteger getN() {
    return n;
  }

  public BigInteger getG() {
    return g;
  }

  public int getBitLength() {
    return n.bitLength();
  }

  public BigInteger getN2() {
    return n2;
  }

  public Random getRandom() {
    return random;
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
}
