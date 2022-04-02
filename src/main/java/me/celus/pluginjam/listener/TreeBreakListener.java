package me.celus.pluginjam.listener;

import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Makes wood blocks scream when broken
 */
public class TreeBreakListener implements Listener {

    private static final Sound[] SCREAM_SOUNDS = new Sound[] {
            Sound.ENTITY_HORSE_DEATH,
            Sound.ENTITY_GOAT_SCREAMING_DEATH,
            Sound.ENTITY_GOAT_SCREAMING_HURT
    };

    @EventHandler
    public void handleBlockBreak(final BlockBreakEvent event) {
        final Block block = event.getBlock();
        // Check if block is a log
        if (Tag.LOGS.isTagged(block.getType())) {
            // Play sounds and effect
            block.getWorld().playSound(
                    block.getLocation(),
                    this.getRandomSound(),
                    1f,
                    0.8f + (ThreadLocalRandom.current().nextFloat() * 0.4f) // 0.8 - 1.2
            );
            block.getWorld().playEffect(
                    block.getLocation(),
                    Effect.STEP_SOUND,
                    Material.RED_GLAZED_TERRACOTTA
            );
        }
    }

    /**
     * Get a random scream sound
     *
     * @return A random sound
     */
    private Sound getRandomSound() {
        return SCREAM_SOUNDS[ThreadLocalRandom.current().nextInt(SCREAM_SOUNDS.length)];
    }

}
