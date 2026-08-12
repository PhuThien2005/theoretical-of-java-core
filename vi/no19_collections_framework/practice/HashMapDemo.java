package vi.no19_collections_framework.practice;

import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {
    public static void main(String[] args) {
        Map<String, String> capitalMap = new HashMap<>();
        capitalMap.put("USA", "Washington D.C.");
        capitalMap.put("Japan", "Tokyo");
        capitalMap.put(null, "No Capital"); // Allows one null key
        
        System.out.println(capitalMap.toString());
        System.out.println("Japan Capital: " + capitalMap.get("Japan")); // Tokyo
    }
}