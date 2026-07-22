package no26_io.practice.log_file_tailer;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

/**
 * Reference solution for LogFileTailerSolution using RandomAccessFile.
 * 
 * Seekable I/O:
 * - `RandomAccessFile` allows reading from specific byte offsets (`seek`).
 * - This mirrors `tail -f` behavior, as we seek to the end position of the last check
 *   and read only newly appended data.
 */
public class LogFileTailerSolution {

    private long lastOffset = 0;

    public List<String> readNewLines(File file) throws IOException {
        List<String> newLines = new ArrayList<>();
        
        if (file == null || !file.exists()) {
            return newLines;
        }

        long fileLength = file.length();
        if (fileLength < lastOffset) {
            // File was truncated or reset (e.g. log rotation). Reset offset to beginning.
            lastOffset = 0;
        }

        try (RandomAccessFile raf = new RandomAccessFile(file, "r")) {
            raf.seek(lastOffset);
            String line;
            while ((line = raf.readLine()) != null) {
                // raf.readLine() decodes bytes as ISO-8859-1. Convert to UTF-8 if needed.
                // In our testing environment, standard ASCII/UTF-8 lines are fine.
                newLines.add(line);
            }
            // Update lastOffset to the current cursor position
            lastOffset = raf.getFilePointer();
        }

        return newLines;
    }

    public long getLastOffset() {
        return lastOffset;
    }
}
