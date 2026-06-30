# Practice Exercises: ClassLoaders

This folder contains hands-on practice exercises to reinforce your understanding of Java ClassLoaders: custom classloading subclass implementations, defining classes dynamically from files, and auditing the classloader parent delegation hierarchy.

## Exercises

### 1. Custom Hot ClassLoader (`custom-hot-class-loader`)
Java class loaders cache classes. Once a class loader loads a class, it cannot be replaced or reloaded using the same class loader instance. To support **hot-reloading** of code updates dynamically, we must instantiate a new custom ClassLoader instance to load the modified byte stream.
- **Goal**: Implement `CustomHotClassLoader` extending `java.lang.ClassLoader` that reads raw `.class` file bytes from a target directory and defines them dynamically at runtime.

#### Directory Structure
- [CustomHotClassLoader.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/32-classloader/practice/custom-hot-class-loader/src/CustomHotClassLoader.java)
- [CustomHotClassLoaderTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/32-classloader/practice/custom-hot-class-loader/test/CustomHotClassLoaderTest.java)
- [CustomHotClassLoader.java (Solution)](file:///home/fhu_thjen/projects/learning-java/32-classloader/practice/custom-hot-class-loader/solution/CustomHotClassLoader.java)

---

### 2. ClassLoader Hierarchy Printer (`class-loader-hierarchy-printer`)
Java classloaders operate under the **Parent Delegation Model**. When requested to load a class, a classloader delegates to its parent before searching locally.
- **Goal**: Implement `ClassLoaderHierarchyPrinter` to extract and trace the parent delegation tree of a given class from the System ClassLoader up to the Bootstrap ClassLoader.

#### Directory Structure
- [ClassLoaderHierarchyPrinter.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/32-classloader/practice/class-loader-hierarchy-printer/src/ClassLoaderHierarchyPrinter.java)
- [ClassLoaderHierarchyPrinterTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/32-classloader/practice/class-loader-hierarchy-printer/test/ClassLoaderHierarchyPrinterTest.java)
- [ClassLoaderHierarchyPrinter.java (Solution)](file:///home/fhu_thjen/projects/learning-java/32-classloader/practice/class-loader-hierarchy-printer/solution/ClassLoaderHierarchyPrinter.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 32-classloader
```
