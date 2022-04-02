package me.celus.pluginjam.playerdeath;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class AnvilDeath implements PlayerDeath {

    @Override
    public void killPlayer(final Player player) {
        player.getLocation().clone().add(0, 15, 0)
                .getBlock().setType(Material.ANVIL);
    }

}
