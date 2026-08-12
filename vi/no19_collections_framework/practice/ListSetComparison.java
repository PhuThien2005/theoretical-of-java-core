package vi.no19_collections_framework.practice;

import java.util.*;

public class ListSetComparison {
    public static void main(String[] args) {
        // 1. Insertion order comparison
        Set<String> hashSet = new HashSet<>();
        Set<String> linkedHashSet = new LinkedHashSet<>();

        List<String> fruits = List.of("Orange", "Apple", "Banana");
        hashSet.addAll(fruits);
        linkedHashSet.addAll(fruits);

        System.out.println("HashSet (arbitrary order): " + hashSet);
        System.out.println("LinkedHashSet (insertion order): " + linkedHashSet);

        // 2. Performance Comparison (ArrayList vs LinkedList lookup)
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        int count = 100_000;

        for (int i = 0; i < count; i++) {
            arrayList.add(i);
            linkedList.add(i);
        }

        long start = System.nanoTime();
        int val1 = arrayList.get(count / 2);
        long arrayListTime = System.nanoTime() - start;

        start = System.nanoTime();
        int val2 = linkedList.get(count / 2);
        long linkedListTime = System.nanoTime() - start;

        System.out.println("ArrayList mid-lookup time: " + arrayListTime + " ns");
        System.out.println("LinkedList mid-lookup time: " + linkedListTime + " ns");
    }
}
