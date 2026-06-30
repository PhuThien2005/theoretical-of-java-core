# Exercise: Pattern Matching with Records

## Objective
Implement an e-commerce order processing system utilizing Java Records and record pattern matching in `instanceof` and `switch` expressions.

## Requirements
1. **Records**:
   - `Customer(String name, CustomerType type)` where `CustomerType` is an enum with `REGULAR`, `VIP`, `ELITE`.
   - `Item(String name, double price)`
   - `interface Discount` (a marker interface).
   - Concrete discount records: `NoDiscount()`, `FlatDiscount(double amount)`, `PercentageDiscount(double rate)`.
   - `Order(String id, Customer customer, List<Item> items, Discount discount)`.

2. **Price Calculation**:
   - Implement `calculateFinalPrice(Order order)`:
     - Sum the prices of all items in the order.
     - Inspect the `Discount` pattern:
       - `FlatDiscount(amount)`: Subtract the amount. Total cannot be negative (return `0.0` if discount exceeds sum).
       - `PercentageDiscount(rate)`: Subtract `sum * rate`.
       - `NoDiscount()`: Subtract nothing.
     - Note: Throw `IllegalArgumentException` if any record has invalid bounds (e.g., negative price, negative discount, percentage rate not in `[0.0, 1.0]`).

3. **Loyalty Points**:
   - Implement `calculateLoyaltyPoints(Order order)` using a switch expression with record patterns:
     - If the customer is a `VIP` customer and receives a `PercentageDiscount(rate)`, the loyalty points are `(int) (totalPrice * rate * 100)`.
     - If the customer is an `ELITE` customer (regardless of discount type), the loyalty points are `(int) (totalPrice * 2.0)`.
     - If the customer is a `VIP` customer with any other discount or no discount, the loyalty points are `(int) (totalPrice * 0.5)`.
     - For any other customers, the loyalty points are `(int) (totalPrice * 0.1)`.
     - Return `0` if `totalPrice` is `0.0`.
