import java.util.Arrays;

public class ImmutableString {
    public static void main(String... agrs) {
        // String a = new String("Java");
        // a = a.intern();
        // String b = "Javac".intern();
        // String c = "Javac";
        // System.out.println(c.indexOf("a"));
        // System.out.println(b.contains("ac"));
        // // System.out.println(c.substring(4, 5));
        // System.out.println(b == c);
        // System.out.println("TEST".equalsIgnoreCase("Test"));
        // System.out.println("bd".compareTo("bd"));
        // String tmp = " java ";
        // tmp = tmp.trim();
        // System.out.println(tmp);
        // System.out.println(a.hashCode());
        // String b = a.concat("core");
        // System.out.println("a = " + a);
        // System.out.println("ha = " + a.hashCode());
        // System.out.println("b = " + b);
        // System.out.println("hb = " + b.hashCode());

        // // String containing Unicode Em Space (\u2003)
        // String input = "\u2003Java Core\u2003";

        // System.out.println(input + "Original length: " + input.length()); // Output:
        // 11
        // System.out.println(input.trim() + "trim() length: " + input.trim().length());
        // // Output: 11 (ignored!)
        // System.out.println(input.strip() + "strip() length: " +
        // input.strip().length()); // Output: 9 (removed!)

        // String a = "Anh oi Anh a";
        // String b = a.replace("Anh", "em");
        // System.out.println(b);
        // StringBuilder ab = new StringBuilder(b);
        // b = ab.reverse().toString();
        // System.out.println(b);
        // StringBuffer bc = new StringBuffer();
        // ImmutableString g = new ImmutableString();
        // System.out.println(g.toString());
        String[] arr = new String[] { "anh", "oi", "anh", "a" };
        System.out.println(Arrays.toString(arr));
        System.out.println(new int[] { 1, 2, 3 }.toString());
    }
}
