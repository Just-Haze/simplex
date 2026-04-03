package net.hazedf.optimizer;

import net.hazedf.optimizer.core.ThreadOptimizer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HazedfOptimizerMod implements ModInitializer {
	public static final String MOD_ID = "simplex";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Simplex for Fabric 1.21.x");
		net.hazedf.optimizer.config.OptimizerConfig.load();

		ThreadOptimizer.optimize();
	}
}
