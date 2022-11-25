package com.deuceng.voting.cypto.paillier;

import java.math.BigInteger;

import java.util.Random;

public class PaillierCryptoSystem {
  private PaillierCryptoSystem() {
  }

  public static PaillierKeyPair generateKeyPair(int bitlength, Random random) {
    BigInteger p = BigInteger.probablePrime(bitlength / 2, random);
    System.out.println("p=" + p.bitLength());
    BigInteger q = BigInteger.probablePrime(bitlength / 2, random);
    System.out.println("q=" + q.bitLength());
    BigInteger n = p.multiply(q);
    BigInteger lambda = phi(p, q).divide(p.subtract(BigInteger.ONE).gcd(q.subtract(BigInteger.ONE)));
    BigInteger g = BigInteger.ZERO;
    while (g.equals(BigInteger.ZERO)) {
      g = new BigInteger(n.bitLength(), random);
    }
    try {
      BigInteger mu = PaillierOps.L(g.modPow(lambda, n.multiply(n)), n).modInverse(n);
      return new PaillierKeyPair(new PaillierPublicKey(n, g, random), new PaillierPrivateKey(lambda, mu));
    } catch (ArithmeticException e) {
      System.err.println("Mu is not invertible, try again");
      return generateKeyPair(bitlength, random);
    }
  }

  private static BigInteger phi(BigInteger p, BigInteger q) {
    return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
  }

  public static BigInteger encrypt(BigInteger m, PaillierPublicKey pub) {
    return PaillierOps.encrypt(m, pub.getN(), pub.getG(), pub.getN2(), pub.getRandom());
  }

  public static BigInteger decrypt(BigInteger c, PaillierPrivateKey priv, PaillierPublicKey pub) {
    return PaillierOps.decrypt(c, priv.getLambda(), priv.getMu(), pub.getN(), pub.getN2());
  }

  public static BigInteger add(BigInteger c1, BigInteger c2, PaillierPublicKey pub) {
    return PaillierOps.add(c1, c2, pub.getN2());
  }

  public static BigInteger multiply(BigInteger c, BigInteger m, PaillierPublicKey pub) {
    return PaillierOps.multiply(c, m, pub.getN2());
  }
}