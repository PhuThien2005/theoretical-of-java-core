import alpha.ParentClassSolution;
import alpha.NeighborInSamePackageSolution;
import beta.ChildInDifferentPackageSolution;

/**
 * Reference solution for VisibilityTesterSolution.
 */
public class VisibilityTesterSolution {

    public static String getChildFields() {
        ChildInDifferentPackageSolution child = new ChildInDifferentPackageSolution();
        return child.getVisibleFields();
    }

    public static String getNeighborFields() {
        ParentClassSolution parent = new ParentClassSolution();
        NeighborInSamePackageSolution neighbor = new NeighborInSamePackageSolution();
        return neighbor.getVisibleFields(parent);
    }
}
