package com.yllwofficial.observermod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.text.Text;

import java.util.Random;

public class ObserverClient implements ClientModInitializer {
    private static final Random RANDOM = new Random();
    private int flashTimer = 0;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            // Very rare chance to trigger the fake death flash (client-side)
            if (client.player != null && RANDOM.nextInt(10000) == 0) {
                if (flashTimer == 0) {
                    triggerFakeDeath(client);
                }
            }

            // If the fake death screen is active, count down and then close it
            if (flashTimer > 0) {
                flashTimer--;
                if (flashTimer == 0) {
                    client.setScreen(null); // Closes the screen
                }
            }
        });
    }

    private void triggerFakeDeath(MinecraftClient client) {
        // Opens the standard Minecraft Death Screen
        client.setScreen(new DeathScreen(Text.literal("You shouldn't be here."), false));
        // Keeps the screen open for exactly 5 ticks (1/4th of a second) for a quick jumpscare flash
        flashTimer = 5; 
    }
}
