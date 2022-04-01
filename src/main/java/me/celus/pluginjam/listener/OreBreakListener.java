package me.celus.pluginjam.listener;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class OreBreakListener implements Listener {

    private static final int TELEPORT_CHANCE = 50;
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

        Location safeLocation;
        int count = 0;
        while ((safeLocation = this.attemptGetLocation(rand, block.getLocation())) == null && count < TELEPORT_TRIES) {
            count++;
        }
        if (safeLocation == null) {
            return;
        }

        final Material oreType = block.getType();
        event.setCancelled(true);
        block.setType(Material.AIR);
        safeLocation.getBlock().setType(oreType);
        block.getWorld().playEffect(block.getLocation(), Effect.ENDER_SIGNAL, null);
    }

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
