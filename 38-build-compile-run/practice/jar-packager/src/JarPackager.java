package jarpackager;

import java.io.IOException;

public class JarPackager {

    /**
     * Programmatically packages a source directory of compiled .class files 
     * and a generated Manifest (containing the Main-Class specification) 
     * into a single executable JAR file.
     * 
     * @param sourceDir the directory containing class files to package
     * @param outputJarPath the file path where the output JAR will be written
     * @param mainClassName the fully qualified name of the Main class, or null if not executable
     */
    public static void packageJar(String sourceDir, String outputJarPath, String mainClassName) throws IOException {
        // TODO: Validate inputs.
        // TODO: Create a JarOutputStream.
        // TODO: Create and write the manifest entry to "META-INF/MANIFEST.MF".
        // TODO: Recursively traverse the sourceDir and write all files to the JAR stream.
        // TODO: Close streams safely to prevent resource leaks.
    }
}
