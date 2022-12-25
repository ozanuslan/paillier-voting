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
        PaillierKeyPair keyPair = PaillierCryptoSystem.generateKeyPair(1024, new SecureRandom());
        List<Candidate> candidates = List.of(
                new Candidate(4, "A"),
                new Candidate(3, "B"),
                new Candidate(2, "C"),
                new Candidate(1, "D"));
        VotingSystem vs = new VotingSystem(keyPair, 1234l, candidates, 5l);

        vs.addVote(4);
        vs.addVote(4);

        vs.addVote(3);

        vs.addVote(1);
        vs.addVote(1);
        vs.addVote(1);

        var result = vs.tallyVotes();

        List<Candidate> sorted = result.stream()
                .sorted((a, b) -> Long.compare(b.getVotes(), a.getVotes()))
                .collect(Collectors.toList());

        for (Candidate c : sorted) {
            System.out.println(c);
        }
    }
}