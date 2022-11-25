package com.deuceng.voting;

import java.math.BigInteger;
import java.util.Random;

import com.deuceng.voting.cypto.paillier.PaillierCryptoSystem;

public class App {
    public static void main(String[] args) throws Exception {
        Random r = new Random();
        r.setSeed(6);
        var keyPair = PaillierCryptoSystem.generateKeyPair(128, r);
        var encrypted = PaillierCryptoSystem.encrypt(BigInteger.valueOf(125), keyPair.getPublicKey());
        System.out.println("Encrypted=" + encrypted);
        var decrypted = PaillierCryptoSystem.decrypt(encrypted, keyPair.getPrivateKey(), keyPair.getPublicKey());
        System.out.println("Decrypted=" + decrypted);
        var added = PaillierCryptoSystem.multiply(encrypted, BigInteger.valueOf(2), keyPair.getPublicKey());
        System.out.println("Added(Encrypted)=" + added);
        System.out.println(
                "Doubled=" + PaillierCryptoSystem.decrypt(added, keyPair.getPrivateKey(), keyPair.getPublicKey()));
        System.out.println("Encrypted bit length=" + encrypted.bitLength());
        System.out.println("PublicKey bit length=" + keyPair.getPublicKey().getBitLength());
    }
}