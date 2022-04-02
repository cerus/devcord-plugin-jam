package me.celus.pluginjam.listener;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * Randomly teleports ores when they are broken
 */
public class OreBreakListener implements Listener {

    private static final int TELEPORT_CHANCE = 30;
    private static final int TELEPORT_TRIES = 50;
    private static final double TELEPORT_RADIUS = 7.5;

    @EventHandler
    public void handleOreBreak(final BlockBreakEvent event) {
        final Block block = event.getBlock();
        if (!block.getType().name().contains("_ORE")) {
            return;
        }

        final ThreadLocalRandom rand = ThreadLocalRandom.current();
        if (rand.nextInt(100) + 1 > TELEPORT_CHANCE) {
            return;
        }

        // Attempt to get a safe teleport location
        Location safeLocation;
        int count = 0;
        while ((safeLocation = this.attemptGetLocation(rand, block.getLocation())) == null
                && count < TELEPORT_TRIES) {
            count++;
        }
        if (safeLocation == null) {
            // No safe location was found, abort
            return;
        }

        // "Teleport" the block
        final Material oreType = block.getType();
        event.setCancelled(true);
        block.setType(Material.AIR);
        safeLocation.getBlock().setType(oreType);

        // Spawn particles and effects
        final World world = block.getWorld();
        world.playEffect(block.getLocation(), Effect.ENDER_SIGNAL, null);
        world.spawnParticle(
                Particle.DRAGON_BREATH,
                block.getLocation()
                        .clone().add(0.5, 0.5, 0.5),
                15,
                0.3f,
                0.3f,
                0.3f,
                0
        );
        world.playSound(
                block.getLocation(),
                Sound.ENTITY_ENDERMAN_TELEPORT,
                1f,
                1f
        );
    }

    /**
     * Attempts to find a safe location for block teleports
     *
     * @param random           The random to use
     * @param startingLocation The starting location
     *
     * @return A safe location or null
     */
    private Location attemptGetLocation(final Random random, final Location startingLocation) {
        final Location clonedLoc = startingLocation.clone();
        clonedLoc.setX(clonedLoc.getX() + random.nextDouble() * (TELEPORT_RADIUS * 2) - TELEPORT_RADIUS);
        clonedLoc.setY(clonedLoc.getY() + random.nextDouble() * (TELEPORT_RADIUS * 2) - TELEPORT_RADIUS);
        clonedLoc.setZ(clonedLoc.getZ() + random.nextDouble() * (TELEPORT_RADIUS * 2) - TELEPORT_RADIUS);

        return clonedLoc.getBlock().getType() == Material.AIR
                && clonedLoc.getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR
                && clonedLoc.getBlock().getRelative(BlockFace.DOWN).getType().isSolid()
                ? clonedLoc : null;
    }

}
