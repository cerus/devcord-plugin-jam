package me.celus.pluginjam.event;

import org.bukkit.entity.Creeper;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a creeper gets ignited
 */
public class CreeperFuseEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Creeper entity;

    public CreeperFuseEvent(final Creeper entity) {
        this.entity = entity;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public Creeper getEntity() {
        return this.entity;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

}
