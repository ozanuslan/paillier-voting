package com.deuceng.voting.cmd;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.deuceng.voting.cypto.paillier.PaillierCryptoSystem;
import com.deuceng.voting.cypto.paillier.PaillierKeyPair;
import com.deuceng.voting.system.Candidate;
import com.deuceng.voting.system.UnknownCandidateException;
import com.deuceng.voting.system.VotingSystem;

public class VotingCommandLine {
  private int keyPairBitLength;
  private long voterCount;
  private long votesCast;
  private long candidateCount;
  private List<Candidate> candidates;
  private PaillierKeyPair keyPair;
  private long seed;
  private VotingSystem votingSystem;
  private Scanner sc;
  private Random random;

  public VotingCommandLine() {
    this.votesCast = 0;
    sc = new Scanner(System.in);
  }

  public void displayMainMenu() {
    System.out.println("\n\nWelcome to the Voting System");
    System.out.println("1. Create a new voting system");
    System.out.println("2. Exit");

    System.out.print("Choice: ");
    String choice = sc.nextLine();
    switch (choice) {
      case "1":
        clearParameters();
        displayVotingSystemCreationMenu();
        break;
      case "2":
        System.out.println("Exiting...");
        System.exit(0);
        break;
      default:
        System.out.println("Invalid choice. Please try again.");
        displayMainMenu();
    }

    sc.close();
  }

  private void clearParameters() {
    this.keyPairBitLength = 0;
    this.voterCount = 0;
    this.votesCast = 0;
    this.candidateCount = 0;
    this.candidates = null;
    this.keyPair = null;
    this.seed = 0;
    this.votingSystem = null;

    System.gc();
  }

  public void displayVotingSystemCreationMenu() {
    System.out.println("\n\nVoting System Creation Menu");

    System.out.print("Please enter candidate count: ");
    // try to parse to long and check if it is positive
    // if not, display error message and ask again
    // if yes, continue
    String candidateCount = sc.nextLine();
    try {
      long candidateCountLong = Long.parseLong(candidateCount);
      if (candidateCountLong <= 0) {
        System.out.println("Candidate count must be a positive integer.");
        displayVotingSystemCreationMenu();
      }
      this.candidateCount = candidateCountLong;
    } catch (NumberFormatException e) {
      System.out.println("Candidate count must be a positive integer.");
      displayVotingSystemCreationMenu();
    }

    System.out.print("Please enter voter count: ");
    // try to parse to long and check if it is positive
    // if not, display error message and ask again
    // if yes, continue
    String voterCount = sc.nextLine();
    try {
      long voterCountLong = Long.parseLong(voterCount);
      if (voterCountLong <= 0) {
        System.out.println("Voter count must be a positive integer.");
        displayVotingSystemCreationMenu();
      }
      this.voterCount = voterCountLong;
    } catch (NumberFormatException e) {
      System.out.println("Voter count must be a positive integer.");
      displayVotingSystemCreationMenu();
    }

    System.out.print("Please enter key pair bit length: ");
    // try to parse to int and check if it is positive
    // if not, display error message and ask again
    // if yes, continue
    String keyPairBitLength = sc.nextLine();
    try {
      int keyPairBitLengthInt = Integer.parseInt(keyPairBitLength);
      if (keyPairBitLengthInt <= 0) {
        System.out.println("Key pair bit length must be a positive integer.");
        displayVotingSystemCreationMenu();
      }
      this.keyPairBitLength = keyPairBitLengthInt;
    } catch (NumberFormatException e) {
      System.out.println("Key pair bit length must be a positive integer.");
      displayVotingSystemCreationMenu();
    }

    System.out.print("Please enter seed(0 for random seed): ");
    // try to parse to long
    // if not, display error message and ask again
    // if yes, continue
    String seed = sc.nextLine();
    try {
      long seedLong = Long.parseLong(seed);
      this.seed = seedLong;
    } catch (NumberFormatException e) {
      System.out.println("Seed must be a long integer.");
      displayVotingSystemCreationMenu();
    }

    // if seed is 0, create a random seed
    if (this.seed == 0) {
      this.seed = Math.abs(new Random().nextLong()) + 1;
    }

    // create a random object with the seed
    this.random = new Random(this.seed);

    // create the voting system
    this.keyPair = PaillierCryptoSystem.generateKeyPair(this.keyPairBitLength, new SecureRandom());
    this.candidates = createCandidates(this.candidateCount);
    this.votingSystem = new VotingSystem(keyPair, this.seed, this.candidates, this.voterCount);
    System.out.println("Voting system created successfully.");

    // display the parameters
    displayVotingSystemParameters();

    displayVotingSystemMenu();
  }

  private void displayVotingSystemParameters() {
    System.out.println("");
    System.out.println("------------------------");
    System.out.println("Voting System Parameters");
    System.out.println("------------------------");

    System.out.println("Candidate Count: " + candidateCount);
    System.out.println("Voter Count: " + voterCount);
    System.out.println("Key Pair Bit Length: " + keyPairBitLength);
    System.out.println("Seed: " + seed);
    System.out.println("");
    System.out.println("Candidates: ");
    System.out.println(candidates.stream().map(Candidate::toString).collect(Collectors.joining("\n")));
    System.out.println("");
    System.out.println("Public Key: " + keyPair.getPublicKey());
    System.out.println("Private Key: " + keyPair.getPrivateKey());
    System.out.println("------------------------");
  }

  private List<Candidate> createCandidates(long candidateCount) {
    List<Candidate> candidates = new ArrayList<>();
    for (int i = 0; i < candidateCount; i++) {
      candidates.add(new Candidate(i, "Candidate " + i));
    }
    return candidates;
  }

  public void displayVotingSystemMenu() {
    System.out.println("\n\nVoting System Menu");
    System.out.println("1. Start Voting Randomly");
    System.out.println("2. Cast a Vote");
    System.out.println("3. Display Results");
    System.out.println("4. Display Voting System Parameters");
    System.out.println("5. Return to Main Menu");
    System.out.println("6. Exit");

    System.out.print("Choice: ");
    String choice = sc.nextLine();
    switch (choice) {
      case "1":
        if (isVotingComplete()) {
          System.out.println("Voting already complete.");
          displayVotingSystemMenu();
        }
        startVotingRandomly();
        break;
      case "2":
        if (isVotingComplete()) {
          System.out.println("Voting already complete.");
          displayVotingSystemMenu();
        }
        castAVote();
        break;
      case "3":
        displayResults();
        break;
      case "4":
        displayVotingSystemParameters();
        displayVotingSystemMenu();
        break;
      case "5":
        displayMainMenu();
        break;
      case "6":
        System.out.println("Exiting...");
        System.exit(0);
        break;
      default:
        System.out.println("Invalid choice. Please try again.");
        displayVotingSystemMenu();
    }
  }

  private boolean isVotingComplete() {
    return this.votesCast == this.voterCount;
  }

  private void castAVote() {
    System.out.print("Please enter candidate index[0-" + (this.candidateCount - 1) + "]: ");
    String candidateIndexString = sc.nextLine();
    try {
      int candidateIndex = Integer.parseInt(candidateIndexString);
      if (candidateIndex < 0 || candidateIndex >= this.candidateCount) {
        System.out.println("Invalid candidate index.");
        castAVote();
      }
      this.votingSystem.addVote(candidateIndex);
      this.votesCast++;
      System.out.println("Vote cast successfully.");
      displayVotingSystemMenu();
    } catch (NumberFormatException e) {
      System.out.println("Invalid candidate index.");
      castAVote();
    } catch (UnknownCandidateException e) {
      System.out.println("Unknown candidate exception occured.");
      displayVotingSystemMenu();
    }
  }

  public void startVotingRandomly() {
    System.out.println("\nStarting voting randomly...");
    long votesToCast = this.voterCount - this.votesCast;
    for (long i = 0; i < votesToCast; i++) {
      try {
        int candidateIndex = this.random.nextInt((int) this.candidateCount);
        System.out.println("Voter " + i + " voting for: " + this.candidates.get(candidateIndex).getName());
        this.votingSystem.addVote(candidateIndex);
        this.votesCast++;
      } catch (UnknownCandidateException e) {
        System.out.println("Unknown candidate exception occured.");
        break;
      }
    }
    System.out.println("Voting completed successfully.");
    displayVotingSystemMenu();
  }

  public void displayResults() {
    List<Candidate> result = this.votingSystem.tallyVotes();
    System.out.println("Votes tallied successfully.");

    List<Candidate> sorted = result.stream()
        .sorted((a, b) -> Long.compare(b.getVotes(), a.getVotes()))
        .collect(Collectors.toList());

    System.out.println("Results");
    for (Candidate candidate : sorted) {
      System.out.println(candidate);
    }
    displayVotingSystemMenu();
  }
}
