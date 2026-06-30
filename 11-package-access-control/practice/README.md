# Practice Exercises: Packages & Access Control

This folder contains hands-on practice exercises to reinforce your understanding of Java packages, import statements, and access modifiers (`public`, `protected`, default/package-private, and `private`).

## Exercises

### 1. Cross-Package Visibility Test (`cross-package-visibility-test`)
Access modifiers control visibility across packages. In this exercise, you will create a parent class in one package, a subclass in a different package, and a neighbor class in the same package to verify which fields are accessible in each scenario.
- **Goal**: Implement the visibility mapping across `alpha` and `beta` packages while adhering to compiler-enforced access rules.

#### Directory Structure
- [ParentClass.java (Starter & Solution)](file:///home/fhu_thjen/projects/learning-java/11-package-access-control/practice/cross-package-visibility-test/src/ParentClass.java)
- [ChildInDifferentPackage.java (Starter & Solution)](file:///home/fhu_thjen/projects/learning-java/11-package-access-control/practice/cross-package-visibility-test/src/ChildInDifferentPackage.java)
- [NeighborInSamePackage.java (Starter & Solution)](file:///home/fhu_thjen/projects/learning-java/11-package-access-control/practice/cross-package-visibility-test/src/NeighborInSamePackage.java)
- [VisibilityTester.java (Starter & Solution)](file:///home/fhu_thjen/projects/learning-java/11-package-access-control/practice/cross-package-visibility-test/src/VisibilityTester.java)
- [VisibilityTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/11-package-access-control/practice/cross-package-visibility-test/test/VisibilityTest.java)

---

### 2. Encapsulation Boundary Simulator (`encapsulation-boundary-simulator`)
Encapsulation boundaries protect internal sub-components from direct external exposure by using package-private visibility.
- **Goal**: Implement a public `ServiceManager` that acts as the entry point, coordinating with a package-private `InternalWorker` class. Verify that external client packages cannot directly access the internal helper.

#### Directory Structure
- [InternalWorker.java (Starter & Solution)](file:///home/fhu_thjen/projects/learning-java/11-package-access-control/practice/encapsulation-boundary-simulator/src/InternalWorker.java)
- [ServiceManager.java (Starter & Solution)](file:///home/fhu_thjen/projects/learning-java/11-package-access-control/practice/encapsulation-boundary-simulator/src/ServiceManager.java)
- [BoundaryTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/11-package-access-control/practice/encapsulation-boundary-simulator/test/BoundaryTest.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py 11-package-access-control
```
