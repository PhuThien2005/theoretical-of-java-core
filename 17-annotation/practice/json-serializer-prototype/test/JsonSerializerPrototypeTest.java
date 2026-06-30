/**
 * Test runner for JsonSerializerPrototype.
 */
public class JsonSerializerPrototypeTest {

    public static void main(String[] args) {
        try {
            testSerialization();
            testNonSerializableClass();
            System.out.println("✅ All tests passed successfully!");
            System.exit(0);
        } catch (Throwable t) {
            System.err.println("❌ Test Suite Failed!");
            t.printStackTrace();
            System.exit(1);
        }
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("Assertion failed: " + message);
        }
    }

    private static void testSerialization() throws Exception {
        Student s = new Student("Bob", 22, true);
        String json = JsonSerializerPrototype.serialize(s);

        // Verify JSON string contents order-independently
        assertTrue(json.startsWith("{") && json.endsWith("}"), "JSON must start with { and end with }");
        assertTrue(json.contains("\"name\":\"Bob\""), "JSON should contain name mapping");
        assertTrue(json.contains("\"student_age\":22"), "JSON should contain custom field value mapping");
        assertTrue(json.contains("\"enrolled\":true"), "JSON should contain boolean mapping");
        
        // Assert field counts by checking commas
        int commas = 0;
        for (char c : json.toCharArray()) {
            if (c == ',') commas++;
        }
        assertTrue(commas == 2, "JSON must contain exactly 3 fields (2 commas)");
    }

    private static void testNonSerializableClass() {
        NonSerializable obj = new NonSerializable("test");
        try {
            JsonSerializerPrototype.serialize(obj);
            throw new AssertionError("Serializing a class without @JsonSerializable must throw IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Expected
        } catch (Exception e) {
            throw new AssertionError("Expected IllegalArgumentException but got: " + e.getClass().getSimpleName());
        }
    }

    // Test classes
    @JsonSerializable
    static class Student {
        @JsonField
        private String name;

        @JsonField("student_age")
        private int age;

        @JsonField
        private boolean enrolled;

        // Field NOT annotated with @JsonField - should be ignored during serialization
        private String internalNotes = "secret";

        public Student(String name, int age, boolean enrolled) {
            this.name = name;
            this.age = age;
            this.enrolled = enrolled;
        }
    }

    static class NonSerializable {
        @JsonField
        private String name;

        public NonSerializable(String name) {
            this.name = name;
        }
    }
}
