public class ArrayPractice {
    public static void main(String[] args) {
        int[] numbers = {4, 7, 1, 9, 2};

        int sum = 0;
        int max = numbers[0];

        for (int number : numbers) {
            sum += number;
            if (number > max) {
                max = number;
            }
        }

        System.out.println("Sum: " + sum);
        System.out.println("Max: " + max);
    }
}
