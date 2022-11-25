package com.deuceng.voting.cypto.paillier;

public class PaillierKeyPair {
  private PaillierPublicKey publicKey;
  private PaillierPrivateKey privateKey;

  public PaillierKeyPair(PaillierPublicKey publicKey, PaillierPrivateKey privateKey) {
    this.publicKey = publicKey;
    this.privateKey = privateKey;
  }

  public PaillierPublicKey getPublicKey() {
    return publicKey;
  }

  public PaillierPrivateKey getPrivateKey() {
    return privateKey;
  }
}
