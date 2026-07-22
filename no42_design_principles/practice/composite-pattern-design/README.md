# Exercise: Composite Pattern Design

## Objective
Implement a file system hierarchy utilizing the Composite Design Pattern.

## Problem Description
The Composite Pattern is a structural design pattern that lets you compose objects into tree structures and then work with these structures as if they were individual objects.
In this exercise, we represent a file system containing files (leaves) and directories (composites). Both implement a shared `FileSystemComponent` interface.

## Requirements
1. **Interface `FileSystemComponent`**:
   - `String getName()`: Get name.
   - `long getSize()`: Get size in bytes.
   - `void print(String indent)`: Recursively prints the structure.

2. **Leaf Class `File`**:
   - Implements `FileSystemComponent`.
   - Has a name and a fixed size (bytes) set in constructor.
   - Size is simply returned by `getSize()`.
   - `print(indent)` prints: `indent + "- File: " + getName() + " (" + getSize() + " bytes)"`.

3. **Composite Class `Directory`**:
   - Implements `FileSystemComponent`.
   - Has a name and a list of `FileSystemComponent` children.
   - Supports methods: `void add(FileSystemComponent component)` and `void remove(FileSystemComponent component)`.
   - `getSize()` returns the dynamically aggregated sum of all its children's sizes (recursively, since children can be directories themselves).
   - `print(indent)` prints: `indent + "+ Directory: " + getName()`, then calls `print(indent + "  ")` on all its children.
