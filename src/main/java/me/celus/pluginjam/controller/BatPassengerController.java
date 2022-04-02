package me.celus.pluginjam.controller;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Fish;

/**
 * Controls flying fish bat removal
 */
public class BatPassengerController {

    private final Set<UUID> bats = new HashSet<>();

    /**
     * Removes the vehicle (bat) of a fish when it dies
     */
    public void fishDied(final Fish fish) {
        if (!fish.isInsideVehicle()) {
            return;
        }
        final Entity vehicle = fish.getVehicle();
        if (!(vehicle instanceof Bat bat)) {
            return;
        }

        if (this.bats.contains(bat.getUniqueId())) {
            this.bats.remove(bat.getUniqueId());
            bat.remove();
        }
    }

    /**
     * Register a bat
     *
     * @param bat The bat to register
     */
    public void add(final Bat bat) {
        this.bats.add(bat.getUniqueId());
    }

}
