package me.celus.pluginjam.task;

import me.celus.pluginjam.event.CreeperFuseEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

/**
 * Checks if creepers are ignited in order to call the fuse event
 */
public class CreeperTask implements Runnable {

    private static final String CREEPER_FUSE_META = "celus_creeper_fuse";

    private final Plugin plugin;

    public CreeperTask(final Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (final World world : Bukkit.getWorlds()) {
            for (final Entity entity : world.getEntities()) {
                if (!(entity instanceof Creeper creeper)) {
                    continue;
                }

                // Get ignited meta
                final boolean ignited = creeper.getMetadata(CREEPER_FUSE_META).stream()
                        .map(MetadataValue::asBoolean)
                        .findAny()
                        .orElse(false);
                if (creeper.getFuseTicks() > 0 && !ignited) {
                    // Call event
                    Bukkit.getPluginManager().callEvent(new CreeperFuseEvent(creeper));
                    creeper.setMetadata(CREEPER_FUSE_META, new FixedMetadataValue(this.plugin, true));
                }
            }
        }
    }

}
