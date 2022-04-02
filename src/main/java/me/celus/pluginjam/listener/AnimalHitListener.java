package me.celus.pluginjam.listener;

import java.util.concurrent.ThreadLocalRandom;
import me.celus.pluginjam.controller.EntityParticleController;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Animals;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

/**
 * Launches animals into the air
 */
public class AnimalHitListener implements Listener {

    private static final int X_RADIUS = 4;
    private static final int Y_BOUNDS = 2;
    private static final int Z_RADIUS = 4;

    private final JavaPlugin plugin;
    private final EntityParticleController entityParticleController;

    public AnimalHitListener(final JavaPlugin plugin, final EntityParticleController entityParticleController) {
        this.plugin = plugin;
        this.entityParticleController = entityParticleController;
    }

    @EventHandler
    public void handleAnimalHit(final EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)
                || !(event.getEntity() instanceof Animals entity)
                || !(entity.hasAI())) {
            return;
        }

        // Calculate a random velocity
        final ThreadLocalRandom rand = ThreadLocalRandom.current();
        final Vector velocity = new Vector(
                rand.nextInt(X_RADIUS) - X_RADIUS / 2,
                rand.nextInt(Y_BOUNDS / 2) + Y_BOUNDS,
                rand.nextInt(Z_RADIUS) - Z_RADIUS / 2
        );

        Bukkit.getScheduler().runTaskLater(this.plugin, () ->
                this.setVelocityAndPlayEffects(entity, velocity), 1);
    }

    /**
     * Launch an entity into the air and play effects
     *
     * @param entity   The entity to launch
     * @param velocity The velocity to launch the entity at
     */
    private void setVelocityAndPlayEffects(final LivingEntity entity, final Vector velocity) {
        entity.setVelocity(velocity);
        entity.getWorld().playSound(
                entity.getLocation(),
                Sound.ENTITY_FIREWORK_ROCKET_LAUNCH,
                1f,
                1f
        );
        this.entityParticleController.add(entity, () -> new EntityParticleController.Particle<>(
                Particle.REDSTONE,
                5,
                0d,
                0d,
                0d,
                0d,
                new Particle.DustOptions(
                        this.getRandomColor(),
                        1f
                )
        ));
    }

    /**
     * Get a random color
     *
     * @return A random color
     */
    private Color getRandomColor() {
        final ThreadLocalRandom rand = ThreadLocalRandom.current();
        return Color.fromRGB(
                rand.nextInt(256),
                rand.nextInt(256),
                rand.nextInt(256)
        );
    }

}
