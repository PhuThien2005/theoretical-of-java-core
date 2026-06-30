package alpha;

/**
 * Reference solution for NeighborInSamePackageSolution.
 */
public class NeighborInSamePackageSolution {

    public String getVisibleFields(ParentClassSolution parent) {
        // Neighbors in the same package (alpha) can access:
        // - publicField (visible everywhere)
        // - protectedField (visible in same package)
        // - defaultField (visible in same package)
        return parent.publicField + "-" + parent.protectedField + "-" + parent.defaultField;
    }
}
