import java.io.File;
import java.io.IOException;

/**
 * Starter template for a custom dynamic hot ClassLoader.
 */
public class CustomHotClassLoader extends ClassLoader {

    private final String classDir;

    public CustomHotClassLoader(String classDir) {
        this.classDir = classDir;
    }

    /**
     * Finds and loads the class with the specified binary name.
     * 
     * Requirements:
     * - Convert class binary name (e.g. "com.example.Test") to file path (e.g. "classDir/com/example/Test.class").
     * - Read all bytes from the class file.
     * - Define and return the Class using defineClass(name, bytes, 0, bytes.length).
     * - Throw ClassNotFoundException if the file is missing or unreadable.
     */
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // TODO: Implement class file finding, byte reading, and defineClass
        return null;
    }
}
