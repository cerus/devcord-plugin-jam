package me.celus.pluginjam.challenge.task;

import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class TreeBreakTask extends Task {

    private final int requiredAmount;
    private int minedAmount = 0;

    public TreeBreakTask(final int requiredAmount) {
        this.requiredAmount = requiredAmount;
    }

    @Override
    public String getTitle() {
        return String.format("Baue Holz ab (%d/%d)", this.minedAmount, this.requiredAmount);
    }

    @Override
    protected void tick() {
    }

    @EventHandler
    public void handleBlockBreak(final BlockBreakEvent event) {
        final Block block = event.getBlock();

        if (!Tag.LOGS.isTagged(block.getType())) {
            return;
        }

        if (event.getPlayer() != this.getAssignedPlayer()) {
            return;
        }

        this.minedAmount++;
        if (this.minedAmount >= 10) {
            this.complete();
        }
    }

}