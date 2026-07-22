package no32_classloader.practice.class_loader_hierarchy_printer;

import java.util.List;

/**
 * Starter template for listing a class's parent classloader hierarchy.
 */
public class ClassLoaderHierarchyPrinter {

    /**
     * Traverses the parent hierarchy of the classloader that loaded the given class.
     * 
     * Requirements:
     * - Retrieve the class loader of the class.
     * - Loop: add the class loader's class name (cl.getClass().getName()) to the hierarchy list.
     * - Move to the parent (cl.getParent()).
     * - When cl is null (reached Bootstrap ClassLoader), add "Bootstrap ClassLoader" and stop.
     *
     * @param clazz the class to trace
     * @return the list of classloader names from leaf to root
     */
    public static List<String> getHierarchy(Class<?> clazz) {
        // TODO: Traverse parent classloaders and compile hierarchy list
        return null;
    }
}
