package no11_package_access_control.practice.cross_package_visibility_test;

import alpha.ParentClass;
import alpha.NeighborInSamePackage;
import beta.ChildInDifferentPackage;

/**
 * A central visibility tester to call package-level operations.
 */
public class VisibilityTester {

    public static String getChildFields() {
        ChildInDifferentPackage child = new ChildInDifferentPackage();
        return child.getVisibleFields();
    }

    public static String getNeighborFields() {
        ParentClass parent = new ParentClass();
        NeighborInSamePackage neighbor = new NeighborInSamePackage();
        return neighbor.getVisibleFields(parent);
    }
}
