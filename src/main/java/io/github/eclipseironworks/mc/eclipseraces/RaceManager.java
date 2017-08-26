package io.github.eclipseironworks.mc.eclipseraces;

import com.google.common.reflect.TypeToken;
import io.github.eclipseironworks.mc.eclipseraces.Properties.Effects.IRacialEffect;
import io.github.eclipseironworks.mc.eclipseraces.Properties.Effects.RacialEffectActions;
import io.github.eclipseironworks.mc.eclipseraces.Properties.Effects.RacialEffectFactory;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RaceManager
{
    private EclipseRaces plugin;

    private File raceFile;
    private File charFile;
    private ConfigurationNode raceCfg;
    private ConfigurationNode charCfg;
    private ConfigurationLoader<CommentedConfigurationNode> loader;
    private Logger logger;

    private HashMap<String, Race> raceMap = new HashMap<>();
    private HashMap<UUID, ECharacter> characterMap = new HashMap<>();
    private HashMap<String, IRacialEffect> effectMap = new HashMap<>();

    /**
    * This is the constructor, it requires the main class to be passed to it in order to get most of the functionality.
    * */
    public RaceManager(EclipseRaces erp)
    {
         this.plugin = erp;
         this.logger = erp.getLogger();
         this.loader = erp.getConfigManager();

    }

    /**
     * This will check if the config files exist, then load them.  If the files don't exist, it will create them for you.
     */
    public void loadOrCreateConfig()
    {
            raceFile = new File(String.valueOf(plugin.getConfigDir()), "races.conf");
            charFile = new File(String.valueOf(plugin.getConfigDir()), "characters.conf");
            try
            {
                loader = HoconConfigurationLoader.builder().setFile(raceFile).build();
                raceCfg = loader.load();
                if(!raceFile.exists())
                {
                    logger.info("Race File does not exist, making a new one...");
                    buildDefaultRaceCfg();
                } else {
                    loadRaces();
                }
                loader = HoconConfigurationLoader.builder().setFile(charFile).build();
                charCfg = loader.load();
                if(!charFile.exists())
                {
                    logger.info("Character file doesn't exist.");
                }
                else
                {
                    loadCharacters();
                }
            }
            catch (IOException e)
            {
                logger.info(e.getMessage());
            } catch (ObjectMappingException e)
            {
                e.printStackTrace();
            }
    }

    /**
     * This builds the default race config file
     */
    public void buildDefaultRaceCfg()
    {
        IRacialEffect defaultEffect = RacialEffectFactory.builder().key("Mundane").action(RacialEffectActions.NONE).build();


        Race dRace = Race.builder().name(
            Text.of("Human"))
            .effect(defaultEffect)
            .build();  //This creates a default race that has no special effects at all.  Useful for some reason, I don't know, I have a NONE race implemented as the default for players anyways
        raceMap.put(dRace.getRaceName().toPlain().toLowerCase(), dRace); //This adds the race to the raceMap
        effectMap.put(defaultEffect.getKey().toLowerCase(),defaultEffect); //This adds the Mundane effect to the effectMap
        try
        {
            raceCfg.getNode("Races", "Human").setValue(TypeToken.of(Race.class), dRace);  //Attempts to add the new race into the raceCfg
        } catch (ObjectMappingException e)
        {
            logger.info(e.getMessage());
        }
    }

    /**
     * This loads the races from the race file into raceMap
     */
    public void loadRaces()
    {
        Map<Object, ? extends ConfigurationNode> raceNodes = raceCfg.getNode("Races").getChildrenMap();  //This loads all the the nodes in the race config to generate races with
        for(Map.Entry<Object, ? extends ConfigurationNode> entry : raceNodes.entrySet())  //Interates over the new map
        {
            logger.info("Loading a race...");
            if(entry.getKey() instanceof String)  //Checks if it did things right
            {
                ConfigurationNode node = entry.getValue();
                try
                {
                    Race newRace = node.getValue(TypeToken.of(Race.class));  //loads the race from the cfg
                    raceMap.put(newRace.getRaceName().toString().toLowerCase(),newRace); //adds the race to the raceMap

                } catch (ObjectMappingException e)
                {
                    e.printStackTrace();
                }

            }
        }
    }
    public void loadCharacters() throws ObjectMappingException
    {
        ConfigurationNode parent = charCfg.getNode("Characters");
        for(ConfigurationNode node : parent.getChildrenList())
        {
            ECharacter c = (ECharacter) node.getValue(TypeToken.of(ECharacter.class));
            characterMap.put(c.getUUID(), c);
        }
    }

    public void save()
    {
        try
        {
            loader.save(charCfg);
            loader.save(raceCfg);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    /**
     *  This method creates a new character for the player, and asks that they set their race.
     * @param p the player that has just joined the server
     * @return the ECharacter that is created
     */
    public ECharacter newPlayer(Player p)
    {
        p.sendMessage(Text.of("Hello, " + p.getName() + "!  Welcome to Eclipse, please choose a race by doing /chooserace."));
        logger.info("Setting " + p.getName() + "'s race to the default...");

        ECharacter newCharacter = ECharacter.builder().player(p).race(Race.NONE);
        logger.info(p.offer(EKeys.RACE, Race.NONE).getType().name());
        return newCharacter;
    }

    /**
     * This checks whether or not the given Player p has a character already, and makes one if they don't
     * @param p target player
     * @return Either returns the relevant player's character or returns the newly created one.
     */
    public ECharacter getOrCreateCharacter(Player p)
    {

        UUID uuid = p.getUniqueId();
        if(characterMap.containsKey(uuid))
        {
            return characterMap.get(uuid);
        }
        ECharacter newC = newPlayer(p);
        return characterMap.put(uuid, newC);
    }

    /* Returns the race found by the given key in raceMap */

    public Optional<Race> getRace(String i)
    {
        return Optional.ofNullable(this.raceMap.get(i));
    }
    /* Adds a race to the raceMap */
    public void addRace(Race r)
    {
        raceMap.put(r.getRaceName().toPlain(), r);
    }


    /* Adds an effect to the effect map */
    public void addEffect(IRacialEffect e)
    {
        effectMap.put(e.getKey(),e);
    }

    public List<String> getEffectTypes()
    {
        return new ArrayList<>(effectMap.keySet());
    }

    public boolean raceExists(String s)
    {
        return raceMap.containsKey(s);
    }

    public enum RaceChangeReason
    {
        COMMAND, ADMIN_COMMAND, QUEST
    }

    public boolean characterExists(UUID uuid){
     return characterMap.containsKey(uuid);
    }

    public HashMap<String, Race> getRaces()
    {
        return this.raceMap;
    }
    public List<String> getRaceTypes()
    {
        return new ArrayList<>(raceMap.keySet());
    }


}
