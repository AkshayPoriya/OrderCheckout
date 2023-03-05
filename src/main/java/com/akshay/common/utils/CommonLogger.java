package com.akshay.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommonLogger {

    private static final Logger logger = LoggerFactory.getLogger(CommonLogger.class);

    public void logInfo(String message) {
        logger.info(message);
    }

    public void logError(String message, Throwable throwable) {
        logger.error(message, throwable);
    }
}
