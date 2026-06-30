/**
 * A class designed to demonstrate different variable scopes and lifetimes in
 * Java.
 */
public class ScopeTracker {

    // Static variable (class-level scope) - shared across all instances
    public static int staticCount = 0;

    // Instance variable (object-level scope) - unique to each object instance
    public int instanceCount = 0;

    /**
     * Increments both the static (class) counter and the instance (object) counter
     * by 1.
     */
    public void increment() {
        // TODO: Implement increment logic for both staticCount and instanceCount
        staticCount++;
        instanceCount++;
    }

    /**
     * Demonstrates local variable shadowing.
     * Inside this method, we want to shadow the instance variable `instanceCount`.
     * 
     * @param instanceCount a parameter that shadows the instance field of the same
     *                      name
     * @return the sum of the local parameter AND the object's instance variable
     *         field
     */
    public int shadowDemo(int instanceCount) {
        // TODO: Calculate the sum of the local parameter `instanceCount` and the
        // instance field `this.instanceCount`
        return instanceCount + this.instanceCount;
    }

    /**
     * Demonstrates block scope.
     * Computes the sum of numbers from 1 to the limit.
     * Inside the loop, declare a block variable that stores the intermediate sum,
     * demonstrating that it cannot be accessed outside the loop block.
     * 
     * @param limit the upper bound (inclusive)
     * @return the calculated sum
     */
    public int blockScopeDemo(int limit) {
        int totalSum = 0;
        for (int i = 1; i <= limit; i++) {
            // TODO: Declare a block-scope variable `temp` inside the loop,
            // assign it `totalSum + i`, and then update `totalSum` using `temp`.
            // Note: `temp` must only be visible inside this block!
            int temp = 0;
            temp = totalSum + i;
            totalSum = temp;
        }

        // Trying to access `temp` here would cause a compile-time error.

        return totalSum;
    }
}
