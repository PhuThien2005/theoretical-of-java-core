# Practice Exercises: Enums

This folder contains hands-on practice exercises to reinforce your understanding of Java enums, including custom enum fields, constructors, methods, and switch-based state transitions.

## Exercises

### 1. Order Status Workflow (`order-status-workflow`)
Java enums are full classes. They can have fields, constructors, methods, and implement interfaces. This makes them highly suitable for modeling workflows and state machines.
- **Goal**: Implement an `OrderStatus` enum that defines a state machine for order processing (PENDING, PAID, SHIPPED, DELIVERED, CANCELLED) and controls allowed transitions.

#### Directory Structure
- [OrderStatusWorkflow.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no16_enum/practice/order-status-workflow/src/OrderStatusWorkflow.java)
- [OrderStatusWorkflowTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no16_enum/practice/order-status-workflow/test/OrderStatusWorkflowTest.java)
- [OrderStatusWorkflow.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no16_enum/practice/order-status-workflow/solution/OrderStatusWorkflow.java)

---

### 2. Card Deck Simulator (`card-deck-simulator`)
A playing card is a combination of two enum types: Suit and Rank. Ranks have associated point values (e.g. Ace is 11, Face cards are 10, Number cards are their face value).
- **Goal**: Build a card simulator utilizing two distinct nested enums (`Suit` and `Rank`), populate a full 52-card deck, and compute hand point totals.

#### Directory Structure
- [CardDeckSimulator.java (Starter Code)](file:///home/fhu_thjen/projects/learning-java/no16_enum/practice/card-deck-simulator/src/CardDeckSimulator.java)
- [CardDeckSimulatorTest.java (Tests)](file:///home/fhu_thjen/projects/learning-java/no16_enum/practice/card-deck-simulator/test/CardDeckSimulatorTest.java)
- [CardDeckSimulator.java (Solution)](file:///home/fhu_thjen/projects/learning-java/no16_enum/practice/card-deck-simulator/solution/CardDeckSimulator.java)

---

## How to Verify Your Solutions

You can run the automatic verification script from the root of the repository:

```bash
python3 scripts/verify_exercise.py no16_enum
```
