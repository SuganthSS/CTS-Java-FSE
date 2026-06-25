package com.cognizant.junit;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import com.cognizant.calculator.Calculator;

@DisplayName("Making sure things finish fast enough")
public class TimeoutTestDemo {
    
    private Calculator calc;
    
    @BeforeEach
    void setUp() {
        calc = new Calculator();
    }
    
    @Test
    @DisplayName("Addition should finish in under a second")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void testAdditionWithTimeout() {
        int output = calc.add(5, 3);
        assertEquals(8, output);
    }
    
    @Test
    @DisplayName("Multiplication should be super quick")
    void testMultiplicationWithAssertTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> {
            int output = calc.multiply(5, 3);
            assertEquals(15, output);
        });
    }
    
    @Test
    @DisplayName("Factorial of 10 should be instant")
    @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
    void testFactorialWithTimeout() {
        long output = calc.factorial(10);
        assertEquals(3628800, output);
    }
    
    @Test
    @DisplayName("Division should not take long at all")
    void testDivisionWithAssertTimeout() {
        assertTimeout(Duration.ofMillis(50), () -> {
            calc.divide(100, 5);
        }, "This division took way too long");
    }
    
    @Test
    @DisplayName("This should stop if it takes too long")
    void testWithAssertTimeoutPreemptively() {
        assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
            calc.add(1, 2);
        }, "It should finish before the time runs out");
    }
    
    @Test
    @DisplayName("Running several operations should still be fast")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void testMultipleOperations() {
        assertAll("Multiple operations",
            () -> assertEquals(8, calc.add(5, 3)),
            () -> assertEquals(15, calc.multiply(5, 3)),
            () -> assertEquals(2, calc.subtract(5, 3)),
            () -> assertEquals(5, calc.divide(15, 3))
        );
    }
    
    @Test
    @DisplayName("Square root calculation should be quick")
    void testSquareRootWithTimeout() {
        assertTimeout(Duration.ofMillis(100), () -> {
            double output = calc.squareRoot(144);
            assertEquals(12.0, output, 0.001);
        });
    }
    
    @Test
    @DisplayName("Adding 1 to 100 in a loop should still be fast")
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testLoopOperationsWithTimeout() {
        int total = 0;
        for (int idx = 1; idx <= 100; idx++) {
            total = calc.add(total, idx);
        }
        assertEquals(5050, total);
    }
    
    @Test
    @DisplayName("We can return a value and check timeout at the same time")
    void testReturningValueWithTimeout() {
        String output = assertTimeout(Duration.ofMillis(100), () -> {
            calc.multiply(2, 3);
            return "Done";
        });
        assertEquals("Done", output);
    }
    
    @Test
    @DisplayName("Factorial should finish within the time limit")
    void testTimeoutWithMessageSupplier() {
        assertTimeout(
            Duration.ofMillis(100),
            () -> calc.factorial(5),
            () -> "The factorial took longer than expected"
        );
    }
    
    @Test
    @DisplayName("A small delay is okay, it should still pass")
    @Timeout(value = 2, unit = TimeUnit.SECONDS)
    void testSlowOperation() throws InterruptedException {
        Thread.sleep(100);
        int output = calc.add(10, 20);
        assertEquals(30, output);
    }
    
    @Test
    @DisplayName("Even a big factorial should finish in time")
    @Timeout(value = 1, unit = TimeUnit.SECONDS)
    void testLargerFactorial() {
        long output = calc.factorial(15);
        assertTrue(output > 0, "The result should be a positive number");
    }
}
