package com.cognizant.junit;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import com.cognizant.calculator.Calculator;

@DisplayName("Running tests with different inputs")
public class ParameterizedTestDemo {
    
    private Calculator calc;
    
    @BeforeEach
    void setUp() {
        calc = new Calculator();
    }
    
    @ParameterizedTest
    @DisplayName("Adding different pairs of numbers")
    @CsvSource({
        "1, 1, 2",
        "5, 3, 8",
        "10, 20, 30",
        "-5, 5, 0",
        "-10, -20, -30"
    })
    void testAdditionWithCsvSource(int a, int b, int expected) {
        assertEquals(expected, calc.add(a, b),
            () -> a + " + " + b + " should equal " + expected);
    }
    
    @ParameterizedTest
    @DisplayName("These numbers should all be even")
    @ValueSource(ints = {2, 4, 6, 8, 10})
    void testEvenNumbers(int number) {
        assertTrue(calc.isEven(number), 
            () -> number + " should be even");
    }
    
    @ParameterizedTest
    @DisplayName("Factorial for a bunch of numbers")
    @CsvSource({
        "0, 1",
        "1, 1",
        "2, 2",
        "3, 6",
        "4, 24",
        "5, 120"
    })
    void testFactorial(int input, long expected) {
        assertEquals(expected, calc.factorial(input));
    }
    
    @ParameterizedTest
    @DisplayName("Division with different number combos")
    @CsvSource({
        "10, 2, 5",
        "20, 4, 5",
        "100, 10, 10",
        "-10, 2, -5",
        "10, -2, -5"
    })
    void testDivision(int dividend, int divisor, int expected) {
        assertEquals(expected, calc.divide(dividend, divisor));
    }
    
    @ParameterizedTest
    @DisplayName("Square root for a few perfect squares")
    @CsvSource({
        "0, 0.0",
        "1, 1.0",
        "4, 2.0",
        "9, 3.0",
        "16, 4.0",
        "25, 5.0"
    })
    void testSquareRoot(double input, double expected) {
        assertEquals(expected, calc.squareRoot(input), 0.001);
    }
    
    @ParameterizedTest
    @DisplayName("Addition using a method to supply test data")
    @MethodSource("provideNumbersForAddition")
    void testAdditionWithMethodSource(int a, int b, int expected) {
        assertEquals(expected, calc.add(a, b));
    }
    
    static Stream<Arguments> provideNumbersForAddition() {
        return Stream.of(
            Arguments.of(1, 1, 2),
            Arguments.of(2, 3, 5),
            Arguments.of(10, 20, 30),
            Arguments.of(-5, 5, 0),
            Arguments.of(0, 0, 0)
        );
    }
    
    @ParameterizedTest
    @DisplayName("Subtraction using a method to supply test data")
    @MethodSource("provideNumbersForSubtraction")
    void testSubtractionWithMethodSource(int a, int b, int expected) {
        assertEquals(expected, calc.subtract(a, b));
    }
    
    static Stream<Arguments> provideNumbersForSubtraction() {
        return Stream.of(
            Arguments.of(5, 3, 2),
            Arguments.of(10, 5, 5),
            Arguments.of(0, 0, 0),
            Arguments.of(-5, -3, -2),
            Arguments.of(3, 5, -2)
        );
    }
    
    @ParameterizedTest
    @DisplayName("Multiplying different number pairs")
    @CsvSource({
        "2, 3, 6",
        "5, 5, 25",
        "10, 0, 0",
        "-2, 3, -6",
        "-2, -3, 6"
    })
    void testMultiplication(int a, int b, int expected) {
        assertEquals(expected, calc.multiply(a, b));
    }
    
    @ParameterizedTest(name = "{index} => isEven({0}) = {1}")
    @DisplayName("Checking if numbers are even or odd")
    @CsvSource({
        "2, true",
        "3, false",
        "4, true",
        "5, false",
        "0, true",
        "-2, true",
        "-3, false"
    })
    void testIsEven(int number, boolean expected) {
        assertEquals(expected, calc.isEven(number));
    }
}
