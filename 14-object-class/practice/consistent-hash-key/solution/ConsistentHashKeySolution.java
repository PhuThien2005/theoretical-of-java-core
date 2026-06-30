import java.util.Objects;

/**
 * Reference solution for ConsistentHashKeySolution.
 * 
 * Rules:
 * 1. If two objects are equal according to equals(Object), their hashCode() must be identical.
 * 2. It is not required that different objects produce different hash codes, but doing so improves
 *    hash table lookup efficiency.
 * 3. Use `Objects.equals()` to safely handle null check comparisons.
 */
public class ConsistentHashKeySolution {

    private final int id;
    private final String category;

    public ConsistentHashKeySolution(int id, String category) {
        this.id = id;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object obj) {
        // 1. Reference check
        if (this == obj) {
            return true;
        }
        // 2. Null and Type check
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        
        ConsistentHashKeySolution other = (ConsistentHashKeySolution) obj;
        // 3. Attribute comparisons
        return this.id == other.id && Objects.equals(this.category, other.category);
    }

    @Override
    public int hashCode() {
        // Computes a combined hash code using the key's attributes
        return Objects.hash(id, category);
    }
}
