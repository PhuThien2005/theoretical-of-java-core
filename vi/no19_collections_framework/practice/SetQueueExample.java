package vi.no19_collections_framework.practice;

import java.util.*;

public class SetQueueExample {
    static class Person implements Comparable<Person> {
        String name;
        int age;

        Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public int compareTo(Person other) {
            return this.name.compareTo(other.name); // Sort by name
        }

        @Override
        public String toString() {
            return name + "(" + age + ")";
        }
    }

    public static void main(String[] args) {
        // 1. TreeSet using natural ordering (Comparable -> name)
        Set<Person> peopleByName = new TreeSet<>();
        peopleByName.add(new Person("Charlie", 30));
        peopleByName.add(new Person("Alice", 25));
        peopleByName.add(new Person("Bob", 35));
        System.out.println("Sorted by Name (Natural): " + peopleByName);

        // 2. TreeSet using custom Comparator (by age)
        Set<Person> peopleByAge = new TreeSet<>(Comparator.comparingInt(p -> p.age));
        peopleByAge.addAll(peopleByName);
        System.out.println("Sorted by Age (Comparator): " + peopleByAge);

        // 3. PriorityQueue max-heap behavior
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.offer(10);
        maxHeap.offer(30);
        maxHeap.offer(20);

        System.out.print("PriorityQueue polling: ");
        while (!maxHeap.isEmpty()) {
            System.out.print(maxHeap.poll() + " "); // 30 20 10
        }
        System.out.println();
    }
}