package me.celus.pluginjam.listener;

import org.bukkit.entity.Cat;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityBreedEvent;

public class CompanionBreedListener implements Listener {

    @EventHandler
    public void handleBreed(final EntityBreedEvent event) {
        final LivingEntity entity = event.getEntity();
        if (entity instanceof Wolf wolf && wolf.isTamed()) {
            final Cat cat = entity.getWorld().spawn(entity.getLocation(), Cat.class);
            cat.setBaby();
            cat.setOwner(wolf.getOwner());
            entity.remove();
        } else if (entity instanceof Cat cat && cat.isTamed()) {
            final Wolf wolf = entity.getWorld().spawn(entity.getLocation(), Wolf.class);
            wolf.setBaby();
            wolf.setOwner(cat.getOwner());
            entity.remove();
        }
    }

}
