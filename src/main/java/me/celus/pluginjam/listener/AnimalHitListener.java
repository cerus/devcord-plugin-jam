package me.celus.pluginjam.listener;

import java.util.concurrent.ThreadLocalRandom;
import me.celus.pluginjam.controller.EntityParticleController;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class AnimalHitListener implements Listener {

    private final JavaPlugin plugin;
    private final EntityParticleController entityParticleController;

    public AnimalHitListener(final JavaPlugin plugin, final EntityParticleController entityParticleController) {
        this.plugin = plugin;
        this.entityParticleController = entityParticleController;
    }

    @EventHandler
    public void handleAnimalHit(final EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        if (!(event.getEntity() instanceof LivingEntity entity)) {
            return;
        }
        if (!(entity.hasAI())) {
            return;
        }

        final ThreadLocalRandom rand = ThreadLocalRandom.current();
        final Vector velocity = new Vector(
                rand.nextInt(4) - 2,
                rand.nextInt(1) + 2,
                rand.nextInt(4) - 2
        );

        Bukkit.getScheduler().runTaskLater(this.plugin, () -> {
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
                            Color.fromRGB(
                                    rand.nextInt(256),
                                    rand.nextInt(256),
                                    rand.nextInt(256)
                            ),
                            1f
                    )
            ));
        }, 1);
    }

}
