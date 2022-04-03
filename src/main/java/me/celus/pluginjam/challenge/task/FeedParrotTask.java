package me.celus.pluginjam.challenge.task;

import org.bukkit.Material;
import org.bukkit.entity.Parrot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class FeedParrotTask extends Task {

    @Override
    public String getTitle() {
        return "Füttere einen Papageien mit einem Keks";
    }

    @Override
    protected void tick() {
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void handleParrotFeed(final PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Parrot)
                || event.getPlayer().getInventory().getItemInMainHand().getType() != Material.COOKIE) {
            return;
        }

        if (!event.getPlayer().getUniqueId().equals(this.getAssignedPlayer().getUniqueId())) {
            return;
        }

        this.complete();
    }

}