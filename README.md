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

#### @CSVFileSource

Allows you to specify a file with parameters to run multiple iterations of a test method.
The path is based at the root of the test resources directory.

```java
@CSVFileSource("/paramaters.csv")
```

#### @ValueSource

Can only be used for testing a method with a single parameter. Allows you to pass a list of arguments into your test method.

```java
@ParameterizedTest
@ValueSource(strings = {"John", "Kate", "Alice"})
void valueSourceDemonstration(String firstName) {
    System.out.println(firstName);
    assertNotNull(firstName);
}
```

### @RepeatedTest

Allows you to test a method multiple times. JUnit also provides some objects that you can inject into the method that can tell you what 
iteration you're on and some metadata for the current test.

```java
@DisplayName("Test division by zero")
@RepeatedTest(value=3, name="{displayName}: Repetition {currentRepetition} of " +
    "{totalRepetitions}")
void testIntegerDivision_WhenDividendIsDividedByZero_ShouldThrowArithmeticException(
        RepetitionInfo repetitionInfo,
        TestInfo testInfo
) {
    System.out.println("Running " + testInfo.getTestMethod().get().getName());
    System.out.println("Repetition #" + repetitionInfo.getCurrentRepetition() +
            " of " + repetitionInfo.getTotalRepetitions());

    // Rest of test ...

}
```

### Run Tests in a Random Order

Allows you to run test in a random order:

```java
@TestMethodOrder(MethodOrderer.Random.class)
public class MethodOrderedRandomlyTest {
}
```

### Run Tests by Name

Allows you to run tests in order by name:

```java
@TestMethodOrder(MethodOrderer.MethodName.class)
public class MethodOrderedByNameTest {
}
```

### Run Tests by Index

Allows you to run tests in order by index:

```java
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MethodOrderedByOrderIndexTest {

    @Order(1)
    @Test
    void testB() {
        System.out.println("Running test B");
    }

    @Order(2)
    @Test
    void testA() {
        System.out.println("Running test A");
    }
}
```

### Run Test Classes in Order by Index

You can configure ordering similarly for classes using a `junit-platform.properties` file.

```properties
junit.jupiter.testclass.order.default=org.junit.jupiter.api.ClassOrderer$OrderAnnotation
```

### Changing Test Instance Lifecycle

By default when testing a new instance of the test class is instantiated for each test method. This keeps tests isolated.
We can change this to allow the sharing of state within the test class with the following annotation:

```java
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
```

When you change the Test Instance Lifecycle to per class, then methods annotated with `@BeforeAll` and `@AfterAll` are no longer `static`.

## Mockito

Example implementation can be found in the [UserServiceTest](./UserService/src/test/java/com/example/service/UserServiceTest.java) class.

### Setup

Add maven or gradle dependency or download jars directly from maven repository after searching for `mockito junit jupiter`.

### Adding Mockito to a Test Class

To add mockito to a test class, we need to annotate the class with the following annotation:

```java
@ExtendWith(MockitoExtension.class)
```

### Mocking a Dependency

To mock an object that is a dependency of the class we are trying to test we need to add the `@Mock` annotation:

```java
@Mock
UsersRepository usersRepository;
```

### Injecting a Mock

Mockito provides us with the ability to inject mocks into our class that we are testing with the `@InjectMocks` annotation:

```java
@InjectMocks
UserServiceImpl userService;
```

### Stubbing a method

You can stub methods that return an object or value with the `when().thenReturn()` pattern.

```java
when(usersRepository.save(any(User.class))).thenReturn(true);
```

You can stub methods to throw an exception with the `when().thenThrow()` pattern.

```java
when(usersRepository.save(any(User.class))).thenThrow(RuntimeException.class);
```

You can stub void methods to do nothing (happy path) with the `doNothing().when()` pattern.

```java
doNothing().when(emailVerificationService).scheduleEmailConfirmation(any(User.class));
```

You can stub void methods to throw an exception with the `doThrow().when()` pattern.

```java
doThrow(EmailNotificationServiceException.class)
        .when(emailVerificationService)
        .scheduleEmailConfirmation(any(User.class));
```

### Calling a real method

There may be times where you really do want to call the method of a class that you are mocking.
You can do this with the `doCallRealMethod().when()` pattern.

```java
doCallRealMethod().when(emailVerificationService)
        .scheduleEmailConfirmation(any(User.class));
```

NOTE:
This can only be done on a non abstract method.
AKA you cannot be mocking an interface, you need to be mocking an implementation of the interface.

## Testing With Spring

### Project Setup

All that is required is the `Spring Boot Starter Test` package that can be found [here](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test) but comes OOTB from a Spring Initializr project. THis package contains libraries like JUnit, Mockito, etc. Everything we need for testing in Spring.

If we are using `Spring Security` (link to mvn package [here](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-security)) then we will also need this test package [spring-security-test](https://mvnrepository.com/artifact/org.springframework.security/spring-security-test)

### @WebMvcTest

This annotation tells spring boot to only load into the application context beans for the web layer.

You can further limit what classes get loaded using the following property:

```java
@WebMvcTest(controllers = UsersController.class)
```

Here we are specifically only testing the `UserController` class, so we limit the application context to only this class.

### Disable Spring Security Filters for Test

To disable filters we need to use the following annotation:

```java
@AutoConfigureMockMvc(addFilters = false)
```

We can also modify the `@WebMvcTest` annotation instead:

```java
@WebMvcTest(controllers = UsersController.class,
excludeAutoConfiguration = {SecurityAutoConfiguration.class})
```

### @MockBean annotation

Creates a mock object for the class that implements the interface

```java
@MockBean
UsersService usersService;
```

If there are multiple classes that implement the interface then you will need to be specific about which one to mock.
To do this, you should set the config at the class level of all classes that it needs to mock and then autowire the interface that needs to be mocked.

```java
@MockBean({UsersServiceImpl.class})
public class UsersControllerWebLayerTest {

    @Autowired
    UsersService usersService;

    // Rest of test
}
```

This is very similar to the `@Mock` annotation from Mokito, but also adds the object to the spring application context.

### @SpringBootTest Annotation

This annotation will load the entire application context of the spring boot app. This should be used for integration testing.
By default `@SpringBootTest` will create a mocked web environment (meaning it won't actually load everything).

#### Defined Port

To use a real web server we need to update the properties of the annotation to look like the following:

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
```

This will use the defined port in the `application.properties` file inside the project's `resources` folder.

You can also overwrite the property of the server port inside the annotation:

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
properties = "server.port=8081")
```

If you needed to overwrite more than one property, your annotation will need to follow the following syntax:

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
properties = {"server.port=8081","other.property=someValue"})
```

#### Random Port (Best Practice)

In general it is best to have tests run on a random port as this prevents conflicts and allows multiple tests to run in parallel.
There is no need to configure ports for this in the annotation or configuration files.

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
```

Behind the scenes this will change the `server.port` property to be zero:

```properties
server.port=0
```

This configuration will tell spring boot to start on a random port number

### @TestPropertySource Annotation

This annotation is to be used as another way of specifying properties for the spring boot test application. It allows you to specify a completely different
properties file for any specific environment changes required for testing.

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "/application-test.properties")
```

As seen before in the `@SpringBootTest` we can also overwrite any of thes properties inside this annotation as well.

```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "/application-test.properties",
    properties = "server.port=8081")
```

This will take the highest priority.

### TestRestTemplate vs. RestTemplate

Both are http clients but `TestRestTemplate` is easier to use in testing and doesn't fully extend a `RestTemplate`.
This is why it is used in integration tests, easier authentication, as shown below:

```java
    @Autowired
    private TestRestTemplate testRestTemplate;
```

### JPA Testing

#### @DataJpaTest Annotation

This annotation allows us to test our data layer in isolation.
It will only load the application context with JPA related components.
By default each test method is treated as transactional and will rollback when complete.
Also an in memory database is used by default, so no additional configuration is required.

## Other

### Generating Coverage Test Report

To generate a coverage test report in IntelliJ, simply right click on the test and click run tests with coverage report or look for the Shield and play icon.

![Test Report Image](./images/test-report.png)

You can then export the results using the icon shown below:

![Export Test Report Icon](./images/export-test-report.png)

### Generating Test Report with Maven

You can also have a test report generated with maven using the `maven surefire report plugin` found [here](https://mvnrepository.com/artifact/org.apache.maven.plugins/maven-surefire-report-plugin)

```xml
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-report-plugin</artifactId>
                <version>3.0.0-M6</version>
                <executions>
                    <execution>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

This will allow the report to be generated if all unit tests pass. To allow report to be generated if a test fails we need to adjust the `maven-surefire-plugin` as well.

```xml
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0-M6</version>
                <configuration>
                    <testFailureIgnore>true</testFailureIgnore>
                </configuration>
            </plugin>
```

Now you will be able to generate a report when running the following command:

```bash
mvn clean test
```

The generated report can be found opened using the following command from the base of the project:

```bash
open ./target/site/surefire-report.html
```

To make the report a little nicer we can use the following command:

```bash
mvn site -DgenerateReport=false
```

### Generating Code Coverage Report with Maven

You can generate a code coverage report with maven using the [Jacoco Maven Plugin](https://mvnrepository.com/artifact/org.jacoco/jacoco-maven-plugin)

```xml
            <plugin>
                <groupId>org.jacoco</groupId>
                <artifactId>jacoco-maven-plugin</artifactId>
                <version>0.8.8</version>
                <executions>
                    <execution>
                        <id>prepare-agent</id>
                        <goals>
                            <goal>prepare-agent</goal>
                        </goals>
                    </execution>
                    <execution>
                        <id>report</id>
                        <phase>test</phase>
                        <goals>
                            <goal>report</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
```

Generate report with command:

```bash
mvn clean test
```

Open report:

```bash
open ./target/site/jacoco/index.html
```

### TDD Flow

The process for test driven development is as follows:

1. Write Test unti it doesn't compile

    1a. Write application code until test compiles

2. Run test (should be red)

3. Update application code and run test until it passes

4. Refactor tests and application code

5. Repeat till application is done

### Set Test Resources Root

First create a `resources` package inside your `test` folder.
Then right click at the base of the project in IntelliJ and select `Open Module Settings`. You should then see an screen like the following:

![Module Settings Window](./images/test-resources-menu.png)

Select the `resources` package you created and then click the Test Resources button at the top and click apply.

### Create JUnit Properties

First make sure you've created and configured your `resources` package.
Then right click the package and select `Resource Bundle` as shown below:

![Select Resource Bundle](./images/select-resources-bundle.png)

Then enter 'junit-platform' as the resource bundle base name:
![Fill Out Resource Bundle](./images/complete-resource-bundle-form.png)

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

### @Value Annotation

This allows you to inject the value of a configuration property to a variable in a class inside a spring boot app.

```java
    @Value("${server.port}")
    private int serverPort;
```

### Creating JSON Formatted Strings

There are two ways to create a JSON formatted string:

A raw string:

```java
String createUserJson = "{\n" +
        "   \"firstName\":\"Sergey\",\n" +
        "   \"lastName\":\"Kargopolov\",\n" +
        "   \"email\":\"test@test.com\",\n" +
        "   \"password\":\"12345678\",\n" +
        "   \"repeatPassword\":\"12345678\",\n" +
        "}";
```

Or with `JSONObject`:

```java
JSONObject userDetailsRequestJson = new JSONObject();
userDetailsRequestJson.put("firstName", "Sergey");
userDetailsRequestJson.put("lastName", "Kargopolov");
userDetailsRequestJson.put("email", "test@test.com");
userDetailsRequestJson.put("password", "12345678");
userDetailsRequestJson.put("repeatPassword", "12345678");
```

### Setting HTTP Headers

We can instantiate an HTTP Header object with the following:

```java
HttpHeaders headers = new HttpHeaders();
```

Then there are two ways to set values for headers:

You can set any key value pair for a header like this:

```java
headers.set("Accept", "application/json");
```

Or if it is a commonly used header (like `Accept`),
then there is most likely a specifc method for setting that header:

```java
headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
```

Note: For `Accept` and `ContentType` we have access to the `MediaType` constants.

### Adding Headers (and Body) to a request with HttpEntity

The `HttpEntity` class has two constructors.

```java
HttpEntity<String> request = new HttpEntity<>(body, headers);
HttpEntity<String> request = new HttpEntity<>(headers);
```

So when there is a body present (ex, POST request) you will need to use the first one,
and when there is not a body present (ex. GET request) you can use the second or set the
body to `null` in the first example.
