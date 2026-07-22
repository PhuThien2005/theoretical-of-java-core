# Practice Exercises: Java Module System (Project Jigsaw)

This folder contains hands-on practice exercises to reinforce your understanding of Java 9's Module System (Jigsaw), including modular declarations, exports, requires, and modular service discovery using `ServiceLoader`.

## Exercises

### 1. Modular App (`modular-app`)
Java modules encapsulate code packages. By writing a `module-info.java` file, you explicitly declare which standard JDK modules your code requires and which packages it exports to other modules.
- **Goal**: Implement a modular configuration for a `my.module` app that requires the JDK `java.logging` platform module and exports its own service package.

#### Directory Structure
- [module-info.java (Starter)](file:///home/fhu_thjen/projects/learning-java/no33_module_system/practice/modular-app/src/module-info.java)
- [ModularApp.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no33_module_system/practice/modular-app/src/my/module/ModularApp.java)
- [ModularAppTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no33_module_system/practice/modular-app/test/my/module/ModularAppTest.java)
- [module-info.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no33_module_system/practice/modular-app/solution/module-info.java)
- [ModularApp.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no33_module_system/practice/modular-app/solution/my/module/ModularApp.java)

---

### 2. Service Loader Provider (`service-loader-provider`)
The `ServiceLoader` pattern allows separating service interfaces from implementations. In the module system, modules declare service consumers using `uses` and service providers using `provides ... with ...`.
- **Goal**: Implement a modular translation service using `ServiceLoader` where the module provides and consumes its own translation implementation.

#### Directory Structure
- [module-info.java (Starter)](file:///home/fhu_thjen/projects/learning-java/no33_module_system/practice/service-loader-provider/src/module-info.java)
- [ServiceLoaderProvider.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no33_module_system/practice/service-loader-provider/src/my/service/ServiceLoaderProvider.java)
- [ServiceLoaderProviderTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no33_module_system/practice/service-loader-provider/test/my/service/ServiceLoaderProviderTest.java)
- [module-info.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no33_module_system/practice/service-loader-provider/solution/module-info.java)
- [ServiceLoaderProvider.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no33_module_system/practice/service-loader-provider/solution/my/service/ServiceLoaderProvider.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no33_module_system
```
