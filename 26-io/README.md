# 26 - IO in Java

This topic follows the master outline in [outline.md](../outline.md). The goal is to understand each concept deeply enough to explain it, recognize it in code, and answer interview-style questions.

## Study Order

- [File Concepts](theory/01-file-concepts.md)
- [Bufferedinputstream Concepts](theory/02-bufferedinputstream-concepts.md)
- [Serialization Concepts](theory/03-serialization-concepts.md)
- [Key Terms](terms/01-key-terms.md)

## Outline Checklist

- File
- Create file
- Delete file
- Check existence
- Read file metadata
- Create directory
- InputStream
- OutputStream
- FileInputStream
- FileOutputStream
- BufferedInputStream
- BufferedOutputStream
- Reader
- Writer
- FileReader
- FileWriter
- BufferedReader
- BufferedWriter
- ObjectInputStream
- ObjectOutputStream
- Serialization
- Deserialization
- Serializable
- serialVersionUID
- transient
- Scanner
- System.in
- System.out
- System.err

## Anki Cards

- [Basic](anki/basic.tsv)
- [Basic Extra](anki/basic-extra.tsv)
- [Cloze](anki/cloze.tsv)
- [Code Question](anki/code-question.tsv)

## Self-Check

Before moving to the next topic, verify that you can answer these questions:
1. Why does Java distinguish between byte streams and character streams, and how do encoding charsets mapping apply under the hood?
2. Why does `BufferedInputStream` / `BufferedOutputStream` significantly outperform raw stream operations, and how does the JVM buffer sizing interact with OS disk page caching?
3. Why does Java serialization require `serialVersionUID`, and what compile-time or runtime compatibility issues occur if it is missing or mismatched during class evolution?
4. Why are `transient` fields excluded from serialization, and what happens to transient fields during deserialization (do constructor rules or zero-value initialization rules apply)?
5. Why is the default Java serialization mechanism considered a security liability, and what are modern alternatives or mitigation strategies?

## Mermaid Overview

```mermaid
flowchart TD
    A[IO in Java] --> B[Definitions]
    A --> C[Rules and syntax]
    A --> D[Common mistakes]
    A --> E[Interview recall]
```

## Reference Links

- https://docs.oracle.com/javase/tutorial/essential/io/
- https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/io/package-summary.html
