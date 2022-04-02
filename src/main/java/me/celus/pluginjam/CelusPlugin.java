package me.celus.pluginjam;

import me.celus.pluginjam.controller.BatPassengerController;
import me.celus.pluginjam.controller.EntityParticleController;
import me.celus.pluginjam.listener.AnimalHitListener;
import me.celus.pluginjam.listener.FishDeathListener;
import me.celus.pluginjam.listener.OreBreakListener;
import me.celus.pluginjam.listener.TreeBreakListener;
import me.celus.pluginjam.task.EntityAiTask;
import me.celus.pluginjam.task.EntityPlayParticleTask;
import me.celus.pluginjam.task.FishLevitateTask;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class CelusPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Create controllers
        final EntityParticleController entityParticleController = new EntityParticleController();
        final BatPassengerController batPassengerController = new BatPassengerController();

        // Register event listeners
        final PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new AnimalHitListener(this, entityParticleController), this);
        pluginManager.registerEvents(new TreeBreakListener(), this);
        pluginManager.registerEvents(new FishDeathListener(batPassengerController), this);
        pluginManager.registerEvents(new OreBreakListener(), this);

        // Schedule tasks
        final BukkitScheduler scheduler = this.getServer().getScheduler();
        scheduler.runTaskTimer(this, new EntityPlayParticleTask(entityParticleController), 0, 1);
        scheduler.runTaskTimer(this, new EntityAiTask(), 0, 1);
        scheduler.runTaskTimer(this, new FishLevitateTask(batPassengerController, entityParticleController), 0, 5);
    }

}
