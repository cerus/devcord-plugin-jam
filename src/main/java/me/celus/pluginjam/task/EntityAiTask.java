package me.celus.pluginjam.task;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;

public class EntityAiTask implements Runnable {

    @Override
    public void run() {
        for (final World world : Bukkit.getWorlds()) {
            for (final Entity entity : world.getEntities()) {
                if (!(entity instanceof Monster livingEntity)) {
                    continue;
                }

                for (final Player player : Bukkit.getOnlinePlayers()) {
                    final Location clonedLoc = player.getLocation().clone();
                    clonedLoc.setPitch(0f);

                    final float angle = clonedLoc.getDirection()
                            .angle(entity.getLocation().toVector().subtract(player.getLocation().toVector()));
                    final double degrees = Math.toDegrees(angle);
                    this.handleEntityDegrees(livingEntity, degrees);
                }
            }
        }
    }

    private void handleEntityDegrees(final LivingEntity entity, final double degrees) {
        if (degrees < 90 && entity.hasAI()) {
            entity.setAI(false);
        } else if (degrees >= 90 && !entity.hasAI()) {
            entity.setAI(true);
        }
    }

}
