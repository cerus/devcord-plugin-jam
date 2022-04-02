package me.celus.pluginjam.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

/**
 * Controls entity particle effects.
 */
public class EntityParticleController {

    private final Map<UUID, Supplier<Particle<?>>> particleMap = new HashMap<>();

    /**
     * Plays entity effects
     */
    public void playEffects() {
        // Iterate through map
        Map.copyOf(this.particleMap).forEach((uuid, supplier) -> {
            final Entity entity = Bukkit.getEntity(uuid);
            if (entity == null || entity.isDead() || !entity.isValid() || entity.isOnGround()) {
                // If entity is not alive / invalid / on ground, remove from map
                this.particleMap.remove(uuid);
            } else {
                // Else, spawn particles
                final Particle<?> particle = supplier.get();
                entity.getWorld().spawnParticle(particle.particleEnum,
                        entity.getLocation(),
                        particle.count,
                        particle.offsetX,
                        particle.offsetY,
                        particle.offsetZ,
                        particle.extra,
                        particle.data);
            }
        });
    }

    /**
     * Register an entity particle effect
     *
     * @param entity   The entity where the particles will be spawned
     * @param particle The particle that should be spawned
     */
    public void add(final Entity entity, final Supplier<Particle<?>> particle) {
        this.particleMap.put(entity.getUniqueId(), particle);
    }

    /**
     * Unregister an entity particle effect
     *
     * @param entity The entity to unregister
     */
    public void remove(final Entity entity) {
        this.particleMap.remove(entity.getUniqueId());
    }

    /**
     * A simple particle data class
     *
     * @param particleEnum The particle type
     * @param count        The amount of particles to spawn
     * @param offsetX      Offset on the x-axis
     * @param offsetY      Offset on the y-axis
     * @param offsetZ      Offset on the z-axis
     * @param extra        Some extra data, usually used for particle speed
     * @param data         Particle data
     * @param <T>          The type of particle data
     */
    public record Particle<T>(
            org.bukkit.Particle particleEnum,
            int count,
            double offsetX,
            double offsetY,
            double offsetZ,
            double extra,
            T data
    ) {}

}
