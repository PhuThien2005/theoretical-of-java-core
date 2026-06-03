public class StringPractice {
    public static void main(String[] args) {
        String text = "  Java Core  ";
        String cleaned = text.trim().toLowerCase();

        System.out.println(cleaned);
        System.out.println("Length: " + cleaned.length());
        System.out.println("Contains java: " + cleaned.contains("java"));
    }
}
