package me.celus.pluginjam.challenge.task;

import me.celus.pluginjam.CelusPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * Sorry, didn't have time for comments
 */
public abstract class Task implements Listener {

    private Player assignedPlayer = null;
    private BukkitTask tickTask = null;

    private boolean completed = false;
    private Runnable onComplete = null;

    /**
     * Assigns a player to this task and thus starts it
     *
     * @param player The player to assign this task to
     */
    public void assignPlayer(final Player player) {
        if (this.assignedPlayer != null) {
            return;
        }
        this.assignedPlayer = player;

        final JavaPlugin plugin = JavaPlugin.getPlugin(CelusPlugin.class);
        this.tickTask = new BukkitRunnable() {
            @Override
            public void run() {
                Task.this.tick();
            }
        }.runTaskTimerAsynchronously(plugin, 0, 10);
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public Player getAssignedPlayer() {
        return this.assignedPlayer;
    }

    public boolean isCompleted() {
        return this.completed;
    }

    /**
     * Registers a callback which gets called when this task gets completed.
     * The task object should be disposed after it is marked as completed.
     *
     * @param callback The callback to call when the task is completed
     */
    public void doOnComplete(final Runnable callback) {
        this.onComplete = callback;
    }

    /**
     * Cancels this task.
     * The task object should be disposed after calling this method.
     */
    public void cancel() {
        if (this.tickTask != null) {
            this.tickTask.cancel();
            this.tickTask = null;
        }
        HandlerList.unregisterAll(this);
    }

    /**
     * @return The title to be shown in the scoreboard for this task
     */
    public abstract String getTitle();

    protected void complete() {
        if (this.completed) {
            return;
        }

        this.cancel();

        this.completed = true;
        if (this.onComplete != null) {
            Bukkit.getScheduler().callSyncMethod(JavaPlugin.getPlugin(CelusPlugin.class), () -> {
                this.onComplete.run();
                return null;
            });
        }
    }

    /**
     * Gets called asynchronously every 10 server ticks while the task is running
     */
    protected abstract void tick();

}
