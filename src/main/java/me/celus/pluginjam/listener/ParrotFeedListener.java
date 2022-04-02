package me.celus.pluginjam.listener;

import me.celus.pluginjam.playerdeath.PlayerDeaths;
import org.bukkit.Material;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Kills a player when they attempt to feed a cookie to a parrot
 */
public class ParrotFeedListener implements Listener {

    @EventHandler
    public void handleParrotFeed(final PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Parrot)
                || event.getPlayer().getInventory().getItemInMainHand().getType() != Material.COOKIE) {
            return;
        }

        event.setCancelled(true);

        // Take cookie away and kill player
        final Player player = event.getPlayer();
        this.decrementItem(player);
        PlayerDeaths.killPlayerRandomly(player);
    }

    /**
     * Decrement the currently selected item
     *
     * @param player The player
     */
    private void decrementItem(final Player player) {
        final PlayerInventory inventory = player.getInventory();
        final ItemStack itemInMainHand = inventory.getItemInMainHand();

        if (itemInMainHand.getAmount() <= 1) {
            inventory.setItemInMainHand(null);
        } else {
            itemInMainHand.setAmount(itemInMainHand.getAmount() - 1);
        }
    }

}
