package no14_object_class.practice.consistent_hash_key;

/**
 * A class designed to represent a composite key in a hash collection.
 */
public class ConsistentHashKey {

    private final int id;
    private final String category;

    public ConsistentHashKey(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    /**
     * Overrides equals to compare this object with another.
     * 
     * Requirements:
     * - Reflexive: x.equals(x) is true.
     * - Symmetric: x.equals(y) is true iff y.equals(x) is true.
     * - Transitive: x.equals(y) and y.equals(z) implies x.equals(z).
     * - Handles null values safely.
     * - Compares exact class types (getClass()) or uses instanceof correctly.
     */
    @Override
    public boolean equals(Object obj) {
        // TODO: Implement consistent equals check
        return false;
    }

    /**
     * Overrides hashCode to return a consistent integer hash representing this object.
     * 
     * Requirement:
     * - If x.equals(y), then x.hashCode() must equal y.hashCode().
     */
    @Override
    public int hashCode() {
        // TODO: Implement consistent hashCode check
        return 0;
    }
}
