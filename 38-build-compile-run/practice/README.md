# Practice Exercises: Build, Compile, and Run

This folder contains hands-on practice exercises to reinforce your understanding of compiling, packaging, and analyzing Java files, manifests, and bytecode dependencies.

## Exercises

### 1. Jar Packager (`jar-packager`)
A tool that dynamically packages compiled Java classes and a custom manifest into an executable JAR file:
- **Executable JAR**: Learn how to write `MANIFEST.MF` programmatically, specify the `Main-Class` entry, and use `java.util.jar` APIs to pack files.

#### Directory Structure
- [JarPackager.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/38-build-compile-run/practice/jar-packager/src/JarPackager.java)
- [JarPackagerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/38-build-compile-run/practice/jar-packager/test/JarPackagerTest.java)
- [JarPackager.java (Solution)](file:///home/fhu_thjen/projects/learning-java/38-build-compile-run/practice/jar-packager/solution/JarPackager.java)

---

### 2. Class Dependency Parser (`class-dependency-parser`)
A parser that inspects raw `.class` JVM bytecode files to extract dependency information:
- **Bytecode Inspection**: Parse constant pool entries in raw compiled Java class bytecode to identify external dependencies and imported package names.

#### Directory Structure
- [ClassDependencyParser.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/38-build-compile-run/practice/class-dependency-parser/src/ClassDependencyParser.java)
- [ClassDependencyParserTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/38-build-compile-run/practice/class-dependency-parser/test/ClassDependencyParserTest.java)
- [ClassDependencyParser.java (Solution)](file:///home/fhu_thjen/projects/learning-java/38-build-compile-run/practice/class-dependency-parser/solution/ClassDependencyParser.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository to test your implementations:

```bash
python3 scripts/verify_exercise.py 38-build-compile-run
```
