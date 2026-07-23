package vi.no13_memory_management;

/**
 * PassByValueEx
 */
public class PassByValueEx {

    public static void main(String[] agrs) {
        int age = 15;
        Customer b = new Customer();
        modifyAge(age);
        modifyAge(b);
        System.out.println(age);
        System.out.println(b.age);
    }

    private static void modifyAge(Customer c) {
        c.age = 20;
    }

    private static void modifyAge(int age) {
        age = 20;
    }
}

class Customer {
    int age = 15;
}