package dynamicproxylogger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class DynamicProxyLoggerSolution {

    public interface UserService {
        String getRole(String username);
        void updateLastLogin(String username);
    }

    public static class UserServiceImpl implements UserService {
        @Override
        public String getRole(String username) {
            if (username == null) {
                throw new IllegalArgumentException("Username cannot be null");
            }
            if ("admin".equals(username)) return "ADMIN";
            return "USER";
        }

        @Override
        public void updateLastLogin(String username) {
            if (username == null) {
                throw new IllegalArgumentException("Username cannot be null");
            }
            // Simulate operation
        }
    }

    public static class LoggingInvocationHandler implements InvocationHandler {
        
        private final Object target;
        private final StringBuilder logDestination;

        public LoggingInvocationHandler(Object target, StringBuilder logDestination) {
            if (target == null) {
                throw new IllegalArgumentException("Target cannot be null");
            }
            if (logDestination == null) {
                throw new IllegalArgumentException("Log destination cannot be null");
            }
            this.target = target;
            this.logDestination = logDestination;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String argsStr = args == null ? "[]" : Arrays.toString(args);
            logDestination.append("[START] Method: ").append(method.getName())
                          .append(" called with args: ").append(argsStr).append("\n");

            long start = System.nanoTime();
            Object result;
            try {
                result = method.invoke(target, args);
            } catch (java.lang.reflect.InvocationTargetException e) {
                // Unwrap core business exceptions from the InvocationTargetException
                throw e.getCause();
            }
            long end = System.nanoTime();
            double durationMs = (end - start) / 1_000_000.0;

            logDestination.append("[END] Method: ").append(method.getName())
                          .append(" completed\n");

            return result;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Class<T> interfaceType, T target, StringBuilder logDestination) {
        if (interfaceType == null || target == null || logDestination == null) {
            throw new IllegalArgumentException("Parameters cannot be null");
        }
        return (T) Proxy.newProxyInstance(
            interfaceType.getClassLoader(),
            new Class<?>[]{interfaceType},
            new LoggingInvocationHandler(target, logDestination)
        );
    }
}
