package com.deuceng.voting.system;

public class UnknownCandidateException extends Exception {
  private static final long serialVersionUID = 1L;

  public UnknownCandidateException() {
    super();
  }

  public UnknownCandidateException(String message) {
    super(message);
  }

  public UnknownCandidateException(String message, Throwable cause) {
    super(message, cause);
  }

  public UnknownCandidateException(Throwable cause) {
    super(cause);
  }

  protected UnknownCandidateException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
