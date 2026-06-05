package com.yllwofficial.observermod;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.component.DataComponentTypes;

import java.util.Random;

public class ObserverScareEvents {
    private static final Random RANDOM = new Random();

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                // 1 in 6000 chance per tick (roughly once every 5 minutes per player)
                if (RANDOM.nextInt(6000) == 0) {
                    triggerRandomScare(player);
                }
            }
        });
    }

    private static void triggerRandomScare(ServerPlayerEntity player) {
        // Randomly pick between 3 different scare events
        int scareType = RANDOM.nextInt(3);

        switch (scareType) {
            case 0:
                // SCARE 1: Fake Chat Message
                player.sendMessage(Text.literal("<The Observer> I saw you."), false);
                break;
                
            case 1:
                // SCARE 2: Audio Hallucination (Footsteps right behind the player)
                // We offset the sound coordinates slightly behind the player's current position
                player.getWorld().playSound(
                        null, 
                        player.getX() - 2, player.getY(), player.getZ() - 2, 
                        SoundEvents.BLOCK_GRASS_STEP, 
                        SoundCategory.HOSTILE, 
                        1.0f, 1.0f
                );
                break;
                
            case 2:
                // SCARE 3: Inventory Tampering
                // Scans the inventory and renames the first item it finds
                for (int i = 0; i < player.getInventory().size(); i++) {
                    ItemStack stack = player.getInventory().getStack(i);
                    if (!stack.isEmpty()) {
                        // Minecraft 1.21 uses Data Components instead of NBT for item names
                        stack.set(DataComponentTypes.CUSTOM_NAME, Text.literal("TURN AROUND"));
                        break; // Stop after renaming one item so we don't ruin their whole inventory
                    }
                }
                break;
        }
    }
}
