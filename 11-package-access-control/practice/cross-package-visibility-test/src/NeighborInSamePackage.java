package alpha;

/**
 * A non-subclass in the same package (alpha) as ParentClass.
 */
public class NeighborInSamePackage {

    /**
     * Retrieves visible fields from a ParentClass instance.
     * Since this class is in the same package:
     * - publicField is visible.
     * - protectedField is visible.
     * - defaultField is visible.
     * - privateField is NOT visible.
     *
     * @return a hyphen-separated string of the visible fields
     */
    public String getVisibleFields(ParentClass parent) {
        // TODO: Concatenate parent.publicField, parent.protectedField, and parent.defaultField
        // separated by hyphens ("-")
        return null;
    }
}
