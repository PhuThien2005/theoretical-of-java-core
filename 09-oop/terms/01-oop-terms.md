# OOP Terms

This file details key terms related to Object-Oriented Programming (OOP) in Java.

## Class

A logical blueprint or template that defines the variables and methods common to all objects of a certain kind.

## Object

An instance of a class that exists physically in memory (heap), having its own state (fields) and behavior (methods).

## Constructor

A block of code similar to a method that is called when an instance of an object is created. Its primary purpose is to initialize the new object's state.

## Encapsulation

The practice of grouping variables and methods together into a class, and hiding internal details (data hiding) to restrict direct access from outside components.

## Inheritance

A design technique where a subclass inherits properties and behaviors from a superclass, fostering code reuse and forming an IS-A relationship.

## Polymorphism

The capability of an action or object to behave differently in different contexts. In Java, this is represented by overloading (compile-time) and overriding (runtime).

## Abstraction

The concept of representing essential features without including background details or explanations. It establishes "what" an object does rather than "how" it does it.

## Access Modifier

Keywords (`private`, `protected`, `public`, or default) that set the visibility and access permissions of classes, methods, constructors, and fields.

## Method Overloading

Declaring multiple methods in the same class with the same name but different signatures (parameter lists). Resolved at compile-time.

## Method Overriding

Redefining a superclass method in a subclass with the exact same name, return type, and parameters. Resolved at runtime.

## Dynamic Method Dispatch

The runtime lookup process where the JVM resolves a call to an overridden method based on the actual type of the object in the heap, not the type of the reference variable.

## Diamond Problem

An ambiguity arising when a subclass inherits from two superclasses that both define a method with the same signature. Java classes avoid this by only allowing single inheritance.

## Upcasting

Implicitly casting a subclass reference to a superclass type. It is always safe and automatic.

## Downcasting

Explicitly casting a superclass reference back to a subclass type. This requires type checks (like `instanceof`) to avoid a runtime `ClassCastException`.

## Abstract Class

A class marked `abstract` that cannot be instantiated and can contain abstract methods (methods without an implementation).

## Interface

A contract specifying a set of methods that a class must implement. It has no instance state and supports multiple inheritance of behavior.

## Default Method

A method defined inside an interface with the `default` keyword that provides a concrete default implementation. Introduced in Java 8 to allow interface evolution without breaking existing code.
