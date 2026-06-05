package com.yllwofficial.observermod;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObserverMod implements ModInitializer {
    public static final String MOD_ID = "observer_mod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("The Observer is watching...");
        // Initialize our custom scare tick handler
        ObserverScareEvents.register();
    }
}
