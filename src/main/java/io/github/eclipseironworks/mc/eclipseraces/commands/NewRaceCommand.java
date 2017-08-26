package io.github.eclipseironworks.mc.eclipseraces.commands;

import io.github.eclipseironworks.mc.eclipseraces.EclipseRaces;
import io.github.eclipseironworks.mc.eclipseraces.Race;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;

public final class NewRaceCommand implements CommandExecutor
{
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {
        if(args.getOne(Text.of("name")).isPresent())
        {
            Text name = Text.of(args.getOne("name").get());
            if(EclipseRaces.instance.getRaceManager().raceExists(name.toPlain()))
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
