package me.celus.pluginjam.listener;

import me.celus.pluginjam.controller.BatPassengerController;
import org.bukkit.entity.Fish;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

/**
 * Removes the bat of a flying fish when they die
 */
public class FishDeathListener implements Listener {

    private final BatPassengerController batPassengerController;

    public FishDeathListener(final BatPassengerController batPassengerController) {
        this.batPassengerController = batPassengerController;
    }

    @EventHandler
    public void handleFishDeath(final EntityDeathEvent event) {
        if (event.getEntity() instanceof Fish fish) {
            this.batPassengerController.fishDied(fish);
        }
    }

}
