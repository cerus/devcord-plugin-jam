package me.celus.pluginjam.challenge;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Sorry, didn't have time for comments
 */
public class ChallengeSupervisor implements Listener {

    private final Map<Player, Challenge> ongoingChallenges;
    private final Map<Player, ChallengeScoreboard> scoreboards;

    public ChallengeSupervisor() {
        this.ongoingChallenges = new HashMap<>();
        this.scoreboards = new HashMap<>();
    }

    public void updateScoreboards() {
        this.scoreboards.forEach((player, scoreboard) -> scoreboard.update());
    }

    @EventHandler
    public void handlePlayerJoin(final PlayerJoinEvent event) {
        // TODO: Nobody likes hardcoding
        final Challenge challenge = Challenge.random(10);
        challenge.assignPlayer(event.getPlayer());
        this.ongoingChallenges.put(event.getPlayer(), challenge);

        final ChallengeScoreboard scoreboard = ChallengeScoreboard.initialize(event.getPlayer(), challenge);
        this.scoreboards.put(event.getPlayer(), scoreboard);
        scoreboard.update();
    }


    @EventHandler
    public void handlePlayerLeave(final PlayerQuitEvent event) {
        if (this.scoreboards.containsKey(event.getPlayer())) {
            this.scoreboards.get(event.getPlayer()).reset();
            this.scoreboards.remove(event.getPlayer());
        }
        if (this.ongoingChallenges.containsKey(event.getPlayer())) {
            this.ongoingChallenges.get(event.getPlayer()).cancel();
            this.ongoingChallenges.remove(event.getPlayer());
        }
    }

}
