package me.celus.pluginjam.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Supplier;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

public class EntityParticleController {

    private final Map<UUID, Supplier<Particle<?>>> particleMap = new HashMap<>();

    public void playEffects() {
        Map.copyOf(this.particleMap).forEach((uuid, supplier) -> {
            final Entity entity = Bukkit.getEntity(uuid);
            if (entity == null || entity.isDead() || !entity.isValid() || entity.isOnGround()) {
                this.particleMap.remove(uuid);
            } else {
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

    public void add(final Entity entity, final Supplier<Particle<?>> particle) {
        this.particleMap.put(entity.getUniqueId(), particle);
    }

    public void remove(final Entity entity) {
        this.particleMap.remove(entity.getUniqueId());
    }

    public record Particle<T>(org.bukkit.Particle particleEnum, int count, double offsetX, double offsetY, double offsetZ, double extra, T data) {
    }

}
