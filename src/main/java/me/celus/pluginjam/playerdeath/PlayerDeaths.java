package me.celus.pluginjam.playerdeath;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.entity.Player;

/**
 * A collection of player deaths
 */
public class PlayerDeaths {

    private static final List<PlayerDeath> DEATHS = List.of(
            new FireworkDeath(),
            new AnvilDeath(),
            new DripStoneDeath()
    );

    private PlayerDeaths() {
        throw new UnsupportedOperationException();
    }

    /**
     * Choose a random death and kill the player
     *
     * @param player The player to kill
     */
    public static void killPlayerRandomly(final Player player) {
        final int index = ThreadLocalRandom.current().nextInt(DEATHS.size());
        DEATHS.get(index).killPlayer(player);
    }

}
