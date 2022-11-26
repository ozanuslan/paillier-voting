package com.deuceng.voting.util;

import java.util.logging.Logger;

public final class LogUtil {

  private static Logger logger = Logger.getLogger(LogUtil.class.getName());

  private LogUtil() {
  }

  public static void log(LogLevel level, String message) {
    switch (level) {
      case INFO:
        logger.info(message);
        break;
      case WARNING:
        logger.warning(message);
        break;
      case ERROR:
        logger.severe(message);
        break;
    }
  }
}