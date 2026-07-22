# Exercise: SOLID Violations Refactoring

## Objective
Refactor a legacy order processor to align with SOLID design principles (specifically SRP, OCP, and DIP).

## Problem Description
The original class `OrderProcessor` violates:
1. **SRP**: It calculates order totals, writes data directly to a database, and sends email notifications.
2. **OCP**: Shipping rates are calculated using an `if-else` chain on shipping speed. Adding a new shipping method requires editing `OrderProcessor`.
3. **DIP**: It directly constructs concrete classes `MySQLDatabase` and `OutlookEmailService` using the `new` keyword, coupling itself tightly to specific databases and email clients.

## Requirements
Refactor the system by decomposing `OrderProcessor`:
1. **DIP & SRP**:
   - Define interfaces `Database` and `NotificationService`.
   - Pass them to the `OrderProcessor` constructor (Dependency Injection).
2. **OCP**:
   - Define a `ShippingStrategy` interface.
   - Implement concrete shipping strategies: `StandardShipping`, `ExpressShipping`, `OvernightShipping`.
   - Inject the strategy or pass it when processing.
3. Keep the public API and integration behavior consistent with the assertions in `SolidViolationsRefactoringTest`.
