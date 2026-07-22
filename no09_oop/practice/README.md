# Practice Exercises: Object-Oriented Programming (OOP)

This folder contains hands-on practice exercises to reinforce your understanding of Java object-oriented concepts: encapsulation, inheritance, polymorphism, abstract classes, and interfaces.

## Exercises

### 1. Library Management System (`library-management-system`)
A library houses various items, such as books and journals. Although they share characteristics like titles and check-out status, their details and structures differ.
- **Goal**: Implement a class hierarchy where a base class `LibraryItem` encapsulates common state, and subclasses `Book` and `Journal` inherit this state and implement custom polymorphic behavior for retrieving item details.

#### Directory Structure
- [LibraryManagementSystem.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no09_oop/practice/library-management-system/src/LibraryManagementSystem.java)
- [LibraryManagementSystemTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no09_oop/practice/library-management-system/test/LibraryManagementSystemTest.java)
- [LibraryManagementSystem.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no09_oop/practice/library-management-system/solution/LibraryManagementSystem.java)

---

### 2. Generic Billing System (`generic-billing-system`)
Billing systems process different kinds of items: physical items with dynamic discounts and flat-rate monthly services.
- **Goal**: Model a billing system using a common `Billable` interface. You will compare interfaces (used to define common capabilities across unrelated classes) and abstract classes (used to share structure and base calculations among related classes).

#### Directory Structure
- [GenericBillingSystem.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no09_oop/practice/generic-billing-system/src/GenericBillingSystem.java)
- [GenericBillingSystemTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no09_oop/practice/generic-billing-system/test/GenericBillingSystemTest.java)
- [GenericBillingSystem.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no09_oop/practice/generic-billing-system/solution/GenericBillingSystem.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no09_oop
```
