import java.io.File;
import java.io.IOException;

/**
 * Starter template for a high-performance NIO file copy utility.
 */
public class NioFastFileCopy {

    /**
     * Copies the content of source file to destination file using FileChannels
     * and a direct ByteBuffer.
     * 
     * Requirements:
     * - Open FileChannel on FileInputStream (for source) and FileOutputStream (for destination).
     * - Allocate a direct buffer using ByteBuffer.allocateDirect(4096).
     * - Loop read from source channel into buffer, flip it, write to target channel, and clear buffer.
     *
     * @param source the source file
     * @param destination the destination file
     * @throws IOException on I/O issues
     */
    public static void copyFile(File source, File destination) throws IOException {
        // TODO: Implement high-performance copy using FileChannel and direct ByteBuffer
    }
}
