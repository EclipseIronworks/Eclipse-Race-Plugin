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

public class EffectChoiceElement extends CommandElement
{
    public EffectChoiceElement(@Nullable Text key)
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
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext commandContext)
    {
        return EclipseRaces.instance.getRaceManager().getEffectTypes();
    }
}
