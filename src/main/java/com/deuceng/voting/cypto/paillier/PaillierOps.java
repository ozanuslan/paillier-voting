package com.deuceng.voting.cypto.paillier;

import java.math.BigInteger;
import java.util.Random;

final class PaillierOps {
  private PaillierOps() {
  }

  public static BigInteger encrypt(BigInteger m, BigInteger n, BigInteger g, BigInteger n2, Random random) {
    // r is a random integer in Z_n and it satisfies 0 < r < n
    BigInteger r = BigInteger.ZERO;
    while (r.equals(BigInteger.ZERO)) {
      r = new BigInteger(n.bitLength(), random);
    }

    // c = g^m * r^n mod n^2
    return g.modPow(m, n2).multiply(r.modPow(n, n2)).mod(n2);
  }

  public static BigInteger decrypt(BigInteger c, BigInteger lambda, BigInteger mu, BigInteger n, BigInteger n2) {
    // m = L(c^lambda mod n^2) * mu mod n
    return L(c.modPow(lambda, n2), n).multiply(mu).mod(n);
  }

  public static BigInteger L(BigInteger x, BigInteger n) {
    // L(x) = (x - 1) / n
    return x.subtract(BigInteger.ONE).divide(n);
  }

  public static BigInteger add(BigInteger c1, BigInteger c2, BigInteger n2) {
    // c = c1 * c2 mod n^2
    return c1.multiply(c2).mod(n2);
  }

  public static BigInteger multiply(BigInteger c, BigInteger m, BigInteger n2) {
    // c = c^m mod n^2
    return c.modPow(m, n2);
  }
}
