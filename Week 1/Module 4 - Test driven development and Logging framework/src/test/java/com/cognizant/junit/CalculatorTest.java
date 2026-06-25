package com.cognizant.junit;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.cognizant.calculator.Calculator;

@DisplayName("Tests for the Calculator class")
public class CalculatorTest {
    
    private Calculator calc;
    
    @BeforeAll
    static void setUpAll() {
        System.out.println("Starting up, this runs once before everything");
    }
    
    @BeforeEach
    void setUp() {
        calc = new Calculator();
        System.out.println("Getting a fresh calculator ready for the next test");
    }
    
    @AfterEach
    void tearDown() {
        System.out.println("That test is done, cleaning up now");
    }
    
    @AfterAll
    static void tearDownAll() {
        System.out.println("All tests finished, wrapping things up");
    }
    
    @Test
    @DisplayName("Adding 5 and 3 should give us 8")
    void testAdd() {
        int output = calc.add(5, 3);
        assertEquals(8, output, "Adding 5 and 3 should give 8");
    }
    
    @Test
    @DisplayName("Adding negative numbers should work correctly")
    void testAddNegative() {
        assertEquals(-2, calc.add(-5, 3));
        assertEquals(-8, calc.add(-5, -3));
    }
    
    @Test
    @DisplayName("Subtracting numbers gives the right difference")
    void testSubtract() {
        assertEquals(2, calc.subtract(5, 3));
        assertEquals(-2, calc.subtract(3, 5));
    }
    
    @Test
    @DisplayName("Multiplying numbers works as expected")
    void testMultiply() {
        assertEquals(15, calc.multiply(5, 3));
        assertEquals(0, calc.multiply(5, 0));
        assertEquals(-15, calc.multiply(-5, 3));
    }
    
    @Test
    @DisplayName("Dividing numbers gives the right quotient")
    void testDivide() {
        assertEquals(2, calc.divide(6, 3));
        assertEquals(-2, calc.divide(6, -3));
    }
    
    @Test
    @DisplayName("Dividing by zero should throw an error")
    void testDivideByZero() {
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            calc.divide(10, 0);
        });
        assertEquals("Hey, you can't divide by zero", exception.getMessage());
    }
    
    @Test
    @DisplayName("Square root of 25 should be 5")
    void testSquareRoot() {
        assertEquals(5.0, calc.squareRoot(25), 0.001);
        assertEquals(0.0, calc.squareRoot(0), 0.001);
    }
    
    @Test
    @DisplayName("Square root of a negative number should blow up")
    void testSquareRootNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            calc.squareRoot(-4);
        });
    }
    
    @Test
    @DisplayName("Even numbers are correctly identified")
    void testIsEven() {
        assertTrue(calc.isEven(4), "4 is definitely even");
        assertTrue(calc.isEven(0), "0 counts as even");
        assertFalse(calc.isEven(3), "3 is odd, not even");
        assertFalse(calc.isEven(-5), "-5 is odd too");
    }
    
    @Test
    @DisplayName("Factorial gives the right results")
    void testFactorial() {
        assertEquals(1, calc.factorial(0));
        assertEquals(1, calc.factorial(1));
        assertEquals(2, calc.factorial(2));
        assertEquals(6, calc.factorial(3));
        assertEquals(24, calc.factorial(4));
        assertEquals(120, calc.factorial(5));
    }
    
    @Test
    @DisplayName("Factorial of a negative number should fail")
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            calc.factorial(-1);
        });
    }
    
    @Test
    @Disabled("Skipping this one for now")
    @DisplayName("This test is turned off on purpose")
    void testDisabled() {
        fail("This one shouldn't have run");
    }
    
    @Test
    @DisplayName("All basic operations work together")
    void testMultipleAssertions() {
        assertAll("Basic calculator checks",
            () -> assertEquals(8, calc.add(5, 3), "Addition didn't work right"),
            () -> assertEquals(2, calc.subtract(5, 3), "Subtraction didn't work right"),
            () -> assertEquals(15, calc.multiply(5, 3), "Multiplication didn't work right"),
            () -> assertEquals(5, calc.divide(15, 3), "Division didn't work right")
        );
    }
}
