package io.github.eclipseironworks.mc.eclipseraces.events;

import io.github.eclipseironworks.mc.eclipseraces.Race;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.living.humanoid.player.TargetPlayerEvent;
import org.spongepowered.api.event.impl.AbstractEvent;

public class RaceChangeEvent extends AbstractEvent implements TargetPlayerEvent, Cancellable
{

    private final Player target;
    private final Race newRace;
    private final Race oldRace;
    private final Cause cause;
    private boolean cancelled = false;

    public RaceChangeEvent(Player target, Race newRace, Race oldRace, Cause cause)
    {

        this.target = target;
        this.newRace = newRace;
        this.oldRace = oldRace;
        this.cause = cause;
    }
    @Override
    public boolean isCancelled()
    {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b)
    {
        this.cancelled = b;
    }

    @Override
    public Player getTargetEntity()
    {
        return target;
    }

    @Override
    public Cause getCause()
    {
        return cause;
    }
}
