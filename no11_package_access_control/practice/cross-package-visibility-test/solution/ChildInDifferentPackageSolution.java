package beta;

import alpha.ParentClassSolution;

/**
 * Reference solution for ChildInDifferentPackageSolution.
 */
public class ChildInDifferentPackageSolution extends ParentClassSolution {

    public String getVisibleFields() {
        // publicField is accessible globally
        // protectedField is accessible to subclasses in different packages
        return publicField + "-" + protectedField;
    }
}
