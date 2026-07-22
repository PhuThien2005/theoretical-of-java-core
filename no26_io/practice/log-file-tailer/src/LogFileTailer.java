package no26_io.practice.log_file_tailer;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Starter template for a classic log file tailer.
 */
public class LogFileTailer {

    private long lastOffset = 0;

    /**
     * Reads all newly written lines from the file since the last check.
     * 
     * Requirements:
     * - Open the file using a FileInputStream.
     * - Skip `lastOffset` bytes to position the read cursor.
     * - Read all remaining lines using BufferedReader/InputStreamReader.
     * - Update `lastOffset` to the current end position (file length) for subsequent calls.
     * - Return the list of newly read lines.
     * - If the file does not exist, return an empty list.
     *
     * @param file the log file to monitor
     * @return the list of new lines
     * @throws IOException on I/O issues
     */
    public List<String> readNewLines(File file) throws IOException {
        // TODO: Implement file offset tracking and read logic
        return null;
    }

    public long getLastOffset() {
        return lastOffset;
    }
}
