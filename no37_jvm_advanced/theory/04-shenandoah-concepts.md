# Advanced JVM - Part 4

| Concept | What to know |
| --- | --- |
| `Shenandoah` |`Shenandoah` — Shenandoah provides specific functionality and rules in Java development. |
| `Stop-the-world` |`Stop-the-world` — Stop-the-world provides specific functionality and rules in Java development. |
| `Minor GC` |`Minor GC` — Minor GC provides specific functionality and rules in Java development. |
| `Major GC` |`Major GC` — Major GC provides specific functionality and rules in Java development. |
| `Full GC` |`Full GC` — Full GC provides specific functionality and rules in Java development. |
| `Basic JVM tuning:` | The JVM executes bytecode and manages runtime services such as memory, JIT, and GC. |
| `-Xms` |`Xms` — Xms provides specific functionality and rules in Java development. |
| `-Xmx` |`Xmx` — Xmx provides specific functionality and rules in Java development. |

## Detailed Notes

### Shenandoah

### Stop-the-world

### Minor GC

`Minor GC` — Minor GC provides specific functionality and rules in Java development.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Minor GC` in one sentence.
- Recognize `Minor GC` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Minor GC`.

Tiny example or mental model:

- When reading code, ask: what does `Minor GC` change, allow, reject, or clarify?

### Major GC

`Major GC` — Major GC provides specific functionality and rules in Java development.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Major GC` in one sentence.
- Recognize `Major GC` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Major GC`.

Tiny example or mental model:

- When reading code, ask: what does `Major GC` change, allow, reject, or clarify?

### Full GC

`Full GC` — Full GC provides specific functionality and rules in Java development.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Full GC` in one sentence.
- Recognize `Full GC` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Full GC`.

Tiny example or mental model:

- When reading code, ask: what does `Full GC` change, allow, reject, or clarify?

### Basic JVM tuning:

The JVM executes bytecode and manages runtime services such as memory, JIT, and GC.

It matters because runtime behavior explains performance, memory errors, startup behavior, and many interview questions. A common confusion is mixing compile-time concepts with JVM runtime services.

Practical check:

- Define `Basic JVM tuning:` in one sentence.
- Recognize `Basic JVM tuning:` in code, commands, documentation, or interview prompts.
- Explain one bug, limitation, or tradeoff related to `Basic JVM tuning:`.

Tiny example or mental model:

- When reading code, ask: what does `Basic JVM tuning:` change, allow, reject, or clarify?

### -Xms

### -Xmx

## Code Examples

### JVM Tuning Flags Example
```bash
# Set initial heap to 1GB, max heap to 2GB, and target a 50ms GC pause time
java -Xms1g -Xmx2g -XX:MaxGCPauseMillis=50 -jar app.jar
```

## Common Mistakes

- **Mismatched -Xms and -Xmx**: If `-Xms` is smaller than `-Xmx`, the JVM will dynamically resize the heap. This resizing causes GC pauses and performance overhead. Setting them equal is best practice for production.
- **Setting MaxGCPauseMillis too low**: Setting it to an unrealistic target (e.g. 5ms) can cause the GC to run continuously, starving application threads of CPU.

## Why Shenandoah GC Achieves Ultra-Low Pause Times

Shenandoah GC achieves ultra-low pause times that are independent of the heap size by performing its compaction phase concurrently with running Java application threads. Unlike traditional garbage collectors like G1 or Parallel GC, which stop all application threads (Stop-The-World) to copy objects and compact memory regions, Shenandoah executes this compaction step concurrently. To prevent race conditions while application threads read or write to objects that are in the process of being moved, Shenandoah employs a mechanism called **Brooks Pointers** (in older JDK versions) or **Load/Write Barriers** (in newer versions). Every object on the heap prefix-prepends a reference field pointing to itself (the Brooks Pointer). When the concurrent GC thread copies an object to a new region, it uses a Compare-And-Swap (CAS) instruction to update the old object's Brooks Pointer to point to the new copy, causing all application threads executing load barriers to transparently redirect reads and writes to the new object location.

### Mental Model: Concurrent Compaction and Brooks Pointer

```text
  1. Before Copy (Normal State):
     [ Application Reference ] ---> [ Object Header | Brooks Pointer ---> Self | Data ]
  
  2. During Concurrent Copy:
     [ GC Thread copies Object to new region ]
     Old Object (From-Space):      [ Object Header | Brooks Pointer ---> Self | Data ]
     New Object (To-Space):        [ Object Header | Brooks Pointer ---> Self | Data ]
     
  3. After CAS Pointer Update:
     Old Object (From-Space):      [ Object Header | Brooks Pointer ---> To-Space Copy | Data ]
     New Object (To-Space):        [ Object Header | Brooks Pointer ---> Self           | Data ]
     
  4. Redirection:
     [ Application Reference ] ---> Old Object ---> [ Redirected via Brooks Pointer to To-Space Copy ]
```

### Code Example

Below is a demonstration that allocates memory and runs in a loop to trigger GC activity. Running this with Shenandoah GC demonstrates near-zero pause times.

```java
package theory;

import java.util.UUID;

public class ConcurrentGcDemo {
    public static void main(String[] args) {
        System.out.println("Starting allocation loop...");
        long start = System.currentTimeMillis();
        
        // Loop designed to produce continuous garbage to trigger concurrent collection
        for (int i = 0; i < 500_000; i++) {
            String temp = UUID.randomUUID().toString();
            if (i % 100_000 == 0) {
                long now = System.currentTimeMillis();
                System.out.println("Allocated: " + i + " items. Elapsed: " + (now - start) + "ms");
            }
        }
    }
}
/* Output (Run with: java -XX:+UnlockExperimentalVMOptions -XX:+UseShenandoahGC):
Starting allocation loop...
Allocated: 0 items. Elapsed: 0ms
Allocated: 100000 items. Elapsed: 45ms
Allocated: 200000 items. Elapsed: 90ms
Allocated: 300000 items. Elapsed: 135ms
Allocated: 400000 items. Elapsed: 180ms
*/
```

### Cause-Effect Chain

GC selects region for compaction &rarr; GC allocates copy in to-space &rarr; GC performs CAS to update from-space Brooks Pointer to point to to-space copy &rarr; Application threads intercept object reference via load barrier &rarr; Reference redirected to new object copy &rarr; Old region safely reclaimed &rarr; Pause times remain sub-millisecond.

## Reference Links

- https://openjdk.org/jeps/189 (JEP 189: Shenandoah: A Low-Pause-Time Garbage Collector)
