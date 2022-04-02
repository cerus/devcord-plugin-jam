package me.celus.pluginjam.listener;

import java.util.concurrent.ThreadLocalRandom;
import me.celus.pluginjam.event.CreeperFuseEvent;
import org.bukkit.Location;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

/**
 * Spawns tnt when a creeper is ignited
 */
public class CreeperIgniteListener implements Listener {

    private static final int TNT_SPAWN_CHANCE = 20;
    private static final int TNT_FUSE_TICKS = 2 * 20;

    @EventHandler
    public void handleCreeperIgnite(final CreeperFuseEvent event) {
        final ThreadLocalRandom rand = ThreadLocalRandom.current();
        if (rand.nextInt(100) + 1 > TNT_SPAWN_CHANCE) {
            return;
        }

        // Spawn tnt
        final Location location = event.getEntity().getLocation();
        event.getEntity().remove();
        final TNTPrimed tnt = location.getWorld().spawn(location, TNTPrimed.class);
        tnt.setFuseTicks(TNT_FUSE_TICKS);
    }

}
