package me.celus.pluginjam.challenge;

import me.celus.pluginjam.challenge.task.Task;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Sorry, didn't have time for comments
 */
public class ChallengeScoreboard {

    private static final String OBJECTIVE_NAME = "celus-challenge-scoreboard";

    private final Player player;
    private final Scoreboard scoreboard;
    private final Challenge challenge;

    private ChallengeScoreboard(final Player player, final Scoreboard scoreboard, final Challenge challenge) {
        this.player = player;
        this.scoreboard = scoreboard;
        this.challenge = challenge;
    }

    public static ChallengeScoreboard initialize(final Player player, final Challenge challenge) {
        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        player.setScoreboard(scoreboard);
        return new ChallengeScoreboard(player, scoreboard, challenge);
    }

    public void update() {
        Objective objective = this.scoreboard.getObjective(OBJECTIVE_NAME);
        if (objective != null) {
            objective.unregister();
        }
        objective = this.scoreboard.registerNewObjective(OBJECTIVE_NAME, "dummy", Component.text("Challenge"));
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        int i = 0;
        for (final Task task : this.challenge.getTasks()) {
            final Score score = objective.getScore((task.isCompleted() ? "§a" : "§c") + task.getTitle());
            score.setScore(i);
            i++;
        }
    }

    public void reset() {
        this.player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

}
