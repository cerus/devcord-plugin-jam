package me.celus.pluginjam.playerdeath;

import org.bukkit.entity.Player;

/**
 * Represents a way for a player to die
 */
public interface PlayerDeath {

    /**
     * Kill the specified player
     *
     * @param player The player to kill
     */
    void killPlayer(Player player);

}
