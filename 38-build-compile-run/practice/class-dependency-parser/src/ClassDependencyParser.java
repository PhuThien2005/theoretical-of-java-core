package classdependencyparser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassDependencyParser {

    /**
     * Parses the constant pool of a compiled Java .class file and returns
     * a list of all class names referenced inside.
     * 
     * @param classFilePath path to the compiled .class file
     * @return list of referenced class names (e.g. "java/lang/System")
     * @throws IOException if file operations or parsing fails
     */
    public static List<String> parseDependencies(String classFilePath) throws IOException {
        // TODO: Validate classFilePath and create DataInputStream.
        // TODO: Verify the magic number is 0xCAFEBABE.
        // TODO: Read minor, major versions and constantPoolCount.
        // TODO: Parse each item in the constant pool using Tag types.
        // TODO: Gather Class references (tag = 7) and look up their UTF-8 names.
        // Return only class names that do not start with '['.
        return null;
    }
}
