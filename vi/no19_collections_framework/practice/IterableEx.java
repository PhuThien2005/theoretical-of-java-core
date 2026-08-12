package vi.no19_collections_framework.practice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * IterableEx
 */
public class IterableEx {
    public static void main(String[] args) {
        Iterable<Integer> iterable = new ArrayList<>();
        // iterable.add(10);
        Collection<Integer> collection = new ArrayList<>();
        collection.add(100);
        // Map<Integer, String> map = new Map<Integer, String>() {

        // };
        Deque<Integer> deque = new LinkedList<>();
        List<Integer> list = new ArrayList<>();

        list.addAll(List.of(11, 2, 3));
        list.add(12);
        list.remove(0);
        list.set(1, 1000);
        System.out.println(list.indexOf(1000));
        System.out.println(list.getFirst());
        System.out.println(list.toString());

        List<Integer> linkList = new LinkedList<>();
        linkList.addAll(List.of(11, 2, 3));
        linkList.add(19);
        linkList.add(20);
        linkList.remove(0);
        System.out.println(linkList.indexOf(1000));
        linkList.set(0, 2000);
        System.out.println(linkList.getFirst());
        System.out.println(linkList.toString());
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.addAll(List.of(100, 4, 3, 3));
        System.out.println(hashSet.toString());

        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.addAll(List.of(1111, 2, 3));
        linkedHashSet.add(1000);
        linkedHashSet.addFirst(1000);
        linkedHashSet.addLast(3000);
        System.out.println(linkedHashSet.contains(2000));
        System.out.println(linkedHashSet.toString());
        System.out.println(linkedHashSet.reversed().toString());

        List<String> names = List.of("An", "Bình", "Cường", "Dũng");

        // filter: Lọc các tên có độ dài > 2
        List<String> filtered = names.stream()
                .filter(name -> name.length() > 2)
                .toList(); // ["Bình", "Cường", "Dũng"]

        // map: Biến đổi chuỗi thành chữ hoa
        List<String> upper = names.stream()
                .map(String::toUpperCase)
                .toList(); // ["AN", "BÌNH", "CƯỜNG", "DŨNG"]

        // flatMap: Phẳng hóa List<List<String>> thành List<String>
        List<List<String>> nested = List.of(List.of("Java", "Spring"), List.of("Redis", "Kafka"));
        List<String> flat = nested.stream()
                .flatMap(List::stream)
                .toList(); // ["Java", "Spring", "Redis", "Kafka"]

        // peek: In log debug giữa đường ống mà không làm đổi dữ liệu
        List<String> peeked = names.stream()
                .peek(name1 -> System.out.println("Processing: " + name1))
                .filter(name -> name.startsWith("A"))
                .toList();
        List<String> peekdList = new ArrayList<>(peeked);

        peekdList.addAll(peeked);
        System.out.println(peekdList);
    }
}