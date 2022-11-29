package com.deuceng.voting.crypto.paillier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigInteger;
import java.util.Random;

import org.junit.Test;

import com.deuceng.voting.cypto.paillier.PaillierCryptoSystem;
import com.deuceng.voting.cypto.paillier.PaillierKeyPair;

public class PaillierTest {
  private static Random random = new Random(1);

  @Test
  public void shouldCreateKeyPair() {
    PaillierCryptoSystem pcs = new PaillierCryptoSystem(random);
    PaillierKeyPair kp = pcs.generateKeyPair(1024);
    assertNotNull(kp);
    assertNotNull(kp.getPublicKey());
    assertNotNull(kp.getPrivateKey());
  }

  @Test
  public void shouldEncryptAndDecrypt() {
    PaillierCryptoSystem pcs = new PaillierCryptoSystem(random);
    PaillierKeyPair kp = pcs.generateKeyPair(1024);

    BigInteger message = BigInteger.valueOf(123456789);
    BigInteger encrypted = pcs.encrypt(message, kp.getPublicKey());
    BigInteger decrypted = pcs.decrypt(encrypted, kp.getPrivateKey(), kp.getPublicKey());
    assertEquals(message, decrypted);
  }

  @Test
  public void shouldAddEncrypted() {
    PaillierCryptoSystem pcs = new PaillierCryptoSystem(random);
    PaillierKeyPair kp = pcs.generateKeyPair(1024);

    BigInteger message1 = BigInteger.valueOf(123456789);
    BigInteger message2 = BigInteger.valueOf(987654321);
    BigInteger encrypted1 = pcs.encrypt(message1, kp.getPublicKey());
    BigInteger encrypted2 = pcs.encrypt(message2, kp.getPublicKey());
    BigInteger encryptedSum = pcs.add(encrypted1, encrypted2, kp.getPublicKey());
    BigInteger decryptedSum = pcs.decrypt(encryptedSum, kp.getPrivateKey(), kp.getPublicKey());
    assertEquals(message1.add(message2), decryptedSum);
  }

  @Test
  public void shouldMultiplyEncrypted() {
    PaillierCryptoSystem pcs = new PaillierCryptoSystem(random);
    PaillierKeyPair kp = pcs.generateKeyPair(1024);

    BigInteger message1 = BigInteger.valueOf(123456789);
    BigInteger multiple = BigInteger.valueOf(987654321);
    BigInteger encrypted1 = pcs.encrypt(message1, kp.getPublicKey());
    BigInteger encryptedProduct = pcs.multiply(encrypted1, multiple, kp.getPublicKey());
    BigInteger decryptedProduct = pcs.decrypt(encryptedProduct, kp.getPrivateKey(), kp.getPublicKey());
    assertEquals(message1.multiply(multiple), decryptedProduct);
  }

}
