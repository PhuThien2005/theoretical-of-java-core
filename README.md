# Learning Java

This repository is organized as a Java Core learning system: detailed theory notes, small practice files, and Anki flashcards for spaced repetition.

## How To Study

1. Open each numbered topic folder in order.
2. Start with the topic `README.md`; it acts as the index for that topic.
3. Read the detailed files inside `theory/`.
4. Review the flashcards inside the same topic's `anki/` folder.
5. Sync TSV files into Anki with AnkiConnect, or import them manually.
6. Practice with small Java files in `practice/small-exercises`.
7. Build mini projects in `extras/mini-projects` when the basics feel comfortable.

## Topic Folder Layout

For larger topics, do not put everything into one giant README. Use this structure:

```text
no01_overview/
├── README.md
├── theory/
│   ├── 01-what-is-java.md
│   ├── 02-jvm-jre-jdk.md
│   └── ...
├── terms/
│   └── 01-important-terms.md
└── anki/
    ├── basic.tsv
    ├── basic-extra.tsv
    ├── cloze.tsv
    └── code-question.tsv
```

`README.md` is the entry point. Detailed theory belongs in `theory/*.md`. Under-explained vocabulary belongs in `terms/*.md`. Anki cards belong in the same topic folder under `anki/`.

## Running Simple Java Files

```bash
cd practice/small-exercises
javac HelloWorld.java
java HelloWorld
```

If a file contains multiple non-public classes, compile the file that contains the `main` method:

```bash
javac ArrayPractice.java
java ArrayPractice
```

## Quick Anki Sync

Use the short helper script from the repository root:

```bash
./r.sh check
./r.sh topic no01_overview
./r.sh
```

More commands:

```bash
./r.sh dry
./r.sh dry-topic no01_overview
./r.sh probe
```

## 45 Java Core Topics

- `no01_overview`: Java overview.
- `no02_basic_syntax`: Basic syntax.
- `no03_data_types`: Data types.
- `no04_variables_constants`: Variables and constants.
- `no05_operators`: Operators.
- `no06_control_flow`: Control flow.
- `no07_arrays`: Arrays.
- `no08_string`: String.
- `no09_oop`: Object-oriented programming.
- `no10_modifiers`: Java modifiers.
- `no11_package_access_control`: Packages and access control.
- `no12_exception_handling`: Exception handling.
- `no13_memory_management`: Java memory management.
- `no14_object_class`: The `Object` class.
- `no15_inner_nested_class`: Inner and nested classes.
- `no16_enum`: Enum.
- `no17_annotation`: Annotation.
- `no18_generics`: Generics.
- `no19_collections_framework`: Collections Framework.
- `no20_comparable_comparator`: Comparable and Comparator.
- `no21_lambda_expression`: Lambda expressions.
- `no22_functional_interface`: Functional interfaces.
- `no23_stream_api`: Stream API.
- `no24_optional`: Optional.
- `no25_date_time_api`: Date and Time API.
- `no26_io`: Java IO.
- `no27_nio`: NIO / NIO.2.
- `no28_multithreading`: Multithreading.
- `no29_synchronization_concurrency`: Synchronization and concurrency.
- `no30_regex`: Regular expressions.
- `no31_reflection`: Reflection.
- `no32_classloader`: ClassLoader.
- `no33_module_system`: Java Module System.
- `no34_jdbc`: JDBC.
- `no35_networking`: Networking.
- `no36_security_basic`: Basic security.
- `no37_jvm_advanced`: Advanced JVM.
- `no38_build_compile_run`: Build, compile, and run.
- `no39_utility_apis`: Common utility APIs.
- `no40_modern_java_concepts`: Modern Java concepts.
- `no41_best_practices`: Java best practices.
- `no42_design_principles`: Basic design principles.
- `no43_design_patterns`: Common Java design patterns.
- `no44_unit_testing`: Basic unit testing.
- `no45_interview_questions`: Common Java Core interview questions.

## Localization & Audio Studio

The repository supports translating lessons to Vietnamese and generating synchronized audio narrations (using `edge-tts` with a high-quality Vietnamese AI voice):

1. **Translation**: Synchronize English markdown updates to Vietnamese (`vi/` folder):
   ```bash
   python3 -c "import sys; sys.path.append('scripts'); from translate_optimized import translate_markdown_file; translate_markdown_file('<src_path>', '<dest_path>')"
   ```
2. **Audio & Timestamp Generation**: Re-generate `.mp3` voice and `.json` alignment timestamps:
   ```bash
   python3 generate_single_lesson.py vi/<topic_folder>/theory/<lesson>.md vi/<topic_folder>/theory/audio/<lesson>.json
   ```
3. **Rebuild Manifest**: Re-compile `lessons.json` for the frontend application:
   ```bash
   python3 build_site_manifest.py
   ```

## Supporting Folders

- `no00_setup`: JDK, IDE, compile/run setup.
- `skills`: Skills and rules for generating Java Core theory and Anki cards.
- `agents`: Agent prompts for generating topic notes and flashcards.
- `anki-guides`: Global Anki workflow and TSV templates.
- `scripts`: Utility scripts, including AnkiConnect sync.
- `practice`: Small Java practice files.
- `extras`: Extra topics and mini projects.
- `no99_cheatsheets`: Fast review notes.

## Java Core Goal

After this roadmap, you should be able to:

- Explain how Java runs from `.java` source code to `.class` bytecode.
- Write console programs with variables, conditions, loops, methods, and arrays.
- Understand and apply OOP concepts.
- Choose and use common collections.
- Handle exceptions, files, dates, and time.
- Read and write simple lambda and stream code.
- Build small console projects.
