# Learning Junit Testing

## Course

This repository follows the course taught on [udemy](https://www.udemy.com/share/106yPY3@NCV-swCvDAd_lj7XoibM31mHnJfE6C9N3At-HdtpDA09JBahVWFW8AKOUm6NasPrSw==/)

## Notes

### F.I.R.S.T. Principle

1. Fast
2. Independent
3. Reliable
4. Self-validating
5. Thorough & Timely - Covers edge cases

### Maven Surefire Plugin

It appears that the surefire plugin is part of the JUnit Aggregator dependency, but it is not the latest version of the plugin

Build command:

```bash
mvn package
```

Test command:

```bash
mvn test
```

Build without tests:

```bash
mvn package -Dmaven.test.skip=true
```

### Unit Test Messages

Will always be evaluated even if the test passes.
Best practice is to use a lambda function so they only get evaluated on a failure.
This helps with performance in larger applications.

Example:
```java
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
```

