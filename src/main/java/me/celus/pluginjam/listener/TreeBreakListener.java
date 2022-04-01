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

public class TreeBreakListener implements Listener {

    @EventHandler
    public void handleBlockBreak(final BlockBreakEvent event) {
        final Block block = event.getBlock();
        if (Tag.LOGS.isTagged(block.getType())) {
            block.getWorld().playSound(
                    block.getLocation(),
                    this.getRandomSound(),
                    1f,
                    0.8f + (ThreadLocalRandom.current().nextFloat() * 0.4f)
            );
            block.getWorld().playEffect(
                    block.getLocation(),
                    Effect.STEP_SOUND,
                    Material.NETHER_WART_BLOCK
            );
        }
    }

    private Sound getRandomSound() {
        final Sound[] sounds = new Sound[] {Sound.ENTITY_HORSE_DEATH, Sound.ENTITY_GOAT_SCREAMING_DEATH, Sound.ENTITY_GOAT_SCREAMING_HURT};
        return sounds[ThreadLocalRandom.current().nextInt(sounds.length)];
    }

}
