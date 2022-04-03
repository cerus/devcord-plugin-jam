package me.celus.pluginjam.challenge;

import me.celus.pluginjam.challenge.task.CollectFleshTask;
import me.celus.pluginjam.challenge.task.Task;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class Challenge {

    private static final List<Supplier<Task>> AVAILABLE_TASKS = List.of(
            () -> new CollectFleshTask(10) // TODO: Nobody likes hardcoding
    );

    private final List<Task> tasks;
    private boolean isAssigned = false;

    public Challenge(final List<Task> tasks) {
        this.tasks = tasks;
    }

    public static Challenge random(int taskAmount) {
        if (taskAmount > AVAILABLE_TASKS.size()) {
            taskAmount = AVAILABLE_TASKS.size();
        }

        final List<Supplier<Task>> allTasks = new ArrayList<>(AVAILABLE_TASKS);
        Collections.shuffle(allTasks);
        final List<Task> tasks = allTasks.subList(0, taskAmount).stream().map(Supplier::get).toList();

        return new Challenge(tasks);
    }

    public void assignPlayer(final Player player) {
        if (this.isAssigned) {
            return;
        }
        for (final Task task : this.tasks) {
            task.assignPlayer(player);
        }
        this.isAssigned = true;
    }

    public void cancel() {
        for (final Task task : this.tasks) {
            task.cancel();
        }
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

}
