package jarpackager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

public class JarPackagerSolution {

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
        if (sourceDir == null || outputJarPath == null) {
            throw new IllegalArgumentException("Source directory and output path cannot be null");
        }

        File srcFile = new File(sourceDir);
        if (!srcFile.exists() || !srcFile.isDirectory()) {
            throw new IllegalArgumentException("Source directory must exist and be a directory: " + sourceDir);
        }

        // Construct Manifest attributes
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
        if (mainClassName != null && !mainClassName.trim().isEmpty()) {
            manifest.getMainAttributes().put(Attributes.Name.MAIN_CLASS, mainClassName);
        }

        // Create JarOutputStream (it will auto-create and write META-INF/MANIFEST.MF)
        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(outputJarPath), manifest)) {
            File[] files = srcFile.listFiles();
            if (files != null) {
                for (File file : files) {
                    addFileToJar(file, "", jos);
                }
            }
        }
    }

    private static void addFileToJar(File source, String entryPath, JarOutputStream jos) throws IOException {
        String name = entryPath + source.getName();
        if (source.isDirectory()) {
            // Ensure trailing slash for directory entry in zip/jar
            if (!name.endsWith("/")) {
                name += "/";
            }
            JarEntry entry = new JarEntry(name);
            entry.setTime(source.lastModified());
            jos.putNextEntry(entry);
            jos.closeEntry();

            File[] children = source.listFiles();
            if (children != null) {
                for (File child : children) {
                    addFileToJar(child, name, jos);
                }
            }
        } else {
            // Write individual file content
            JarEntry entry = new JarEntry(name);
            entry.setTime(source.lastModified());
            jos.putNextEntry(entry);
            
            try (FileInputStream in = new FileInputStream(source)) {
                byte[] buffer = new byte[2048];
                int bytesRead;
                while ((bytesRead = in.read(buffer)) != -1) {
                    jos.write(buffer, 0, bytesRead);
                }
            }
            jos.closeEntry();
        }
    }
}
