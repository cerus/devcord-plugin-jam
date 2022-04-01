package me.celus.pluginjam.task;

import me.celus.pluginjam.controller.BatPassengerController;
import me.celus.pluginjam.controller.EntityParticleController;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fish;

public class FishLevitateTask implements Runnable {

    private final BatPassengerController batPassengerController;
    private final EntityParticleController entityParticleController;

    public FishLevitateTask(final BatPassengerController batPassengerController, final EntityParticleController entityParticleController) {
        this.batPassengerController = batPassengerController;
        this.entityParticleController = entityParticleController;
    }

    @Override
    public void run() {
        for (final World world : Bukkit.getWorlds()) {
            for (final Entity entity : world.getEntities()) {
                if (!(entity instanceof Fish fish)) {
                    continue;
                }
                if (fish.isInWater()) {
                    continue;
                }
                if (fish.isInsideVehicle()) {
                    continue;
                }

                this.spawnBat(world, fish);
            }
        }
    }

    private void spawnBat(final World world, final Fish fish) {
        final Bat bat = world.spawn(fish.getLocation(), Bat.class);
        bat.setInvisible(true);
        bat.setSilent(true);
        bat.addPassenger(fish);
        this.batPassengerController.add(bat);
        this.entityParticleController.add(
                fish,
                () -> new EntityParticleController.Particle<>(
                        Particle.CAMPFIRE_COSY_SMOKE,
                        1,
                        0,
                        0,
                        0,
                        0,
                        null
                )
        );
    }

}
