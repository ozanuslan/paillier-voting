package com.deuceng.voting.cypto.paillier;

import java.math.BigInteger;

import java.util.Random;
import java.util.logging.Logger;

public class PaillierCryptoSystem {
  private Random random;

  public PaillierCryptoSystem(Random random) {
    this.random = random;
  }

  public PaillierKeyPair generateKeyPair(int bitlength) {
    BigInteger p = BigInteger.probablePrime(bitlength / 2, random);
    BigInteger q = BigInteger.probablePrime(bitlength / 2, random);
    BigInteger n = p.multiply(q);
    BigInteger lambda = phi(p, q).divide(p.subtract(BigInteger.ONE).gcd(q.subtract(BigInteger.ONE)));
    BigInteger g = BigInteger.ZERO;
    while (g.equals(BigInteger.ZERO)) {
      g = new BigInteger(n.bitLength(), random);
    }
    try {
      BigInteger mu = PaillierOps.L(g.modPow(lambda, n.multiply(n)), n).modInverse(n);
      return new PaillierKeyPair(new PaillierPublicKey(n, g), new PaillierPrivateKey(lambda, mu));
    } catch (ArithmeticException e) {
      Logger.getGlobal().log(java.util.logging.Level.SEVERE, "mu is not invertible");
      return generateKeyPair(bitlength);
    }
  }

  private static BigInteger phi(BigInteger p, BigInteger q) {
    return p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
  }

  public BigInteger encrypt(BigInteger m, PaillierPublicKey pub) {
    return PaillierOps.encrypt(m, pub.getN(), pub.getG(), pub.getN2(), random);
  }

  public BigInteger decrypt(BigInteger c, PaillierPrivateKey priv, PaillierPublicKey pub) {
    return PaillierOps.decrypt(c, priv.getLambda(), priv.getMu(), pub.getN(), pub.getN2());
  }

  public BigInteger add(BigInteger c1, BigInteger c2, PaillierPublicKey pub) {
    return PaillierOps.add(c1, c2, pub.getN2());
  }

  public BigInteger multiply(BigInteger c, BigInteger m, PaillierPublicKey pub) {
    return PaillierOps.multiply(c, m, pub.getN2());
  }
}