# Advanced JVM - Part 3

## Learning Goal

This file covers a focused slice of **Advanced JVM**. Study each concept as a practical Java rule, not as isolated vocabulary.

## Outline Coverage

| Concept | What to know |
| --- | --- |
| `Survivor` |`Survivor` — Survivor provides specific functionality and rules in Java development. |
| `Old Generation` |`Old Generation` — Old Generation provides specific functionality and rules in Java development. |
| `GC algorithms:` | GC algorithms is a group of related rules in Advanced JVM that groups several related details. |
| `Serial GC` |`Serial GC` — Serial GC provides specific functionality and rules in Java development. |
| `Parallel GC` |`Parallel GC` — Parallel GC provides specific functionality and rules in Java development. |
| `old CMS` |`old CMS` — old CMS provides specific functionality and rules in Java development. |
| `G1 GC` |`G1 GC` — G1 GC provides specific functionality and rules in Java development. |
| `ZGC` |`ZGC` — ZGC provides specific functionality and rules in Java development. |

## Detailed Notes

### Survivor

### Old Generation

### GC algorithms:

GC algorithms is a group of related rules in Advanced JVM that groups several related details.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `GC algorithms:` in one sentence.
- Recognize `GC algorithms:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `GC algorithms:`.

Tiny example or mental model:

- When reading code, ask: what does `GC algorithms:` change, allow, reject, or clarify?

### Serial GC

`Serial GC` — Serial GC provides specific functionality and rules in Java development.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Serial GC` in one sentence.
- Recognize `Serial GC` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Serial GC`.

Tiny example or mental model:

- When reading code, ask: what does `Serial GC` change, allow, reject, or clarify?

### Parallel GC

`Parallel GC` — Parallel GC provides specific functionality and rules in Java development.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Parallel GC` in one sentence.
- Recognize `Parallel GC` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Parallel GC`.

Tiny example or mental model:

- When reading code, ask: what does `Parallel GC` change, allow, reject, or clarify?

### old CMS

### G1 GC

`G1 GC` — G1 GC provides specific functionality and rules in Java development.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `G1 GC` in one sentence.
- Recognize `G1 GC` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `G1 GC`.

Tiny example or mental model:

- When reading code, ask: what does `G1 GC` change, allow, reject, or clarify?

### ZGC

`ZGC` — ZGC provides specific functionality and rules in Java development.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `ZGC` in one sentence.
- Recognize `ZGC` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `ZGC`.

Tiny example or mental model:

- When reading code, ask: what does `ZGC` change, allow, reject, or clarify?

## Code Examples

### Selecting a GC Algorithm via CLI Flags
```bash
# Enable G1 GC
java -XX:+UseG1GC -jar app.jar

# Enable low-latency ZGC
java -XX:+UseZGC -jar app.jar
```

## Common Mistakes

- **Using Serial GC on multi-core servers**: Serial GC uses a single thread for garbage collection. It is fine for tiny CLI tools or single-core containers, but causes terrible pauses on multi-threaded servers.
- **Assuming G1 has contiguous generations**: Unlike Parallel GC, G1 partitions the heap into equal-sized virtual regions. A region can act as Eden, Survivor, or Old dynamically.

## Common Review Prompts

- Which concepts here are compile-time rules?
- Which concepts here affect runtime behavior?
- Which concepts here are likely interview traps?

## Why Survivor Spaces Prevent Heap Fragmentation

The generational garbage collection model uses Survivor spaces (S0 and S1) alongside Eden to prevent heap memory fragmentation and avoid expensive full-heap compactions. According to the weak generational hypothesis, the vast majority of allocated objects die shortly after creation. Instead of allocating and freeing memory in place, the JVM allocates new objects in the **Eden** space. During a minor garbage collection, active (surviving) objects in Eden are copied to one of the empty Survivor spaces (e.g., S0), leaving the Eden space completely contiguous and free of gaps. In subsequent minor GCs, the JVM copies surviving objects from both Eden and the active Survivor space (S0) to the second Survivor space (S1), swapping their roles. By copying survivors to a clean, contiguous destination space and clearing the origin spaces entirely, the JVM avoids memory fragmentation without requiring complex and slow compaction algorithms.

### Mental Model: Copy-and-Evacuate Process

```text
  Initial State:
  [ Eden: Obj A (live), Obj B (dead) ]  ===> Minor GC copies Obj A to S0
  [ S0: Empty                        ]       and clears Eden completely.
  [ S1: Empty                        ]
  
  After Minor GC 1:
  [ Eden: Empty                      ]
  [ S0: Obj A (live, age 1)          ]
  [ S1: Empty                        ]
  
  Next State (after new allocations):
  [ Eden: Obj C (live), Obj D (dead) ]  ===> Minor GC copies Obj C and A to S1,
  [ S0: Obj A (live, age 1)          ]       clearing Eden and S0 completely.
  [ S1: Empty                        ]
  
  After Minor GC 2:
  [ Eden: Empty                      ]
  [ S0: Empty                        ]
  [ S1: Obj A (age 2), Obj C (age 1) ]
```

### Code Example

Below is code simulating rapid creation of short-lived objects. Because these objects are short-lived, they are created in Eden, never age into the Old generation, and are collected during Minor GC from Eden/Survivor.

```java
package theory;

import java.util.ArrayList;
import java.util.List;

public class GenerationalGcSimulation {
    public static void main(String[] args) {
        // High allocation rate of short-lived objects
        for (int i = 0; i < 1_000_000; i++) {
            // These strings are created in Eden and die immediately in the next Minor GC
            String shortLived = new String("Short-lived object " + i);
            
            // To simulate surviving objects that age and move to Survivor/Old
            if (i % 100_000 == 0) {
                System.out.println("Allocated: " + shortLived);
            }
        }
    }
}
/* Output:
Allocated: Short-lived object 0
Allocated: Short-lived object 100000
Allocated: Short-lived object 200000
Allocated: Short-lived object 300000
Allocated: Short-lived object 400000
Allocated: Short-lived object 500000
Allocated: Short-lived object 600000
Allocated: Short-lived object 700000
Allocated: Short-lived object 800000
Allocated: Short-lived object 900000
*/
```

### Cause-Effect Chain

New objects allocated in Eden &rarr; Minor GC triggers &rarr; Live objects copied to S0, Eden cleared &rarr; Next Minor GC triggers &rarr; Live objects from Eden & S0 copied to S1, Eden & S0 cleared &rarr; Aging objects promoted to Old Gen after age threshold &rarr; Contiguous memory layout preserved without fragmentation.

## Reference Links

- https://docs.oracle.com/en/java/javase/21/gctuning/factors-affecting-garbage-collection-performance.html (Generational GC & Aging)

