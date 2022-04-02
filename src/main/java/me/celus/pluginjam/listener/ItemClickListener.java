package me.celus.pluginjam.listener;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * "Teleports" items away when clicked
 */
public class ItemClickListener implements Listener {

    private static final int TELEPORT_CHANCE = 25;

    @EventHandler
    public void onItemClick(final InventoryClickEvent event) {
        if (!(event.getClickedInventory() instanceof PlayerInventory inv)
                || (event.getAction() != InventoryAction.PICKUP_ALL
                && event.getAction() != InventoryAction.SWAP_WITH_CURSOR)
                || event.getCurrentItem() == null
                || event.getCurrentItem().getType() == Material.AIR) {
            return;
        }

        final ThreadLocalRandom rand = ThreadLocalRandom.current();
        if (rand.nextInt(100) + 1 > TELEPORT_CHANCE) {
            return;
        }

        // Get free slots
        final int[] freeSlots = this.getFreeSlots(inv);
        if (freeSlots.length == 0) {
            // No free slot available, abort
            return;
        }
        final int randomSlot = freeSlots[rand.nextInt(freeSlots.length)];

        // Teleport item
        final ItemStack itemToTeleport = event.getCurrentItem();
        event.setCancelled(true);
        inv.setItem(event.getSlot(), null);
        inv.setItem(randomSlot, itemToTeleport);

        // Play sound
        final Player actor = (Player) event.getWhoClicked();
        actor.playSound(actor.getLocation(), Sound.ENTITY_CHICKEN_EGG, 1f, 1f);
    }

    /**
     * Get all the free slots of an inventory
     *
     * @param inv The inventory to check
     *
     * @return All free slots
     */
    private int[] getFreeSlots(final Inventory inv) {
        final Set<Integer> slots = new HashSet<>();
        for (int i = 0; i < inv.getSize(); i++) {
            final ItemStack item = inv.getItem(i);
            if (item == null || item.getType() == Material.AIR) {
                slots.add(i);
            }
        }
        return slots.stream()
                .mapToInt(value -> value)
                .toArray();
    }

}
