package com.cognizant.junit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cognizant.calculator.Calculator;

@DisplayName("Checking how exceptions are handled")
public class ExceptionTestDemo {
    
    private Calculator calc;
    
    @BeforeEach
    void setUp() {
        calc = new Calculator();
    }
    
    @Test
    @DisplayName("Dividing by zero should give an error")
    void testDivisionByZeroThrowsArithmeticException() {
        ArithmeticException exception = assertThrows(
            ArithmeticException.class,
            () -> calc.divide(10, 0),
            "We expected an ArithmeticException here"
        );
        
        assertEquals("Hey, you can't divide by zero", exception.getMessage());
    }
    
    @Test
    @DisplayName("Negative square root should cause an error")
    void testNegativeSquareRootThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> calc.squareRoot(-16)
        );
        
        assertTrue(exception.getMessage().contains("negative"));
    }
    
    @Test
    @DisplayName("Negative factorial should throw an error")
    void testNegativeFactorialThrowsException() {
        assertThrows(
            IllegalArgumentException.class,
            () -> calc.factorial(-5),
            "Negative factorial should break"
        );
    }
    
    @Test
    @DisplayName("Normal operations should run without any issues")
    void testNoExceptionForValidOperations() {
        assertDoesNotThrow(() -> {
            calc.divide(10, 2);
            calc.squareRoot(25);
            calc.factorial(5);
        }, "These are valid inputs so nothing should go wrong");
    }
    
    @Test
    @DisplayName("The error message should match what we expect")
    void testExceptionMessage() {
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> calc.factorial(-1)
        );
        
        String expectedMsg = "Sorry, factorial doesn't work with negative numbers";
        String actualMsg = exception.getMessage();
        
        assertEquals(expectedMsg, actualMsg);
    }
    
    @Test
    @DisplayName("All the bad inputs should throw errors")
    void testMultipleExceptions() {
        assertThrows(ArithmeticException.class, () -> calc.divide(5, 0));
        assertThrows(IllegalArgumentException.class, () -> calc.squareRoot(-1));
        assertThrows(IllegalArgumentException.class, () -> calc.factorial(-1));
    }
    
    @Test
    @DisplayName("These errors are all RuntimeExceptions underneath")
    void testExceptionTypeHierarchy() {
        assertThrows(RuntimeException.class, () -> calc.divide(10, 0));
        assertThrows(RuntimeException.class, () -> calc.squareRoot(-1));
    }
    
    @Test
    @DisplayName("Checking all errors at once with assertAll")
    void testExceptionsWithAssertAll() {
        assertAll("All these should throw errors",
            () -> assertThrows(ArithmeticException.class, 
                () -> calc.divide(1, 0)),
            () -> assertThrows(IllegalArgumentException.class, 
                () -> calc.squareRoot(-1)),
            () -> assertThrows(IllegalArgumentException.class, 
                () -> calc.factorial(-1))
        );
    }
    
    @Test
    @DisplayName("Catching the error ourselves should work too")
    void testExceptionThrownAndCaught() {
        try {
            calc.divide(10, 0);
            fail("We expected it to throw an error here");
        } catch (ArithmeticException e) {
            assertEquals("Hey, you can't divide by zero", e.getMessage());
        }
    }
}
