package com.deuceng.voting;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import com.deuceng.voting.cypto.paillier.PaillierCryptoSystem;
import com.deuceng.voting.cypto.paillier.PaillierKeyPair;
import com.deuceng.voting.system.Candidate;
import com.deuceng.voting.system.VotingSystem;

public class App {
    public static void main(String[] args) throws Exception {
        PaillierKeyPair keyPair = PaillierCryptoSystem.generateKeyPair(16, new SecureRandom());
        System.out.println("Key pair generated. Bit length: " + 16);
        System.out.println("Public key: " + keyPair.getPublicKey());
        System.out.println("Private key: " + keyPair.getPrivateKey());
        List<Candidate> candidates = List.of(
                new Candidate(1, "A"),
                new Candidate(2, "B"));
        System.out.println("Candidates: ");
        for (Candidate c : candidates) {
            System.out.println(c);
        }

        VotingSystem vs = new VotingSystem(keyPair, 1l, candidates, 3l);
        System.out.println("Voting system created. Seed: " + 1l + ", Voter count: " + 3l);

        System.out.println("Adding vote for voter 1: candidate 1");
        vs.addVote(1);
        System.out.println("Adding vote for voter 2: candidate 2");
        vs.addVote(2);
        System.out.println("Adding vote for voter 3: candidate 1");
        vs.addVote(1);

        var result = vs.tallyVotes();
        System.out.println("Votes tallied.");

        List<Candidate> sorted = result.stream()
                .sorted((a, b) -> Long.compare(b.getVotes(), a.getVotes()))
                .collect(Collectors.toList());

        System.out.println("Sorted results: ");
        for (Candidate c : sorted) {
            System.out.println(c);
        }
    }
}