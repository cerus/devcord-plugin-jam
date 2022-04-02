package me.celus.pluginjam.listener;

import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

/**
 * Swaps hands when a tool is used
 */
public class ToolUseListener implements Listener {

    private static final int SWAP_CHANCE = 25;

    @EventHandler
    public void handleToolUse(final PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_BLOCK
                || event.getHand() != EquipmentSlot.HAND
                || !this.isTool(event.getPlayer().getInventory().getItemInMainHand())) {
            return;
        }

        final ThreadLocalRandom rand = ThreadLocalRandom.current();
        if (rand.nextInt(100) + 1 > SWAP_CHANCE) {
            return;
        }

        // Get items
        final Player player = event.getPlayer();
        final PlayerInventory inventory = player.getInventory();
        final ItemStack itemInMainHand = inventory.getItemInMainHand();
        final ItemStack itemInOffHand = inventory.getItemInOffHand();

        // Swap items
        inventory.setItemInMainHand(itemInOffHand);
        inventory.setItemInOffHand(itemInMainHand);

        // Play sound
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1f, 1f);
    }

    /**
     * Check if an item is a tool
     *
     * @param item The item to check
     *
     * @return True or false
     */
    private boolean isTool(final ItemStack item) {
        return item != null && this.isTool(item.getType());
    }

    /**
     * Check if a material is a tool
     *
     * @param material The item type to check
     *
     * @return True or false
     */
    private boolean isTool(final Material material) {
        final String matStr = material.name();
        return matStr.contains("_PICKAXE") || matStr.contains("_AXE")
                || matStr.contains("_SHOVEL") || matStr.contains("_HOE");
    }

}
