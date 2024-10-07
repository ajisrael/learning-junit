# Learning Junit Testing

## Course

This repository follows the course taught on [udemy](https://www.udemy.com/share/106yPY3@NCV-swCvDAd_lj7XoibM31mHnJfE6C9N3At-HdtpDA09JBahVWFW8AKOUm6NasPrSw==/)

## JUnit Notes

### F.I.R.S.T. Principle

1. Fast
2. Independent
3. Reliable
4. Self-validating
5. Thorough & Timely - Covers edge cases

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

### Unit Test Naming Convention

Below is a common pattern for naming tests:

```
test<System Under Test>_<Condition or State Change>_<Expected Result>
```

So the integerDivision() test that checks if 4 / 2 = 2 would be:

```java
testIntegerDivision_WhenFourIsDividedByTwo_ShouldReturnTwo() {}
```

### Test Method Code Structure: AAA

A good practice is to arrange your test methods like the following:

```java
void test() {
    // Arrange

    // Act

    // Assert
}
```

Another common way this pattern is described is with Given, When, Then:

```java
void test() {
    // Given

    // When

    // Then
}
```

### Lifecycle Annotations

The following are the 4 lifecycle annotations provided by the JUnit Jupiter API

```java
@BeforeAll  // used for setup() and must be static
@BeforeEach // used for beforeEach() instance of a test method
@AfterEach  // used for afterEach() instance of a test method
@AfterAll   // used for cleanup() and must be static
```

### @Disabled Annotation:

Allows you to disable a unit test with a description and still keep it in the report:

```java
@Disabled("TODO: Still needs work")
```

### @ParameterizedTest

This annotation is used to indicate that the test accepts parameters.

#### @MethodSource

An additional annotation `@MethodSource` is used to specify which method will return the list of arguments to be passed as parameters.
The method referenced by `@MethodSource` must be `static`.

If the name of the method referenced by `@MethodSource` is the same as a test method name, we do not need to specify it in the annotation.

#### @CSVSource

A comma separated list of values to be passed as arguments to the method.

`''` will denote an empty string and `` nothing indicates a null value

```java
@CSVSource({
    "apple,orange", // passes "apple" and "orange" as strings
    "apple,''",     // passes "apple" and "" as a string and empty string
    "apple,"        // passes "apple" and null as string and null
})
```

## Other

### Set Test Resources Root

First create a `resources` package inside your `test` folder.
Then right click at the base of the project in IntelliJ and select `Open Module Settings`. You should then see an screen like the following:

![Module Settings Window](./images/test-resources-root.png)

Select the `resources` package you created and then click the Test Resources button at the top and click apply.

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

