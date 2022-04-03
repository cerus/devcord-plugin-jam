package me.celus.pluginjam.challenge.task;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class MineOreTask extends Task {

    private final int requiredAmount;
    private int minedAmount = 0;

    public MineOreTask(final int requiredAmount) {
        this.requiredAmount = requiredAmount;
    }

    @Override
    public String getTitle() {
        return String.format("Baue Erz ab (%d/%d)", this.minedAmount, this.requiredAmount);
    }

    @Override
    protected void tick() {
    }

    @EventHandler
    public void handleOreBreak(final BlockBreakEvent event) {
        final Block block = event.getBlock();
        if (!block.getType().name().contains("_ORE")) {
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