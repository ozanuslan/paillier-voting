package com.deuceng.voting.cypto.paillier;

import java.math.BigInteger;

import java.util.Random;

import com.deuceng.voting.util.LogLevel;
import com.deuceng.voting.util.LogUtil;

public class PaillierCryptoSystem {
  private Random random;

  public PaillierCryptoSystem(Random random) {
    this.random = random;
  }

  public PaillierKeyPair generateKeyPair(int bitlength) {
    try {
      return PaillierOps.generateKeyPair(bitlength, random);
    } catch (ArithmeticException e) {
      LogUtil.log(LogLevel.ERROR, e.getMessage());
      return generateKeyPair(bitlength);
    }
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