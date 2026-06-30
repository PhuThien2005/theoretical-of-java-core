import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;

/**
 * Reference solution for CustomHotClassLoaderSolution.
 * 
 * ClassLoading principles:
 * - A custom class loader overrides `findClass` to load class byte arrays.
 * - `defineClass` takes raw bytes and registers the class with the JVM runtime.
 * - To reload classes, a new class loader instance must be created, bypassing the parent cache.
 */
public class CustomHotClassLoaderSolution extends ClassLoader {

    private final String classDir;

    public CustomHotClassLoaderSolution(String classDir) {
        this.classDir = classDir;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // Convert package name dots to folder path separators
        String path = classDir + File.separator + name.replace('.', File.separatorChar) + ".class";
        File file = new File(path);

        if (!file.exists()) {
            throw new ClassNotFoundException("Could not find class file: " + path);
        }

        try {
            byte[] bytes = readClassBytes(file);
            return defineClass(name, bytes, 0, bytes.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Failed to read class bytes for: " + name, e);
        }
    }

    private byte[] readClassBytes(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            return baos.toByteArray();
        }
    }
}
