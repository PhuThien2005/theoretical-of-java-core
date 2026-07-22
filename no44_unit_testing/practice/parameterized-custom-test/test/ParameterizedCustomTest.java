package parameterizedcustomtest;

import java.lang.reflect.Method;
import parameterizedcustomtest.EmailValidator.*;

public class ParameterizedCustomTest {

    @MyParameterizedTest
    @MyValueSource(strings = {
        "john.doe@example.com",
        "alice@domain.co",
        "user_name@service.org",
        "some.body@nested.sub.net"
    })
    public void testValidEmails(String email) {
        if (!EmailValidator.isValidEmail(email)) {
            throw new AssertionError("Email should be valid: " + email);
        }
    }

    @MyParameterizedTest
    @MyValueSource(strings = {
        "plainaddress",
        "@no-local.com",
        "double@@gmail.com",
        "name@.com",
        "name@domain.",
        ".name@domain.com",
        "name@domain.com.",
        "   ",
        ""
    })
    public void testInvalidEmails(String email) {
        if (EmailValidator.isValidEmail(email)) {
            throw new AssertionError("Email should be invalid: " + email);
        }
    }

    public static void main(String[] args) {
        int passedRuns = 0;
        int failedRuns = 0;

        try {
            Class<?> testClass = ParameterizedCustomTest.class;
            Object testInstance = testClass.getDeclaredConstructor().newInstance();

            for (Method m : testClass.getDeclaredMethods()) {
                if (m.isAnnotationPresent(MyParameterizedTest.class)) {
                    if (!m.isAnnotationPresent(MyValueSource.class)) {
                        throw new AssertionError("Method " + m.getName() + " is missing @MyValueSource");
                    }

                    MyValueSource source = m.getAnnotation(MyValueSource.class);
                    String[] inputs = source.strings();

                    for (String input : inputs) {
                        try {
                            m.invoke(testInstance, input);
                            passedRuns++;
                        } catch (Throwable t) {
                            System.err.println("❌ Parameterized test failed for input: [" + input + "]");
                            if (t.getCause() != null) {
                                t.getCause().printStackTrace();
                            } else {
                                t.printStackTrace();
                            }
                            failedRuns++;
                        }
                    }
                }
            }

            if (failedRuns > 0) {
                System.err.println("❌ " + failedRuns + " runs failed!");
                System.exit(1);
            } else {
                System.out.println("✅ All " + passedRuns + " parameterized runs passed successfully!");
                System.exit(0);
            }

        } catch (Throwable t) {
            System.err.println("❌ Runner Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }
}
