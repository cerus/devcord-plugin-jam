package me.celus.pluginjam.challenge.task;

import java.util.Set;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityPickupItemEvent;

/**
 * Sorry, didn't have time for comments
 */
public class CollectFleshTask extends Task {

    private static final Set<Material> FLESH_MATERIALS = Set.of(
            Material.PORKCHOP,
            Material.COOKED_PORKCHOP,
            Material.COD,
            Material.COOKED_COD,
            Material.SALMON,
            Material.COOKED_SALMON,
            Material.TROPICAL_FISH,
            Material.PUFFERFISH,
            Material.BEEF,
            Material.COOKED_BEEF,
            Material.CHICKEN,
            Material.COOKED_CHICKEN,
            Material.ROTTEN_FLESH,
            Material.RABBIT,
            Material.COOKED_RABBIT,
            Material.MUTTON,
            Material.COOKED_MUTTON
    );

    private final int requiredAmount;
    private int collectedAmount = 0;

    public CollectFleshTask(final int requiredAmount) {
        this.requiredAmount = requiredAmount;
    }

    @Override
    public String getTitle() {
        return String.format("Sammle Fleisch / Fisch (%d/%d)", this.collectedAmount, this.requiredAmount);
    }

    @Override
    protected void tick() {
    }

    @EventHandler
    public void handleItemPickup(final EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (player != super.getAssignedPlayer()) {
            return;
        }

        if (!FLESH_MATERIALS.contains(event.getItem().getItemStack().getType())) {
            return;
        }

        this.collectedAmount += event.getItem().getItemStack().getAmount();
        if (this.collectedAmount >= this.requiredAmount) {
            this.complete();
        }
    }

}
