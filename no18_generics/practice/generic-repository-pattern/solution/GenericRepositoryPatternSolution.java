package no18_generics.practice.generic_repository_pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Reference solution for GenericRepositoryPatternSolution.
 * 
 * Generics allow:
 * - Reusable type-safe logic: we don't need separate repositories for User, Product, Order.
 * - Compilation-time type checks: prevents mixing different types of objects at runtime.
 */
public class GenericRepositoryPatternSolution {
    // Wrapper class
}

class GenericRepository<T, ID> {
    private final Map<ID, T> storage = new HashMap<>();

    public void save(ID id, T entity) {
        if (id == null || entity == null) {
            throw new IllegalArgumentException("ID and entity cannot be null");
        }
        storage.put(id, entity);
    }

    public T findById(ID id) {
        if (id == null) {
            return null;
        }
        return storage.get(id);
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public void deleteById(ID id) {
        if (id != null) {
            storage.remove(id);
        }
    }

    public boolean existsById(ID id) {
        if (id == null) {
            return false;
        }
        return storage.containsKey(id);
    }

    public int size() {
        return storage.size();
    }
}
