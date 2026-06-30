# Practice Exercises: Annotations

This folder contains hands-on practice exercises to reinforce your understanding of Java custom annotations, retention policies, targets, and processing annotations at runtime using Java reflection.

## Exercises

### 1. JSON Serializer Prototype (`json-serializer-prototype`)
Annotations are metadata added to source code that can be inspected at runtime. Modern serialization frameworks (like Jackson or Gson) inspect classes for custom labels to map fields to JSON keys.
- **Goal**: Implement a custom `@JsonSerializable` type annotation and a `@JsonField` field annotation, and write a reflection-based serializer that converts objects into JSON strings.

#### Directory Structure
- [JsonSerializerPrototype.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/17-annotation/practice/json-serializer-prototype/src/JsonSerializerPrototype.java)
- [JsonSerializerPrototypeTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/17-annotation/practice/json-serializer-prototype/test/JsonSerializerPrototypeTest.java)
- [JsonSerializerPrototype.java (Solution)](file:///home/fhu_thjen/projects/learning-java/17-annotation/practice/json-serializer-prototype/solution/JsonSerializerPrototype.java)

---

### 2. Validation Annotation Processor (`validation-annotation-processor`)
Many enterprise frameworks (like Hibernate Validator) use annotations to enforce constraints on model data fields.
- **Goal**: Implement custom field annotations `@NotNull` and `@Min`, and write a reflection-based validation processor `ValidationAnnotationProcessor` that verifies object state and throws validation errors on violation.

#### Directory Structure
- [ValidationAnnotationProcessor.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/17-annotation/practice/validation-annotation-processor/src/ValidationAnnotationProcessor.java)
- [ValidationAnnotationProcessorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/17-annotation/practice/validation-annotation-processor/test/ValidationAnnotationProcessorTest.java)
- [ValidationAnnotationProcessor.java (Solution)](file:///home/fhu_thjen/projects/learning-java/17-annotation/practice/validation-annotation-processor/solution/ValidationAnnotationProcessor.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 17-annotation
```
