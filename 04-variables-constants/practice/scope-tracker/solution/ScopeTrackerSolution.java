/**
 * Reference solution for ScopeTrackerSolution.
 */
public class ScopeTrackerSolution {

    // Shared across all instances of the class. Initialized when the class is loaded.
    public static int staticCount = 0;

    // Unique to each object instance. Initialized when the object is created.
    public int instanceCount = 0;

    /**
     * Increments both the static (class) counter and the instance (object) counter.
     */
    public void increment() {
        staticCount++;
        this.instanceCount++;
    }

    /**
     * Demonstrates shadowing: parameter `instanceCount` hides the instance field.
     * We use `this.instanceCount` to explicitly access the instance field.
     */
    public int shadowDemo(int instanceCount) {
        // `instanceCount` refers to the method parameter (shadow variable)
        // `this.instanceCount` refers to the instance field
        return instanceCount + this.instanceCount;
    }

    /**
     * Demonstrates block scope: variables declared inside loop blocks {} are
     * only visible inside that block.
     */
    public int blockScopeDemo(int limit) {
        int totalSum = 0;
        for (int i = 1; i <= limit; i++) {
            // block-scope variable `temp` is created at the start of each iteration
            // and goes out of scope at the end of the iteration.
            int temp = totalSum + i;
            totalSum = temp;
        }
        return totalSum;
    }
}
