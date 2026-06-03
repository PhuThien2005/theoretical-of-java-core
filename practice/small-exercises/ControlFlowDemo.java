public class ControlFlowDemo {
    public static void main(String[] args) {
        int score = 8;

        if (score >= 8) {
            System.out.println("Good");
        } else if (score >= 5) {
            System.out.println("Pass");
        } else {
            System.out.println("Fail");
        }

        for (int i = 1; i <= 5; i++) {
            System.out.println("i = " + i);
        }
    }
}
