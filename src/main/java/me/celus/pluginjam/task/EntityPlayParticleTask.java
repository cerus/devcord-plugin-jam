package me.celus.pluginjam.task;

import me.celus.pluginjam.controller.EntityParticleController;

/**
 * Controls entity particle effects
 */
public class EntityPlayParticleTask implements Runnable {

    private final EntityParticleController entityParticleController;

    public EntityPlayParticleTask(final EntityParticleController entityParticleController) {
        this.entityParticleController = entityParticleController;
    }

    @Override
    public void run() {
        this.entityParticleController.playEffects();
    }

}
