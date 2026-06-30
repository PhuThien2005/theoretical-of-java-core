package classdependencyparser;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassDependencyParserSolution {

    /**
     * Parses the constant pool of a compiled Java .class file and returns
     * a list of all class names referenced inside.
     * 
     * @param classFilePath path to the compiled .class file
     * @return list of referenced class names (e.g. "java/lang/System")
     * @throws IOException if file operations or parsing fails
     */
    public static List<String> parseDependencies(String classFilePath) throws IOException {
        if (classFilePath == null) {
            throw new IllegalArgumentException("Class file path cannot be null");
        }

        List<String> dependencies = new ArrayList<>();

        try (DataInputStream in = new DataInputStream(new FileInputStream(classFilePath))) {
            // 1. Verify Magic Number (4 bytes)
            int magic = in.readInt();
            if (magic != 0xCAFEBABE) {
                throw new IOException("Invalid class file format: magic number mismatch");
            }

            // 2. Minor and Major Version (4 bytes)
            int minor = in.readUnsignedShort();
            int major = in.readUnsignedShort();

            // 3. Constant Pool Count (2 bytes)
            int constantPoolCount = in.readUnsignedShort();

            Object[] pool = new Object[constantPoolCount];
            int[] classIndices = new int[constantPoolCount];
            int classCount = 0;

            // 4. Parse Constant Pool Items
            // The index starts from 1; element at 0 is reserved by the JVM.
            for (int i = 1; i < constantPoolCount; i++) {
                int tag = in.readUnsignedByte();
                switch (tag) {
                    case 1: // CONSTANT_Utf8
                        pool[i] = in.readUTF();
                        break;
                    case 7: // CONSTANT_Class
                        classIndices[classCount++] = in.readUnsignedShort();
                        // Store the index to point back to the Utf8 entry later
                        pool[i] = classIndices[classCount - 1]; 
                        break;
                    case 3: // CONSTANT_Integer
                    case 4: // CONSTANT_Float
                    case 9: // CONSTANT_Fieldref
                    case 10: // CONSTANT_Methodref
                    case 11: // CONSTANT_InterfaceMethodref
                    case 12: // CONSTANT_NameAndType
                    case 17: // CONSTANT_Dynamic
                    case 18: // CONSTANT_InvokeDynamic
                        in.skipBytes(4);
                        break;
                    case 5: // CONSTANT_Long
                    case 6: // CONSTANT_Double
                        in.skipBytes(8);
                        // Long and Double entries occupy two consecutive slots in the constant pool.
                        i++; 
                        break;
                    case 8: // CONSTANT_String
                    case 16: // CONSTANT_MethodType
                    case 19: // CONSTANT_Module
                    case 20: // CONSTANT_Package
                        in.skipBytes(2);
                        break;
                    case 15: // CONSTANT_MethodHandle
                        in.skipBytes(3);
                        break;
                    default:
                        throw new IOException("Unsupported constant pool tag: " + tag);
                }
            }

            // 5. Extract referenced class names
            for (int i = 0; i < classCount; i++) {
                int utf8Index = classIndices[i];
                if (utf8Index > 0 && utf8Index < constantPoolCount) {
                    Object item = pool[utf8Index];
                    if (item instanceof String) {
                        String className = (String) item;
                        // Exclude array types (e.g. "[Ljava/lang/String;")
                        if (!className.startsWith("[")) {
                            dependencies.add(className);
                        }
                    }
                }
            }
        }

        return dependencies;
    }
}
