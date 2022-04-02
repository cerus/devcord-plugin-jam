package me.celus.pluginjam.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

/**
 * Makes monsters stand still when a player looks at them
 */
public class EntityAiTask implements Runnable {

    @Override
    public void run() {
        // Loop through worlds, entities and players
        for (final World world : Bukkit.getWorlds()) {
            for (final Entity entity : world.getEntities()) {
                // Only check for monsters
                if (!(entity instanceof Monster livingEntity)) {
                    continue;
                }

                for (final Player player : Bukkit.getOnlinePlayers()) {
                    final Location clonedLoc = player.getLocation().clone();
                    clonedLoc.setPitch(0f);

                    // Calculate angle
                    final float angle = clonedLoc.getDirection()
                            .angle(entity.getLocation().toVector().subtract(player.getLocation().toVector()));
                    final double degrees = Math.toDegrees(angle);
                    this.handleEntityDegrees(livingEntity, degrees);
                }
            }
        }
    }

    /**
     * Controls whether an entity can move or not
     * <p>
     * If the degrees are < 90 the entity will not be able to move.
     * <p>
     * If the degrees are >= 90 the entity will be able to move.
     *
     * @param entity  The entity
     * @param degrees The degrees between the entity's location and player's direction
     */
    private void handleEntityDegrees(final LivingEntity entity, final double degrees) {
        if (degrees < 90 && entity.hasAI()) {
            entity.setAI(false);
        } else if (degrees >= 90 && !entity.hasAI()) {
            entity.setAI(true);
        }
    }

}
