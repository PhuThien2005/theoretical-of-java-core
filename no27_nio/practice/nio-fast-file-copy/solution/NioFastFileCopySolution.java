package no27_nio.practice.nio_fast_file_copy;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Reference solution for NioFastFileCopySolution.
 * 
 * NIO File Copy mechanics:
 * - `FileChannel` acts as an open connection to a file, capable of bulk data transfers.
 * - `ByteBuffer.allocateDirect(capacity)` requests native memory allocations from the OS kernel,
 *   bypassing standard JVM heap garbage collection cycles for raw block I/O throughput.
 * - Flip/Clear loop coordinates cursor states:
 *   - Read moves limit forward.
 *   - Flip prepares buffer to be read from (limit = position, position = 0).
 *   - Clear prepares buffer to be written into (limit = capacity, position = 0).
 */
public class NioFastFileCopySolution {

    public static void copyFile(File source, File destination) throws IOException {
        if (source == null || destination == null) {
            throw new IllegalArgumentException("Source and destination cannot be null");
        }

        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(destination);
             FileChannel srcChannel = fis.getChannel();
             FileChannel destChannel = fos.getChannel()) {

            // Allocate a direct buffer in native memory
            ByteBuffer buffer = ByteBuffer.allocateDirect(4096);

            while (srcChannel.read(buffer) != -1) {
                // Flip buffer: prepare for channel writing
                buffer.flip();

                // Make sure all bytes in buffer are fully written
                while (buffer.hasRemaining()) {
                    destChannel.write(buffer);
                }

                // Clear buffer: prepare for next channel read
                buffer.clear();
            }
        }
    }
}
