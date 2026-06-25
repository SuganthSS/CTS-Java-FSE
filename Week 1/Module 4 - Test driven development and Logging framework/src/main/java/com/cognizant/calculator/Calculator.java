package com.cognizant.calculator;

public class Calculator {
    
    public int add(int x, int y) {
        return x + y;
    }
    
    public int subtract(int x, int y) {
        return x - y;
    }
    
    public int multiply(int x, int y) {
        return x * y;
    }
    
    public int divide(int numeratorVal, int divisorVal) {
        if (divisorVal == 0) {
            throw new ArithmeticException("Hey, you can't divide by zero");
        }
        return numeratorVal / divisorVal;
    }
    
    public double squareRoot(double value) {
        if (value < 0) {
            throw new IllegalArgumentException("Oops, negative numbers don't have a real square root");
        }
        return Math.sqrt(value);
    }
    
    public boolean isEven(int val) {
        return val % 2 == 0;
    }
    
    public long factorial(int num) {
        if (num < 0) {
            throw new IllegalArgumentException("Sorry, factorial doesn't work with negative numbers");
        }
        if (num == 0 || num == 1) {
            return 1;
        }
        long fact = 1;
        for (int idx = 2; idx <= num; idx++) {
            fact *= idx;
        }
        return fact;
    }
}
