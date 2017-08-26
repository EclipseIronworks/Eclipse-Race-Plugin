package io.github.eclipseironworks.mc.eclipseraces;

        import com.google.common.reflect.TypeToken;
        import com.google.inject.Inject;
        import io.github.eclipseironworks.mc.eclipseraces.commands.ChooseRaceCommand;
        import io.github.eclipseironworks.mc.eclipseraces.commands.NewRaceCommand;
        import io.github.eclipseironworks.mc.eclipseraces.commands.element.EffectChoiceElement;
        import io.github.eclipseironworks.mc.eclipseraces.commands.element.RaceChoiceElement;
        import io.github.eclipseironworks.mc.eclipseraces.util.CharacterSerializer;
        import io.github.eclipseironworks.mc.eclipseraces.util.RaceSerializer;
        import ninja.leaping.configurate.ConfigurationNode;
        import ninja.leaping.configurate.commented.CommentedConfigurationNode;
        import ninja.leaping.configurate.loader.ConfigurationLoader;
        import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
        import org.spongepowered.api.Game;
        import org.spongepowered.api.Sponge;
        import org.spongepowered.api.command.args.CommandElement;
        import org.spongepowered.api.command.args.GenericArguments;
        import org.spongepowered.api.command.spec.CommandSpec;
        import org.spongepowered.api.config.ConfigDir;
        import org.spongepowered.api.config.DefaultConfig;
        import org.spongepowered.api.entity.living.player.Player;
        import org.spongepowered.api.event.Listener;
        import org.spongepowered.api.event.filter.cause.First;
        import org.spongepowered.api.event.game.state.GameInitializationEvent;
        import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
        import org.spongepowered.api.event.game.state.GameStartedServerEvent;
        import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
        import org.spongepowered.api.event.network.ClientConnectionEvent;
        import org.spongepowered.api.plugin.Plugin;
        import org.spongepowered.api.plugin.PluginContainer;
        import org.slf4j.Logger;
        import org.spongepowered.api.service.user.UserStorageService;
        import org.spongepowered.api.text.Text;

        import java.io.File;
        import java.io.IOException;
        import java.nio.file.Path;

@Plugin(id = "eclipseecon", name = "EclipseRaces", version = "0.0.1", description = "Eclipse Ironwork's own racial plugin")
public class EclipseRaces
{

    @Inject @ConfigDir(sharedRoot = false) private Path configDir;

    @Inject private Logger logger;

    @Inject private PluginContainer pluginContainer;


    private UserStorageService userStorageService;

    private RaceManager raceManager;

    @Inject private Game game;

    @Inject @DefaultConfig(sharedRoot = false) private File defaultConfig;

    private ConfigurationNode cfg;

    @Inject @DefaultConfig(sharedRoot = false) private ConfigurationLoader<CommentedConfigurationNode> configManager;

    public static EclipseRaces instance;



    @Listener
    //When the server starts, this runs
    public void onServerInitialize(GamePreInitializationEvent event)
    {
        instance = this;
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(ECharacter.class), new CharacterSerializer());
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(Race.class), new RaceSerializer());
        logger.info("Loaded serializers");
        this.raceManager = new RaceManager(this);
        logger.info("Loaded race manager");


    }
    @Listener
    public void init(GameInitializationEvent event)
    {
        buildCommands();
    }


    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        userStorageService = game.getServiceManager().provideUnchecked(UserStorageService.class);
        logger.info("User Storage Service loaded!");
        raceManager.loadOrCreateConfig();
    }
    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event, @First Player p)
    {
        raceManager.getOrCreateCharacter(p);
    }
    @Listener
    public void onServerStopping(GameStoppingServerEvent event)
    {
        try
        {
            configManager.save(cfg);
            raceManager.save();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void getOrMakeConfig()
    {

    }

    public void buildCommands()
    {

        /*
        *
        *
        * TODO Add more commands, like one that makes a new effect, one that adds and one that removes effects from races
         */
        logger.info("Loading commands");
        CommandSpec chooseRace = CommandSpec.builder()
                .description(Text.of("Choose your race command"))
                .arguments
                (
                        new RaceChoiceElement(Text.of("race"))
                        //GenericArguments.onlyOne(GenericArguments.choices(Text.of("race"), raceManager.getRaces()))
                )
                .executor(new ChooseRaceCommand())
                .build();
        Sponge.getCommandManager().register(this, chooseRace, "chooserace");
        CommandSpec newRace = CommandSpec.builder()
                .description(Text.of("Creates a new race"))
                .arguments
                        (
                        GenericArguments.string(Text.of("name"))
                        )
                .executor(new NewRaceCommand())
                .build();
        Sponge.getCommandManager().register(this,  newRace,"newrace");
        CommandSpec addEffect = CommandSpec.builder()
                .description(Text.of("Add an effect to a racec"))
                .arguments(
                        new RaceChoiceElement(Text.of("race")),
                        new EffectChoiceElement(Text.of("effect"))
                )
                .build();




    }



    //Getter Methods
    public Path getConfigDir()
    {
        return configDir;
    }

    public Logger getLogger()
    {
        return logger;
    }

    public ConfigurationLoader<CommentedConfigurationNode> getConfigManager()
    {
        return configManager;
    }

    public UserStorageService getUserStorageService()
    {
        return userStorageService;
    }

    public PluginContainer getPluginContainer()
    {
        return pluginContainer;
    }


    public RaceManager getRaceManager()
    {
        return raceManager;
    }

}
