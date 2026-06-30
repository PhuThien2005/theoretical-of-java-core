class Student {
    String name;
    static String schoolName;
}

public class Compare {
    public static final int BASE = 100; // Hằng số thời gian biên dịch

    public static void main(String[] args) {
        // Trình biên dịch tính toán BASE + 50 thành 150 khi biên dịch
        int val1 = BASE % 7 + 50; // Biên dịch trực tiếp thành: int val1 = 150;

        System.out.println(val1); // 150
    }
}