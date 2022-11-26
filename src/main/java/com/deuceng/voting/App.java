package com.deuceng.voting;

import java.math.BigInteger;
import java.util.Random;

import com.deuceng.voting.cypto.paillier.PaillierCryptoSystem;
import com.deuceng.voting.cypto.paillier.PaillierKeyPair;

public class App {
    public static void main(String[] args) throws Exception {
        Random r = new Random();
        r.setSeed(1);
        PaillierCryptoSystem paillier = new PaillierCryptoSystem(r);
        PaillierKeyPair keyPair = paillier.generateKeyPair(128);
        BigInteger m = paillier.encrypt(BigInteger.valueOf(0), keyPair.getPublicKey());

        int iter = 1_000_000;
        BigInteger one = paillier.encrypt(BigInteger.valueOf(1), keyPair.getPublicKey());
        long start = System.nanoTime();
        for (int i = 0; i < iter; i++) {
            m = paillier.add(m, one, keyPair.getPublicKey());
        }
        long end = System.nanoTime();
        System.out.println("Time taken: " + (end - start) / 1_000_000 + "ms");
        System.out.println("Result: " + paillier.decrypt(m, keyPair.getPrivateKey(), keyPair.getPublicKey()));

        int n = 0;
        start = System.nanoTime();
        for (int i = 0; i < iter; i++) {
            n += 1;
        }
        end = System.nanoTime();
        System.out.println("Time taken: " + (end - start) / 1_000_000 + "ms");
        System.out.println("Result: " + n);
    }
}