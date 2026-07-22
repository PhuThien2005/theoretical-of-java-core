# Exercise: Parameterized Custom Test

## Objective
Implement parameterized test runs using reflection and custom annotations to test validation edge cases.

## Problem Description
A parameterized test runs the same test method multiple times with different arguments. This helps avoid copying and pasting tests for various inputs.
In this exercise, you will create a custom parameterized runner:
1. Define a custom annotation `@MyParameterizedTest` and a data source provider annotation `@MyValueSource(strings = { ... })`.
2. Write a validator method `boolean isValidEmail(String email)` in `ParameterizedCustomTest`.
3. Write a test method that takes a String parameter. Use reflection to parse the values inside `@MyValueSource` and invoke the test method with each string.
