package vi.no12_exception_handling.theory;

public class ThrowableHierarchyDemo {
    // 1. Error: Stack Overflow do đệ quy vô hạn (thảm họa cấp JVM)
    public static void causeStackOverflow() {
        causeStackOverflow();
    }

    // 2. Exception: Chia cho 0 (vấn đề cấp chương trình có thể phục hồi)
    public static void causeException() {
        int result = 10 / 0;
    }

    public static void main(String[] args) {
        // Phục hồi từ Exception
        try {
            causeException();
        } catch (ArithmeticException e) {
            System.out.println("Recovered from exception: " + e.getMessage() + '\n' + e);
        }

        System.out.println("code after exception");

        try {
            throw new Exception("some thing went wrong");
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        // Gặp phải Error (Tránh bắt Error trong code production!)
        try {
            causeStackOverflow();
        } catch (StackOverflowError err) {
            System.err.println("Caught StackOverflowError (Highly discouraged to catch Errors): " + err);
        }
    }
}