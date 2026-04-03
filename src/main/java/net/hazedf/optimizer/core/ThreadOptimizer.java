package net.hazedf.optimizer.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadOptimizer {
    private static final Logger LOGGER = LoggerFactory.getLogger("Simplex");

    public static void optimize() {
        String cpu = System.getenv("PROCESSOR_IDENTIFIER");
        if (cpu == null) {
            cpu = "";
        }
        cpu = cpu.toLowerCase();

        if (cpu.contains("amd") || cpu.contains("authenticamd")) {
            LOGGER.info("AMD CPU detected. Using safe scheduler defaults.");
        } else if (cpu.contains("intel")) {
            LOGGER.info("Intel CPU detected. Using safe scheduler defaults.");
        } else {
            LOGGER.info("CPU vendor not detected. Using safe scheduler defaults.");
        }
    }
}
