# Practice Exercises: Regular Expressions (Regex)

This folder contains hands-on practice exercises to reinforce your understanding of Java Regular Expressions (`java.util.regex`), including Compiled Patterns, Matchers, and extracting content via Capture Groups.

## Exercises

### 1. Log Parser Regex (`log-parser-regex`)
Parsing raw server logs to extract structured entities is a very common task. By using capturing groups in a regular expression, you can parse logs and map them to fields inside a data object.
- **Goal**: Implement a log line parser in `LogParserRegex` that parses structured log lines into a `LogEntry` object using Regex Capture Groups.

#### Directory Structure
- [LogParserRegex.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/30-regex/practice/log-parser-regex/src/LogParserRegex.java)
- [LogParserRegexTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/30-regex/practice/log-parser-regex/test/LogParserRegexTest.java)
- [LogParserRegex.java (Solution)](file:///home/fhu_thjen/projects/learning-java/30-regex/practice/log-parser-regex/solution/LogParserRegex.java)

---

### 2. HTML Link Extractor (`html-link-extractor`)
Extracting links from HTML anchor tags (`<a href="...">`) is a basic step in web scraping and crawl indexers.
- **Goal**: Implement an anchor link extractor in `HtmlLinkExtractor` that searches a block of HTML text and returns a list of all URLs found within `href` attributes.

#### Directory Structure
- [HtmlLinkExtractor.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/30-regex/practice/html-link-extractor/src/HtmlLinkExtractor.java)
- [HtmlLinkExtractorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/30-regex/practice/html-link-extractor/test/HtmlLinkExtractorTest.java)
- [HtmlLinkExtractor.java (Solution)](file:///home/fhu_thjen/projects/learning-java/30-regex/practice/html-link-extractor/solution/HtmlLinkExtractor.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 30-regex
```
