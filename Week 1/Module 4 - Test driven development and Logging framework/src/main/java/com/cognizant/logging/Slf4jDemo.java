package com.cognizant.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jDemo {
    
    private static final Logger logger = LoggerFactory.getLogger(Slf4jDemo.class);
    
    public static void main(String[] args) {
        logger.info("Alright, starting up the logging demo now");
        
        demonstrateLoggingLevels();
        demonstrateParameterizedLogging();
        demonstrateExceptionLogging();
        
        logger.info("All done, the logging demo ran without issues");
    }
    
    public static void demonstrateLoggingLevels() {
        logger.trace("This is a trace message, super detailed stuff");
        logger.debug("Just a debug note to help figure things out");
        logger.info("Here's some general info about what's happening");
        logger.warn("Heads up, something might be off here");
        logger.error("Yikes, something broke");
    }
    
    public static void demonstrateParameterizedLogging() {
        String userName = "jane_smith";
        int retryCount = 3;
        
        logger.info("Looks like {} tried to log in {} times", userName, retryCount);
        
        String operation = "UPDATE";
        String targetResource = "user_profile";
        long elapsedTime = 150;
        logger.debug("Ran {} on {} and it took {}ms", operation, targetResource, elapsedTime);
    }
    
    public static void demonstrateExceptionLogging() {
        try {
            divideNumbers(10, 0);
        } catch (Exception e) {
            logger.error("Something went wrong while dividing numbers", e);
        }
    }
    
    private static int divideNumbers(int topValue, int bottomValue) {
        logger.debug("Trying to divide {} by {}", topValue, bottomValue);
        
        if (bottomValue == 0) {
            logger.warn("Whoa, someone tried to divide by zero");
            throw new ArithmeticException("Hey, you can't divide by zero");
        }
        
        return topValue / bottomValue;
    }
    
    public double calculateScore(int rightAnswers, int totalQuestions) {
        logger.info("Figuring out the score: got {} right out of {} questions", rightAnswers, totalQuestions);
        
        if (totalQuestions <= 0) {
            logger.error("That total doesn't make sense: {}", totalQuestions);
            throw new IllegalArgumentException("The total has to be at least 1");
        }
        
        double percentage = (double) rightAnswers / totalQuestions * 100;
        logger.debug("Got a score of {}%", percentage);
        
        return percentage;
    }
}
