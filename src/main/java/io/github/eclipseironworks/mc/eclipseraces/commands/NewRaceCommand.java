package io.github.eclipseironworks.mc.eclipseraces.commands;

import io.github.eclipseironworks.mc.eclipseraces.EclipseRaces;
import io.github.eclipseironworks.mc.eclipseraces.Properties.Effects.CustomKeyAction;
import io.github.eclipseironworks.mc.eclipseraces.Properties.Effects.RacialEffectFactory;
import io.github.eclipseironworks.mc.eclipseraces.Properties.Effects.RacialHealthModifier;
import io.github.eclipseironworks.mc.eclipseraces.Race;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.parsing.InputTokenizer;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.annotation.Nullable;
import java.util.*;

public final class NewRaceCommand implements CommandExecutor
{
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        if(args.getOne(Text.of("name")).isPresent())
        {
            Text name = Text.of(args.getOne("name").get());
            if(EclipseRaces.instance.getRaceManager().hasRace(name.toPlain()))
            {
                    src.sendMessage(Text.of("yo, that exists already"));
                return CommandResult.empty();
            }
                EclipseRaces.instance.getRaceManager().addRace(Race.buildEmpty(name));
            return CommandResult.success();
        }
        src.sendMessage(Text.of("You actually have to put a name, dingus."));
        return CommandResult.empty();
    }
}
