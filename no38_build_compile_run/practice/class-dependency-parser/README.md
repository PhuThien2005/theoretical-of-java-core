# Exercise: Class Dependency Parser

## Objective
Implement a JVM bytecode class file parser that extracts dependency information (class names referenced in the constant pool).

## Requirements
When a Java file compiles, it contains references to external classes (e.g. `java/lang/System`, `java/util/List`) inside its `.class` constant pool under the type `CONSTANT_Class_info`.
A `.class` file has a strict binary format:
1. Magic Number (4 bytes): `0xCAFEBABE`.
2. Minor/Major Version (4 bytes).
3. Constant Pool Count (2 bytes): Number of constants in the pool + 1.
4. Constant Pool Items: A series of items. Each item starts with a 1-byte tag indicating its type:
   - `CONSTANT_Utf8_info` (tag = 1): Contains a UTF-8 string. Length is defined by the next 2 bytes, followed by the actual bytes.
   - `CONSTANT_Class_info` (tag = 7): Contains a 2-byte index into the constant pool pointing to a `CONSTANT_Utf8` item representing the class name.
   - Other tags are of fixed size:
     - Tag 3, 4, 9, 10, 11, 12, 18: size is 4 bytes (e.g., Integer, Float, Methodref, InterfaceMethodref, NameAndType).
     - Tag 5, 6: size is 8 bytes (Double, Long). *Note: These double-sized constants take up two constant pool slots.*
     - Tag 8, 16, 19, 20: size is 2 bytes (String, MethodType).
     - Tag 15: size is 3 bytes (MethodHandle).

Implement the method `List<String> parseDependencies(String classFilePath)` in `ClassDependencyParser`:
- Validate the magic number is `0xCAFEBABE`. If not, throw `IOException("Invalid class file format")`.
- Parse the constant pool:
  - First, read the constant pool items into memory.
  - Keep track of the tag type and associated payload/references.
  - Extract all UTF-8 strings pointed to by `CONSTANT_Class` entries.
  - Return a list of all class names referenced in the pool (e.g., `java/lang/Object`, `java/lang/String`, etc.).
  - Exclude primitive type representations or array notations if they start with `[` (e.g., `[Ljava/lang/String;`).
