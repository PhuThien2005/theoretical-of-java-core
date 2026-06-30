# Exercise: Jar Packager

## Objective
Create an executable JAR (Java Archive) packaging tool that programmatically packages a directory of compiled `.class` files and generates the required executable `META-INF/MANIFEST.MF`.

## Requirements
Implement `JarPackager` with the following methods:
1. `void packageJar(String sourceDir, String outputJarPath, String mainClassName)`:
   - Create a new ZIP/JAR stream to write to `outputJarPath`.
   - Programmatically construct a `Manifest` object (or write a manifest file entry to the archive under `META-INF/MANIFEST.MF`).
   - Add standard manifest headers:
     - `Manifest-Version: 1.0`
     - `Main-Class: <mainClassName>` (only if `mainClassName` is not null or empty).
   - Traverse `sourceDir` recursively and add all compiled `.class` files to the output JAR stream, preserving their relative folder structures (representing package names).
   - Ensure the output file stream is properly closed to prevent resource leaks.
   - Throw `IOException` if file operations fail.
