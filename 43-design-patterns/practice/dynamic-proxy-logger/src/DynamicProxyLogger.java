package dynamicproxylogger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

public class DynamicProxyLogger {

    public interface UserService {
        String getRole(String username);
        void updateLastLogin(String username);
    }

    public static class UserServiceImpl implements UserService {
        @Override
        public String getRole(String username) {
            if ("admin".equals(username)) return "ADMIN";
            return "USER";
        }

        @Override
        public void updateLastLogin(String username) {
            // Simulate operation
        }
    }

    public static class LoggingInvocationHandler implements InvocationHandler {
        
        // TODO: Declare private fields for the target object and a StringBuilder for logs.

        public LoggingInvocationHandler(Object target, StringBuilder logDestination) {
            // TODO: Initialize fields.
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // TODO: Log start: "[START] Method: " + method.getName() + " called with args: " + Arrays.toString(args)
            // TODO: Measure start nanoTime.
            // TODO: Invoke method.invoke(target, args). Wrap inside a try-catch to catch InvocationTargetException,
            //       and rethrow the cause: throw e.getCause().
            // TODO: Measure end nanoTime, compute duration in ms.
            // TODO: Log end: "[END] Method: " + method.getName() + " completed" (note: format details in test).
            // Return result.
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Class<T> interfaceType, T target, StringBuilder logDestination) {
        // TODO: Use Proxy.newProxyInstance to create and return the dynamic proxy.
        return null;
    }
}
