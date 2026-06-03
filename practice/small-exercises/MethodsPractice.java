public class MethodsPractice {
    public static void main(String[] args) {
        System.out.println(add(3, 4));
        System.out.println(isEven(10));
    }

    static int add(int a, int b) {
        return a + b;
    }

    static boolean isEven(int number) {
        return number % 2 == 0;
    }
}
