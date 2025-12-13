package net.hazedf.optimizer.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadOptimizer {
    private static final Logger LOGGER = LoggerFactory.getLogger("HazedfOptimizer");

    public static void optimize() {
        LOGGER.info("Optimizing Thread Priorities...");
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        LOGGER.info("Main thread priority set to MAX.");

        String os = System.getProperty("os.name").toLowerCase();
        String cpu = System.getenv("PROCESSOR_IDENTIFIER");
        if (cpu == null)
            cpu = "";
        cpu = cpu.toLowerCase();

        if (cpu.contains("amd") || cpu.contains("authenticamd")) {
            LOGGER.info("AMD CPU Detected. Java's G1GC is generally good, but ensure you are using a modern JDK.");
        } else if (cpu.contains("intel")) {
            LOGGER.info("Intel CPU Detected.");
        }
    }
}
