package io.github.eclipseironworks.mc.eclipseraces.commands.element;

import io.github.eclipseironworks.mc.eclipseraces.EclipseRaces;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;
import java.util.List;

public class RaceChoiceElement extends CommandElement
{


    public RaceChoiceElement(@Nullable Text key)
    {
        super(key);
    }

    @Nullable
    @Override
    protected Object parseValue(CommandSource src, CommandArgs args) throws ArgumentParseException
    {
        return args.next();
    }

    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context)
    {
        return EclipseRaces.instance.getRaceManager().getRaceTypes();
    }
}
