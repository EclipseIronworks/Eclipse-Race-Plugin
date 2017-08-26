package io.github.eclipseironworks.mc.eclipseraces.commands;

import io.github.eclipseironworks.mc.eclipseraces.ECharacter;
import io.github.eclipseironworks.mc.eclipseraces.EclipseRaces;
import io.github.eclipseironworks.mc.eclipseraces.Race;
import io.github.eclipseironworks.mc.eclipseraces.RaceManager;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class ChooseRaceCommand implements CommandExecutor
{

    private final Optional<Text> desc = Optional.of(Text.of("Chooses your race."));
    private final Optional<Text> help = Optional.of(Text.of("Chooses your race initially, can only be used once."));
    private final Text usage = Text.of("<race>");
    private RaceManager r = EclipseRaces.instance.getRaceManager();

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException
    {

        if(args.getOne("race").isPresent())
        {
            String raceType = ((String) args.getOne("race").get());
            if (src instanceof Player)
            {
                Player player = (Player) src;


                Player p = ((Player) src);
                ECharacter c = r.getOrCreateCharacter(p);
                if (!c.hasChosenCharacter())
                {
                    c.setRace(r.getRace(raceType), Cause.of(NamedCause.of(NamedCause.SOURCE, RaceManager.RaceChangeReason.COMMAND)));
                    return CommandResult.success();
                } else
                {
                    player.sendMessage((Text.of("Oi, you've done this before, haven't you?")));
                    return CommandResult.empty();
                }


            }
            src.sendMessage(Text.of("Bad."));
        }
        return CommandResult.empty();

    }
}
