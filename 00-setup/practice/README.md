# Practice Exercises: Java Environment Setup

This folder contains hands-on practice exercises to verify your development environment setup, retrieve system properties, and query system environment variables.

## Exercises

### 1. Environment Verifier (`environment-verifier`)
Verifying the installed JDK version and vendor details is the first step in setting up any Java development environment.
- **Goal**: Implement `EnvironmentVerifier` to extract the current Java runtime version and verify if the environment meets a minimum required Java version using the modern `Runtime.version()` APIs.

#### Directory Structure
- [EnvironmentVerifier.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/00-setup/practice/environment-verifier/src/EnvironmentVerifier.java)
- [EnvironmentVerifierTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/00-setup/practice/environment-verifier/test/EnvironmentVerifierTest.java)
- [EnvironmentVerifier.java (Solution)](file:///home/fhu_thjen/projects/learning-java/00-setup/practice/environment-verifier/solution/EnvironmentVerifier.java)

---

### 2. Path Validator (`path-validator`)
Operating systems locate executables via the system `PATH` environment variable. Parsing and validating the path entries is a common system administration task.
- **Goal**: Implement a `PathValidator` utility to read the system `PATH` environment variable, split it correctly using the system-dependent path separator, and verify if specific directory keywords exist in the path.

#### Directory Structure
- [PathValidator.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/00-setup/practice/path-validator/src/PathValidator.java)
- [PathValidatorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/00-setup/practice/path-validator/test/PathValidatorTest.java)
- [PathValidator.java (Solution)](file:///home/fhu_thjen/projects/learning-java/00-setup/practice/path-validator/solution/PathValidator.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 00-setup
```
