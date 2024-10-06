package com.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test Math operations in Calculator class")
class CalculatorTest {

    @DisplayName("Test 4/2 = 2")
    @Test
    void testIntegerDivision_WhenFourIsDividedByTwo_ShouldReturnTwo() {
        // Arrange
        Calculator calculator = new Calculator();
        int dividend = 4;
        int divisor = 2;
        int expectedResult = 2;

        // Act
        int actualResult = calculator.integerDivision(dividend, divisor);

        // Assert
        assertEquals(expectedResult, actualResult, "4/2 dot not produce 2");
    }

    @DisplayName("Test division by zero")
    @Test
    void testIntegerDivision_WhenDividendIsDividedByZero_ShouldThrowArithmeticException() {
        fail("Not implemented yet");
    }

    @DisplayName("Test 33-1 = 32")
    @Test
    void integerSubtraction() {
        Calculator calculator = new Calculator();
        int minuend = 33;
        int subtrahend = 1;
        int expectedResult = 32;

        int actualResult = calculator.integerSubtraction(33,1);
        assertEquals(expectedResult, actualResult,
                () -> minuend + " - " + subtrahend + " did not produce " + expectedResult);
    }
}