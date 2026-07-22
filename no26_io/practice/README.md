# Practice Exercises: Classic I/O

This folder contains hands-on practice exercises to reinforce your understanding of Java Classic I/O (`java.io`), Object Serialization, the `transient` keyword, and reading files using InputStreams and Readers.

## Exercises

### 1. Object Graph Serializer (`object-graph-serializer`)
Object serialization transforms an object into a byte stream, allowing it to be persisted to disk or sent over a network. Fields marked with the `transient` keyword are excluded from serialization.
- **Goal**: Implement serialization and deserialization for a `UserSession` class, and verify that transient security credentials are not serialized.

#### Directory Structure
- [ObjectGraphSerializer.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no26_io/practice/object-graph-serializer/src/ObjectGraphSerializer.java)
- [ObjectGraphSerializerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no26_io/practice/object-graph-serializer/test/ObjectGraphSerializerTest.java)
- [ObjectGraphSerializer.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no26_io/practice/object-graph-serializer/solution/ObjectGraphSerializer.java)

---

### 2. Log File Tailer (`log-file-tailer`)
A "tailer" monitors a text file (like a server log) and reads newly appended lines since the last check, similar to the Unix command `tail -f`.
- **Goal**: Implement a `LogFileTailer` class that tracks file position offsets using classic IO streams and reads only newly written lines from a log file.

#### Directory Structure
- [LogFileTailer.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no26_io/practice/log-file-tailer/src/LogFileTailer.java)
- [LogFileTailerTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no26_io/practice/log-file-tailer/test/LogFileTailerTest.java)
- [LogFileTailer.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no26_io/practice/log-file-tailer/solution/LogFileTailer.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no26_io
```
