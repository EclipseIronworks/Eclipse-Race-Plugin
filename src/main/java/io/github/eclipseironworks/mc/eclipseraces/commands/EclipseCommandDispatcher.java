package io.github.eclipseironworks.mc.eclipseraces.commands;

import com.google.common.collect.Multimap;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandMapping;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.dispatcher.Dispatcher;
import org.spongepowered.api.command.dispatcher.SimpleDispatcher;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class EclipseCommandDispatcher implements Dispatcher
{
    @Override
    public Set<? extends CommandMapping> getCommands()
    {
        return null;
    }

    @Override
    public Set<String> getPrimaryAliases()
    {
        return null;
    }

    @Override
    public Set<String> getAliases()
    {
        return null;
    }

    @Override
    public Optional<? extends CommandMapping> get(String s)
    {
        return null;
    }

    @Override
    public Optional<? extends CommandMapping> get(String s, @Nullable CommandSource commandSource)
    {
        return null;
    }

    @Override
    public Set<? extends CommandMapping> getAll(String s)
    {
        return null;
    }

    @Override
    public Multimap<String, CommandMapping> getAll()
    {
        return null;
    }

    @Override
    public boolean containsAlias(String s)
    {
        return false;
    }

    @Override
    public boolean containsMapping(CommandMapping commandMapping)
    {
        return false;
    }

    @Override
    public CommandResult process(CommandSource commandSource, String s) throws CommandException
    {
        return null;
    }

    @Override
    public List<String> getSuggestions(CommandSource commandSource, String s, @Nullable Location<World> location) throws CommandException
    {
        return null;
    }

    @Override
    public boolean testPermission(CommandSource commandSource)
    {
        return false;
    }

    @Override
    public Optional<Text> getShortDescription(CommandSource commandSource)
    {
        return null;
    }

    @Override
    public Optional<Text> getHelp(CommandSource commandSource)
    {
        return null;
    }

    @Override
    public Text getUsage(CommandSource commandSource)
    {
        return null;
    }
}
