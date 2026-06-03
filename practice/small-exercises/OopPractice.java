public class OopPractice {
    public static void main(String[] args) {
        Student student = new Student("An", 8.5);
        student.printInfo();
    }
}

class Student {
    private String name;
    private double score;

    Student(String name, double score) {
        this.name = name;
        this.score = score;
    }

    void printInfo() {
        System.out.println(name + " - " + score);
    }
}
