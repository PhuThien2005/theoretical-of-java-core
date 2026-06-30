import java.util.ArrayList;
import java.util.List;

/**
 * Reference solution for ClassLoaderHierarchyPrinterSolution.
 * 
 * Parent Delegation Tree:
 * - A class loader hierarchy starts at a specific ClassLoader.
 * - Calling `.getParent()` traverses the tree upwards.
 * - The parent of the System/Application ClassLoader is the Platform ClassLoader.
 * - The parent of the Platform ClassLoader is the Bootstrap ClassLoader, represented as `null` in Java API.
 */
public class ClassLoaderHierarchyPrinterSolution {

    public static List<String> getHierarchy(Class<?> clazz) {
        List<String> hierarchy = new ArrayList<>();
        if (clazz == null) {
            return hierarchy;
        }

        ClassLoader cl = clazz.getClassLoader();

        // Trace classloader parents
        while (cl != null) {
            hierarchy.add(cl.getClass().getName());
            cl = cl.getParent();
        }

        // The top-level classloader is the Bootstrap ClassLoader (represented as null)
        hierarchy.add("Bootstrap ClassLoader");

        return hierarchy;
    }
}
