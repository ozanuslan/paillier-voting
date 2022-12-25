package com.deuceng.voting.system;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.deuceng.voting.cypto.paillier.PaillierCryptoSystem;
import com.deuceng.voting.cypto.paillier.PaillierKeyPair;

public class VotingSystem {
  private PaillierCryptoSystem pcs;
  private PaillierKeyPair keyPair;
  private List<Candidate> candidates;
  private HashMap<Integer, Integer> candidateIdToIndex;
  private int candidateCount;
  private BigInteger base;
  private List<BigInteger> votes;

  public VotingSystem(PaillierKeyPair keypair, Long seed, List<Candidate> candidates, Long voterCount) {
    this.keyPair = keypair;
    this.pcs = new PaillierCryptoSystem(new SecureRandom(seed.toString().getBytes()));
    this.candidates = candidates;
    this.candidateCount = candidates.size();
    this.candidateIdToIndex = new HashMap<>();
    for (int i = 0; i < candidateCount; i++) {
      candidateIdToIndex.put(candidates.get(i).getId(), i);
    }
    this.base = BigInteger.valueOf(voterCount);
    this.votes = new ArrayList<>();
  }

  public void addVote(int candidateId) throws UnknownCandidateException {
    votes.add(encryptVote(candidateId));
  }

  private BigInteger encryptVote(int candidateId) throws UnknownCandidateException {
    if (!candidateIdToIndex.containsKey(candidateId)) {
      throw new UnknownCandidateException("candidate = " + candidateId + " is invalid.");
    }
    var enc = pcs.encrypt(base.pow(getCandidateIndex(candidateId)), keyPair.getPublicKey());
    System.out.println("Encrypted vote: " + enc);
    return enc;
  }

  private int getCandidateIndex(int candidateId) {
    return candidateIdToIndex.get(candidateId);
  }

  private List<Candidate> countVotes(BigInteger sumOfVotes) {
    int i = 0;

    while (sumOfVotes != BigInteger.ZERO) {
      long voteCount = sumOfVotes.mod(base).longValue();
      getCandidate(i).setVotes(voteCount);
      sumOfVotes = sumOfVotes.divide(base);
      i++;
    }

    return candidates;
  }

  private Candidate getCandidate(int candidateIndex) {
    return candidates.get(candidateIndex);
  }

  private BigInteger sumEncyptedVotes(List<BigInteger> votes) {
    return votes.stream().reduce(BigInteger.ONE, (a, b) -> pcs.add(a, b, keyPair.getPublicKey()));
  }

  public List<Candidate> tallyVotes() {
    BigInteger voteSum = pcs.decrypt(sumEncyptedVotes(votes), keyPair.getPrivateKey(), keyPair.getPublicKey());
    return countVotes(voteSum);
  }
}
