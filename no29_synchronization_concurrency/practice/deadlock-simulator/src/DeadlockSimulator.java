package no29_synchronization_concurrency.practice.deadlock_simulator;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * Starter template for a Deadlock Simulator and Detector.
 */
public class DeadlockSimulator {

    /**
     * Spawns two threads that lock lock1 and lock2 in opposing orders, causing
     * a deadlock. Then detects the deadlock using JVM APIs.
     * 
     * Requirements:
     * - Create Thread 1: Locks lock1, sleeps briefly (e.g. 50ms), then attempts to lock lock2.
     * - Create Thread 2: Locks lock2, sleeps briefly (e.g. 50ms), then attempts to lock lock1.
     * - Ensure both threads are set as DAEMON threads (thread.setDaemon(true)) so they don't block JVM exit.
     * - Start both threads, sleep for a short time (e.g. 200ms) to ensure deadlock occurs.
     * - Use ThreadMXBean to check if any threads are deadlocked.
     * - Return true if a deadlock is detected, false otherwise.
     *
     * @param lock1 Resource lock A
     * @param lock2 Resource lock B
     * @return true if deadlock detected, false otherwise
     * @throws InterruptedException if interrupted during sleep
     */
    public static boolean runDeadlockCheck(Object lock1, Object lock2) throws InterruptedException {
        // TODO: Implement deadlock simulation and detection using ThreadMXBean
        return false;
    }
}
