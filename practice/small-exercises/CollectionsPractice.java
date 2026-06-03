import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionsPractice {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("An");
        names.add("Binh");
        names.add("Cuong");

        for (String name : names) {
            System.out.println(name);
        }

        Map<String, Integer> scores = new HashMap<>();
        scores.put("An", 8);
        scores.put("Binh", 9);

        System.out.println("Score of An: " + scores.get("An"));
    }
}
