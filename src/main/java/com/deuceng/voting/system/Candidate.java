package com.deuceng.voting.system;

public class Candidate {
  private int id;
  private String name;
  private long votes;

  public Candidate(int id, String name) {
    this.id = id;
    this.name = name;
    this.votes = 0;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public long getVotes() {
    return votes;
  }

  public void setVotes(long votes) {
    this.votes = votes;
  }

  @Override
  public String toString() {
    return "Candidate [id=" + id + ", name=" + name + ", votes=" + votes + "]";
  }
}