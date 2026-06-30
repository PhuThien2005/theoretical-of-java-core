import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Starter template for a generic in-memory repository pattern.
 */
public class GenericRepositoryPattern {
    // Wrapper class
}

/**
 * A generic repository storing entities of type T indexed by ID of type ID.
 */
class GenericRepository<T, ID> {
    private final Map<ID, T> storage = new HashMap<>();

    /**
     * Saves an entity under the given ID.
     */
    public void save(ID id, T entity) {
        // TODO: Store the entity in storage
    }

    /**
     * Finds an entity by its ID. Returns null if not found.
     */
    public T findById(ID id) {
        // TODO: Retrieve entity from storage
        return null;
    }

    /**
     * Returns a List containing all entities in the repository.
     */
    public List<T> findAll() {
        // TODO: Return a new List of all values in storage
        return null;
    }

    /**
     * Deletes the entity with the specified ID.
     */
    public void deleteById(ID id) {
        // TODO: Remove the entity from storage
    }

    /**
     * Checks if an entity with the specified ID exists.
     */
    public boolean existsById(ID id) {
        // TODO: Check if key exists in storage
        return false;
    }

    /**
     * Returns the size of the repository.
     */
    public int size() {
        return storage.size();
    }
}
