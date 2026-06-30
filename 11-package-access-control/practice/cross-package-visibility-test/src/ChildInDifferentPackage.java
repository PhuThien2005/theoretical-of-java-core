package beta;

import alpha.ParentClass;

/**
 * A subclass in a different package (beta) extending ParentClass in package (alpha).
 */
public class ChildInDifferentPackage extends ParentClass {

    /**
     * Retrieves visible fields from the parent class.
     * Since this is a subclass in a different package:
     * - publicField is visible.
     * - protectedField is visible (due to inheritance).
     * - defaultField is NOT visible.
     * - privateField is NOT visible.
     *
     * @return a hyphen-separated string of the visible fields
     */
    public String getVisibleFields() {
        // TODO: Concatenate publicField and protectedField separated by a hyphen ("-")
        return null;
    }
}
