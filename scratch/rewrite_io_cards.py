import csv
import os

basic_rows = [
    [
        "io-basic-001",
        "What does a `java.io.File` object represent in Java?",
        "An abstract representation of a file or directory path name, not the actual file contents.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-002",
        "Does creating a `new File(\"example.txt\")` instantiate a file on disk?",
        "No, it only creates a path representation in memory. The file on disk is not created until you call a write stream or createNewFile().",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-003",
        "What does `File.createNewFile()` do, and what does it return?",
        "It atomically creates a new empty file if it doesn't exist, returning true. If the file already exists, it returns false.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-004",
        "What exception must be handled when calling `File.createNewFile()`?",
        "java.io.IOException",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-005",
        "What is the behavior of `File.delete()` when called on a non-empty directory?",
        "It fails and returns false. The directory must be completely empty to be deleted.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-006",
        "How does `File.delete()` indicate success or failure?",
        "It returns a boolean (true if deleted successfully, false otherwise), rather than throwing an exception.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-007",
        "What is the purpose of `File.exists()`?",
        "It checks if the file or directory represented by the path exists on disk.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-008",
        "How can you check if a `File` path refers specifically to a file rather than a directory?",
        "By calling the file.isFile() method.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-009",
        "What does `File.length()` return for a directory or a non-existent file?",
        "It returns 0L.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-010",
        "What does `File.length()` return for a regular file?",
        "The size of the file in bytes.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-011",
        "What is the difference between `File.mkdir()` and `File.mkdirs()`?",
        "mkdir() fails if parent directories don't exist; mkdirs() creates all necessary parent directories recursively.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-012",
        "What does `File.getAbsolutePath()` return?",
        "The absolute path string of the file or directory, starting from the root directory.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-013",
        "How are byte streams hierarchically represented at the base level in Java?",
        "They are represented by the base abstract classes java.io.InputStream (for reading raw 8-bit binary data) and java.io.OutputStream (for writing raw 8-bit data).",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-014",
        "What unit of data is read or written by `InputStream` and `OutputStream`?",
        "Bytes (8-bit binary data).",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-015",
        "What does `InputStream.read()` return when it reaches the end of the stream?",
        "-1",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-016",
        "Why is it critical to close I/O streams?",
        "To release underlying operating system resources like file descriptors and locks.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-017",
        "What is the try-with-resources statement used for in Java I/O?",
        "To automatically close classes implementing AutoCloseable at the end of the block, preventing resource leaks.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-018",
        "What does `FileOutputStream` do by default if the target file already exists?",
        "It overwrites the existing file contents unless the constructor append flag is set to true.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-019",
        "How do you open a `FileOutputStream` in append mode?",
        "By passing true as the second argument to its constructor: new FileOutputStream(file, true).",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-020",
        "What exception is thrown if you try to open a `FileInputStream` on a non-existent file?",
        "java.io.FileNotFoundException",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-021",
        "What is the primary purpose of `BufferedInputStream`?",
        "To read bytes in large blocks (default 8KB) into memory, reducing the number of expensive OS system calls.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-022",
        "What pattern does `BufferedInputStream` use when wrapping a `FileInputStream`?",
        "The Decorator pattern.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-023",
        "Why must you flush or close a `BufferedOutputStream`?",
        "To ensure all buffered data is written out to the underlying file, as some data may still reside in the memory buffer.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-024",
        "How do you manually force a buffered output stream to write its contents to disk?",
        "Call the flush() method.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-025",
        "How are character streams hierarchically represented at the base level in Java?",
        "They are represented by the base abstract classes java.io.Reader (for reading 16-bit character data) and java.io.Writer (for writing 16-bit character data).",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-026",
        "What is the data unit read or written by character streams?",
        "Characters (16-bit Unicode char elements).",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-027",
        "Why should character streams be used instead of byte streams for text files?",
        "They handle character encoding translation (like UTF-8) automatically.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-028",
        "Why should you avoid using character streams to copy binary files like images?",
        "The encoding translation will alter invalid byte sequences into Unicode replacement characters, corrupting the binary data.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-029",
        "What does `FileReader` use for character encoding prior to Java 11?",
        "The platform's default charset, making the code non-portable.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-030",
        "How does `BufferedReader` improve text-reading efficiency?",
        "It buffers characters and provides readLine() to read text line-by-line.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-031",
        "What does `BufferedReader.readLine()` return at the end of a file?",
        "null",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-032",
        "How does `BufferedWriter.newLine()` differ from writing `\\n` directly?",
        "It writes the platform-specific line separator, ensuring compatibility across different operating systems.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-033",
        "What is serialization in Java?",
        "The process of converting an object's state into a byte stream.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-034",
        "What is deserialization in Java?",
        "The process of reconstructing an object from a serialized byte stream.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-035",
        "What interface must a class implement to be serializable?",
        "java.io.Serializable",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-036",
        "Why is `Serializable` called a marker interface?",
        "Because it has no methods or fields; it simply signals to the JVM that the class is eligible for serialization.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-037",
        "What happens if you try to serialize an object of a class that does not implement `Serializable`?",
        "It throws java.io.NotSerializableException at runtime.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-038",
        "Are static fields serialized in Java?",
        "No, static fields belong to the class, not to any individual instance, and are not serialized.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-039",
        "What is the purpose of `serialVersionUID`?",
        "To verify that the sender and receiver of a serialized object have loaded compatible classes.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-040",
        "What happens if there is a `serialVersionUID` mismatch during deserialization?",
        "It throws java.io.InvalidClassException at runtime.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-041",
        "What is the compiler behavior if you do not explicitly define a `serialVersionUID` in a serializable class?",
        "The compiler generates one automatically, but it is highly sensitive to class changes, which can easily cause deserialization failures.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-042",
        "What does the `transient` keyword do?",
        "It prevents a instance variable from being serialized.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-043",
        "What value is assigned to a `transient` reference field during deserialization?",
        "null",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-044",
        "What value is assigned to a `transient int` field during deserialization?",
        "0",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-045",
        "If a subclass is serializable but its parent class is not, what requirement must the parent class meet?",
        "The parent class must define an accessible no-argument constructor so its state can be initialized during deserialization.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-046",
        "What classes are used to serialize and deserialize objects?",
        "java.io.ObjectOutputStream and java.io.ObjectInputStream",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-047",
        "How can a class customize its serialization and deserialization processes?",
        "By defining private writeObject(ObjectOutputStream) and readObject(ObjectInputStream) methods.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-048",
        "What is `java.util.Scanner` primarily used for?",
        "For parsing primitive types and strings from text using regular expressions.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-049",
        "What does closing a `Scanner` wrapped around `System.in` do?",
        "It closes the scanner and also closes the underlying System.in stream.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-050",
        "What happens if you try to read from `System.in` after closing a scanner wrapped around it?",
        "It throws java.util.NoSuchElementException because the stream is closed and cannot be reopened.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-051",
        "What class does `System.in` instantiate?",
        "java.io.InputStream",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-052",
        "What class do `System.out` and `System.err` instantiate?",
        "java.io.PrintStream",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-053",
        "What is the difference between `System.out` and `System.err`?",
        "System.out is standard output (often buffered), while System.err is standard error (unbuffered, flushing immediately).",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-054",
        "Can you serialize an object whose instance variables refer to non-serializable objects?",
        "No, it throws NotSerializableException unless those fields are marked transient.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-055",
        "How does Java deserialization initialize an object without calling its class constructor?",
        "It allocates memory and calls the no-arg constructor of the first non-serializable superclass.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-056",
        "What does `File.getPath()` return?",
        "The path string used to construct the File object (which may be relative or absolute).",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-057",
        "What is the default buffer size of a `BufferedInputStream`?",
        "8,192 bytes (8KB).",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-basic-058",
        "What exception class is the common superclass for most Java I/O errors?",
        "java.io.IOException",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ]
]

extra_rows = [
    [
        "io-extra-001",
        "Explain `File` deeply enough for review.",
        "An abstract representation of file and directory pathnames on disk, which does not access file content directly.",
        "File objects are immutable path keys. They do not hold open file descriptors. Fails if developers assume constructor creates a physical file; must call createNewFile() or use streams.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-002",
        "Explain `Create file` deeply enough for review.",
        "Creating a file on disk using `File.createNewFile()` or writing via output streams.",
        "createNewFile() returns true if file was created, false if it existed. Throws IOException if directory path doesn't exist or permissions are missing.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-003",
        "Explain `Delete file` deeply enough for review.",
        "Deleting a file or directory using `File.delete()`.",
        "Returns boolean indicating success. Fails if the directory is not empty, returning false without throwing an exception. To force delete a non-empty directory, you must recursively delete all of its child files and folders first.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-004",
        "Explain `Check existence` deeply enough for review.",
        "Checking disk presence using `exists()`, `isFile()`, and `isDirectory()`.",
        "exists() only checks path presence. isFile() and isDirectory() resolve the concrete node type on disk. Always check these properties before opening stream readers to prevent hitting FileNotFoundException at runtime.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-005",
        "Explain `Read file metadata` deeply enough for review.",
        "Accessing attributes like name, path, length, and read/write permissions.",
        "Methods like length() (size in bytes), getName() (returns simple name), getPath() (returns creation path), and permissions like canRead()/canWrite() operate solely on filesystem metadata. None of these methods open or read the actual file contents directly.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-006",
        "Explain `Create directory` deeply enough for review.",
        "Creating folders using `mkdir()` or `mkdirs()`.",
        "mkdir() fails if any parent directories in the path are missing. mkdirs() is much safer because it recursively creates all missing parent directories automatically. Both methods return a boolean indicating success.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-007",
        "Explain `InputStream` deeply enough for review.",
        "The abstract superclass for all byte input streams, reading raw 8-bit binary data.",
        "Its read() method blocks execution until input byte data is available, returning -1 at the end of the stream. It is the base abstract class for all byte-based inputs and must be closed to avoid native resource leaks.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-008",
        "Explain `OutputStream` deeply enough for review.",
        "The abstract superclass for all byte output streams, writing raw 8-bit binary data.",
        "Subclasses must implement the write(int) method. It requires explicit flushing and closing to guarantee all buffered bytes are fully committed to the destination disk. It is the base abstract class for all byte-based output streams.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-009",
        "Explain `FileInputStream` deeply enough for review.",
        "A concrete InputStream subclass that reads bytes directly from a file.",
        "Throws FileNotFoundException if the specified file does not exist, lacks permissions, or represents a directory instead of a regular file. Always use it inside a try-with-resources statement to release the file handle automatically.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-010",
        "Explain `FileOutputStream` deeply enough for review.",
        "A concrete OutputStream subclass that writes bytes directly to a file.",
        "By default, overwrites existing files. Can append instead by passing true to the constructor: new FileOutputStream(\"file.txt\", true). Creating a FileOutputStream will immediately truncate an existing file to 0 bytes if the append flag is false.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-011",
        "Explain `BufferedInputStream` deeply enough for review.",
        "An InputStream decorator that wraps a stream and buffers bytes in memory.",
        "Reads large chunks (default 8KB) in single disk reads, reducing CPU context switches and OS system calls significantly. Wraps any existing InputStream using the Decorator pattern to provide seamless in-memory caching.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-012",
        "Explain `BufferedOutputStream` deeply enough for review.",
        "An OutputStream decorator that wraps a stream and buffers writes in memory.",
        "Stores written bytes in a buffer. Data is only written to the target disk stream when the internal buffer is full, or bos.flush()/bos.close() is called. Failure to flush can lead to partial or missing file outputs.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-013",
        "Explain `Reader` deeply enough for review.",
        "The abstract superclass for all character input streams, reading 16-bit char data.",
        "Translates bytes to characters using default or custom character encoding (e.g. UTF-8). Blocks if input is not available. It is the base abstract class for all character input streams and should be closed after use.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-014",
        "Explain `Writer` deeply enough for review.",
        "The abstract superclass for all character output streams, writing 16-bit char data.",
        "Translates characters to bytes using character encoding. Must be flushed and closed to commit all text to disk. It is the base abstract class for all character output streams and should be closed after use.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-015",
        "Explain `FileReader` deeply enough for review.",
        "A concrete Reader class that reads characters from a file on disk.",
        "Gotcha: Prior to Java 11, it used default system encoding, causing platform bugs. Java 11 added charset constructor parameters to allow defining the encoding explicitly. Always specify the encoding when reading files.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-016",
        "Explain `FileWriter` deeply enough for review.",
        "A concrete Writer class that writes characters to a file on disk.",
        "Gotcha: Overwrites by default. Always specify charsets explicitly when writing files to guarantee cross-platform compatibility. Can append to file if instantiated with the append boolean flag set to true.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-017",
        "Explain `BufferedReader` deeply enough for review.",
        "A character input stream decorator that buffers characters and supports line-by-line reading.",
        "Provides readLine() which returns a line of text (excluding line termination) or null when EOF is reached. It is highly performant because it buffers the character input and reads data in large blocks.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-018",
        "Explain `BufferedWriter` deeply enough for review.",
        "A character output stream decorator that buffers characters and supports writing text lines.",
        "Provides newLine() to write a platform-dependent line break, which is safer than hardcoding \\n. Always call flush() or close() to guarantee that the buffered character data is committed to the underlying Writer.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-019",
        "Explain `ObjectInputStream` deeply enough for review.",
        "A byte stream class that deserializes serialized byte streams back into objects.",
        "Provides readObject(). Throws ClassNotFoundException if the class definition of the serialized object is missing in the JVM. Used to reconstruct object graphs from byte streams via deserialization.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-020",
        "Explain `ObjectOutputStream` deeply enough for review.",
        "A byte stream class that serializes Java objects into byte streams.",
        "Provides writeObject(Object). Target object's class must implement Serializable, otherwise throws NotSerializableException. Used to save active object state graphs to byte streams via serialization.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-021",
        "Explain `Serialization` deeply enough for review.",
        "Converting object graphs into binary representations for storage or network transport.",
        "Saves instance fields. Excludes static fields (class state) and fields marked transient. Graph nodes must all be serializable. Non-serializable components throw NotSerializableException at runtime.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-022",
        "Explain `Deserialization` deeply enough for review.",
        "Reconstructing objects from serialized byte streams.",
        "Does not run subclass constructors. Runs the no-arg constructor of the first non-serializable parent class. This allows the non-serializable parent class to initialize its state fields properly.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-023",
        "Explain `Serializable` deeply enough for review.",
        "A marker interface signaling that a class can be serialized.",
        "Has no methods. Implementing it is a declaration of intent; if a class holds non-serializable fields, serialization fails at runtime. It is a marker interface that directs the JVM's default serialization engine.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-024",
        "Explain `serialVersionUID` deeply enough for review.",
        "A unique 64-bit ID verifying compatibility between serialized data and the class version loaded in the JVM.",
        "Mismatch throws InvalidClassException. Compilers generate UIDs if missing, which can change due to small class changes. Explicit declaration is highly recommended to maintain class version compatibility.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-025",
        "What confusion should you avoid with `serialVersionUID`?",
        "Do not rely on compiler-generated serialVersionUID values.",
        "Changes in compiler versions or minor class modifications will alter the generated UID, causing InvalidClassException during deserialization of older files. Always declare it explicitly.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-026",
        "Explain `transient` deeply enough for review.",
        "A keyword marking fields that should be skipped during serialization.",
        "Used for security (passwords) or optimization (cached values). Restored to default values (null, 0, false) during deserialization. Does not save field state when writing to an ObjectOutputStream.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-027",
        "What confusion should you avoid with `transient`?",
        "Do not assume transient fields initialized on declaration retain their values after deserialization.",
        "Java deserialization does not execute field initializers or subclass constructors. Deserialized transient fields always start as null, 0, or false.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-028",
        "Explain `Scanner` deeply enough for review.",
        "A text parsing utility class that parses primitives and strings using regular expressions.",
        "Can parse files, strings, or input streams. Closing a Scanner wrapped around System.in closes System.in itself. This is a common bug, as it makes it impossible to read input from System.in again in the JVM lifetime.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-029",
        "Explain `System.in` deeply enough for review.",
        "The standard input byte stream, wrapping the default input channel (usually keyboard).",
        "It is an instance of InputStream. Closing it makes it impossible to read input again in the JVM lifetime. Representing the standard input channel, it should generally not be closed manually.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-030",
        "Explain `System.out` deeply enough for review.",
        "The standard output byte stream, wrapping the console output.",
        "It is a PrintStream instance. Buffered by default, but flushes on newline characters or explicit print calls. It maps to the standard output of the application and does not throw IOException.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-extra-031",
        "Explain `System.err` deeply enough for review.",
        "The standard error byte stream, wrapping console error output.",
        "It is a PrintStream instance. Typically unbuffered so error outputs are immediately visible on the console, preventing them from being delayed by print stream buffering.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ]
]

cloze_rows = [
    [
        "io-cloze-001",
        "Creating a `new File(\"path\")` does not perform any {{c1::disk I/O}}; it only creates an abstract path representation in memory.",
        "It is a common mistake to assume the constructor creates a physical file.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-002",
        "File.createNewFile() returns {{c1::true}} if the file was successfully created, and {{c2::false}} if the file already exists.",
        "It throws an IOException if parent directories do not exist.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-003",
        "File.delete() will fail and return false if target is a directory that is {{c1::not empty}}.",
        "To delete a directory containing files, you must recursively delete its children first.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-004",
        "File.exists() checks for path presence, while File.isFile() and {{c1::File.isDirectory()}} check the node type.",
        "Always check exists() first to avoid errors when reading properties.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-005",
        "File.length() returns the file size in {{c1::bytes}}, and returns {{c2::0L}} if the file does not exist or is a directory.",
        "This is an unbuffered metadata query.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-006",
        "File.mkdir() fails if parent directories do not exist, whereas {{c1::File.mkdirs()}} creates all nonexistent parent directories recursively.",
        "mkdirs() is much safer when writing nested project folders.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-007",
        "InputStream is the abstract base class for reading {{c1::bytes}}, and its read() method returns {{c2::-1}} at the end of the stream.",
        "read() blocks until a byte is available, EOF is reached, or an exception is thrown.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-008",
        "OutputStream is the abstract base class for writing bytes, and must be closed to prevent {{c1::resource leaks}}.",
        "Unclosed streams keep file handles open in the operating system.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-009",
        "Wrapping resources in a {{c1::try-with-resources}} statement automatically closes them when exiting the block.",
        "The class must implement AutoCloseable to be used in try-with-resources.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-010",
        "FileOutputStream can write in append mode if the second constructor argument is set to {{c1::true}}.",
        "For example, new FileOutputStream(file, true) appends bytes instead of truncating the file.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-011",
        "BufferedInputStream stores bytes in an internal memory buffer of size {{c1::8KB}} by default to optimize OS system calls.",
        "This minimizes disk access and improves I/O performance significantly.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-012",
        "To write all buffered data from a BufferedOutputStream immediately to the output file, call the {{c1::flush()}} method.",
        "Closing the stream also flushes data, but manual flushing is useful to ensure data is committed without closing.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-013",
        "Reader and Writer character streams handle raw 16-bit Unicode characters and manage {{c1::character encoding}} translation automatically.",
        "They translate bytes to chars using charset encoders and decoders.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-014",
        "Using character streams to read or write binary files (like images) results in data {{c1::corruption}} due to character encoding mapping.",
        "Encoding translates arbitrary binary byte values to replacement characters.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-015",
        "Prior to Java 11, FileReader and FileWriter were bound to the default system {{c1::charset}}, rendering output platform-dependent.",
        "Java 11 introduced constructors accepting a Charset parameter.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-016",
        "BufferedReader reads text line-by-line using the {{c1::readLine()}} method, which returns {{c2::null}} when the end of stream is reached.",
        "The returned string does not contain line termination characters.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-017",
        "BufferedWriter provides the {{c1::newLine()}} method to write a platform-independent line separator.",
        "This ensures files read correctly on Windows (\\r\\n) and Unix (\\n).",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-018",
        "To serialize an object in Java, its class must implement the {{c1::Serializable}} marker interface.",
        "Failing to do so throws a NotSerializableException.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-019",
        "Attempting to serialize an object whose class does not implement Serializable throws {{c1::NotSerializableException}} at runtime.",
        "All objects in the serialization graph must implement Serializable.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-020",
        "During serialization, {{c1::static}} fields are not serialized because they represent class state, not instance state.",
        "Static fields are resolved from class memory directly upon loading.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-021",
        "Class fields marked with the {{c1::transient}} keyword are ignored during serialization and restored as {{c2::default}} values.",
        "For example, reference fields restore as null, primitive ints as 0.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-022",
        "Deserialization of an object does not run its own class constructor; instead, it runs the no-arg constructor of the first {{c1::non-serializable}} superclass.",
        "This is a common interview trap regarding object instantiation cycles.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-023",
        "A unique identifier named {{c1::serialVersionUID}} is verified during deserialization to ensure sender/receiver class compatibility.",
        "If class definitions mismatch, deserialization fails.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-024",
        "If a class changes after serialization and lacks an explicit serialVersionUID, deserialization throws {{c1::InvalidClassException}}.",
        "Always declare serialVersionUID explicitly to avoid compiler changes throwing this error.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-025",
        "Custom serialization can be achieved by declaring private {{c1::writeObject}} and {{c2::readObject}} methods inside the serializable class.",
        "These methods allow encryption, decryption, or custom field state manipulation.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-026",
        "Closing a Scanner wrapped around System.in will also close the underlying {{c1::System.in}} stream, making it impossible to read console input again.",
        "Always avoid closing Scanner around System.in if you need standard input again in the application.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-027",
        "System.in is an instance of InputStream, whereas System.out is an instance of {{c1::PrintStream}}.",
        "PrintStream handles output formatting and does not throw IOException.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-028",
        "System.err is typically {{c1::unbuffered}}, meaning output is immediately flushed to the console for error visibility.",
        "In contrast, System.out is buffered by default.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ],
    [
        "io-cloze-029",
        "Deserializing a subclass fails if its non-serializable parent class does not possess a visible {{c1::no-argument constructor}}.",
        "This throws an InvalidClassException at runtime.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io"
    ]
]

code_rows = [
    [
        "io-code-001",
        "What is the benefit of using try-with-resources in this code block?",
        "try (BufferedReader reader = new BufferedReader(new FileReader(\"file.txt\"))) {\\n    System.out.println(reader.readLine());\\n}",
        "Automatically closes resources",
        "It ensures that the BufferedReader (and underlying FileReader) is automatically closed when the try block exits, even if an exception occurs, avoiding resource leaks.",
        "no26_io/README.md | https://docs.oracle.com/javase/tutorial/essential/io/",
        "java::core::io java::code"
    ],
    [
        "io-code-002",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "File file = new File(\"non_existent_folder/file.txt\");\\ntry {\\n    boolean created = file.createNewFile();\\n    System.out.println(created);\\n} catch (IOException e) {\\n    System.out.println(\"Exception\");\\n}",
        "Exception",
        "createNewFile() cannot create a file if the parent directory ('non_existent_folder') does not exist. It throws an IOException at runtime.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-003",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "File dir = new File(\"test_dir\");\\ndir.mkdir();\\nFile file = new File(dir, \"temp.txt\");\\nfile.createNewFile();\\nSystem.out.println(dir.delete());",
        "false",
        "File.delete() returns false when called on a directory that is not empty. Here, test_dir contains temp.txt, so deletion fails.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-004",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "File file = new File(\"test.txt\");\\nSystem.out.println(file.exists());",
        "false",
        "Creating a File object only creates the path representation in memory. It does not touch the filesystem, so exists() returns false unless the file is already present on disk.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-005",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "File dir = new File(\"a/b/c\");\\nSystem.out.println(dir.mkdir() + \" \" + dir.mkdirs());",
        "false true",
        "mkdir() returns false because parent directories a/ and a/b/ do not exist. mkdirs() returns true because it creates all missing parent directories recursively.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-006",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "try (FileInputStream fis = new FileInputStream(\"empty.txt\")) {\\n    System.out.println(fis.read());\\n} catch (IOException e) {\\n    System.out.println(\"Error\");\\n}",
        "-1",
        "InputStream.read() returns -1 when the end of the stream is reached. Since empty.txt is empty, it immediately returns -1.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-007",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "try (FileOutputStream fos = new FileOutputStream(\"out.txt\")) {\\n    fos.write(65);\\n}",
        "Writes character 'A' to out.txt",
        "FileOutputStream.write(int) writes the lower 8 bits of the integer argument as a byte. 65 is the ASCII/Unicode value for the character 'A'.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-008",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "try (FileReader fr = new FileReader(\"test.txt\");\\n     FileWriter fw = new FileWriter(\"test_copy.txt\")) {\\n    int data;\\n    while ((data = fr.read()) != -1) {\\n        fw.write(data);\\n    }\\n}",
        "Copies text contents successfully",
        "FileReader and FileWriter are character streams, which copy text characters correctly while performing character encoding translation.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-009",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "try (BufferedWriter bw = new BufferedWriter(new FileWriter(\"out.txt\"))) {\\n    bw.write(\"Line 1\");\\n}",
        "Writes \"Line 1\" to out.txt",
        "Although BufferedWriter buffers output in memory, wrapping it in a try-with-resources statement ensures it is automatically closed when exiting the block, which flushes the buffer to disk.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-010",
        "What happens when attempting to compile this class?",
        "class Data implements Serializable {\\n    static int x = 10;\\n    transient int y = 20;\\n    int z = 30;\\n}",
        "Compiles successfully",
        "Serializable classes can contain static and transient fields. Static and transient fields will not be saved when serialized.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-011",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "// After deserialization of Data object (x = 10, y = 20, z = 30)\\nSystem.out.println(deserializedData.y + \" \" + deserializedData.z);",
        "0 30",
        "z is a normal field so it is restored as 30. y is marked transient, so it is not serialized and resets to its default value 0. x is static and is not serialized, though it can still be accessed via class context.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-012",
        "What happens when executing this code?",
        "class Address {}\\nclass Person implements Serializable {\\n    Address address = new Address();\\n}",
        "throws NotSerializableException",
        "When serializing an object, Java attempts to serialize its entire object graph. Since Person references Address and Address does not implement Serializable, it throws NotSerializableException at runtime.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-013",
        "What happens when executing this code?",
        "class Address {}\\nclass Person implements Serializable {\\n    transient Address address = new Address();\\n}",
        "Serializes successfully",
        "Marking the non-serializable field address as transient tells Java to skip it during serialization, which avoids the NotSerializableException.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-014",
        "What happens when executing this code?",
        "Scanner scanner = new Scanner(System.in);\\nscanner.close();\\nScanner scanner2 = new Scanner(System.in);\\nscanner2.nextLine();",
        "throws NoSuchElementException",
        "Closing scanner closes the underlying System.in stream. scanner2 attempts to read from the closed stream, which throws a NoSuchElementException.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-015",
        "What happens when executing this code?",
        "class Parent {\\n    Parent(String msg) {}\\n}\\nclass Child extends Parent implements Serializable {\\n    Child() { super(\"Hello\"); }\\n}",
        "throws InvalidClassException",
        "When deserializing a subclass, Java must call the no-argument constructor of its first non-serializable parent class (Parent). Since Parent does not define a no-argument constructor, deserialization fails at runtime with InvalidClassException.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-016",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "File file = new File(\"test.txt\");\\nSystem.out.println(file.getName());",
        "test.txt",
        "getName() returns the last name in the pathname's name sequence, which is test.txt. This does not require disk access.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-017",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(\"temp.txt\"));\\nbos.write(65);\\n// System crash or exit without close/flush",
        "The file temp.txt may remain empty (0 bytes) on disk.",
        "Because BufferedOutputStream buffers writes in memory, data is not written to the underlying file descriptor until the buffer is full (8KB) or flush()/close() is called.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-018",
        "What happens when attempting to compile this class?",
        "class Student implements Serializable {\\n    private static final long serialVersionUID = 1;\\n}",
        "fails to compile",
        "The field serialVersionUID must be of type long. Specifying integer 1 instead of 1L causes a compilation error (type mismatch).",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-019",
        "What is the output of this code, or does it fail to compile/throw an exception?",
        "ByteArrayOutputStream baos = new ByteArrayOutputStream();\\nbaos.write(65);\\nSystem.out.println(baos.toString());",
        "A",
        "ByteArrayOutputStream writes bytes to an in-memory byte array. toString() decodes those bytes using the default charset, yielding the character A.",
        "no26_io/theory/02-bufferedinputstream-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-020",
        "What happens if you try to serialize an enum in Java?",
        "enum Status { ACTIVE, INACTIVE }\\n// Serializing Status.ACTIVE",
        "Serializes successfully",
        "Java handles enum serialization specially. Only the name of the enum constant is serialized, and deserialization uses Enum.valueOf() to recover the reference. Enum classes are implicitly serializable.",
        "no26_io/theory/03-serialization-concepts.md",
        "java::core::io java::code"
    ],
    [
        "io-code-021",
        "What happens when this code is executed?",
        "File file = new File(\"existing.txt\"); // exists on disk\\nFileOutputStream fos = new FileOutputStream(file);\\nfos.close();\\nSystem.out.println(file.length());",
        "0",
        "Instantiating FileOutputStream without the append flag set to true opens the file in write mode, truncating its length to 0 bytes immediately.",
        "no26_io/theory/01-file-concepts.md",
        "java::core::io java::code"
    ]
]

def write_tsv(filepath, headers, rows):
    with open(filepath, "w", encoding="utf-8", newline="") as f:
        writer = csv.writer(f, delimiter="\t", quoting=csv.QUOTE_MINIMAL)
        writer.writerow(headers)
        for r in rows:
            writer.writerow(r)

write_tsv(
    "/home/fhu_thjen/projects/learning-java/no26_io/anki/basic.tsv",
    ["ID", "Front", "Back", "Source", "Tags"],
    basic_rows
)

write_tsv(
    "/home/fhu_thjen/projects/learning-java/no26_io/anki/basic-extra.tsv",
    ["ID", "Front", "Back", "Extra", "Source", "Tags"],
    extra_rows
)

write_tsv(
    "/home/fhu_thjen/projects/learning-java/no26_io/anki/cloze.tsv",
    ["ID", "Text", "Extra", "Source", "Tags"],
    cloze_rows
)

write_tsv(
    "/home/fhu_thjen/projects/learning-java/no26_io/anki/code-question.tsv",
    ["ID", "Question", "Code", "Answer", "Explanation", "Source", "Tags"],
    code_rows
)

print("Successfully rewrote IO cards!")
