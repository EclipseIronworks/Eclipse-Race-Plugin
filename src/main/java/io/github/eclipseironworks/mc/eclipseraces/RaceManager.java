package io.github.eclipseironworks.mc.eclipseraces;

import com.google.common.reflect.TypeToken;
import io.github.eclipseironworks.mc.eclipseraces.Properties.Effects.*;
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

    public RaceManager(EclipseRaces erp)
    {
         this.plugin = erp;
         this.logger = erp.getLogger();
         this.loader = erp.getConfigManager();

    }
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


    public void buildDefaultRaceCfg()
    {
        IRacialEffect defaultEffect = RacialEffectFactory.builder().key("Mundane").action(RacialEffectActions.NONE).build();


        Race dRace = Race.builder().name(
            Text.of("Human"))
            .effect(defaultEffect)
            .build();
        raceMap.put(dRace.getRaceName().toPlain().toLowerCase(), dRace);
        effectMap.put(defaultEffect.getKey().toLowerCase(),defaultEffect);
        try
        {
            raceCfg.getNode("Races", "Human").setValue(TypeToken.of(Race.class), dRace);
        } catch (ObjectMappingException e)
        {
            logger.info(e.getMessage());
        }
    }
    public void loadRaces()
    {
        Map<Object, ? extends ConfigurationNode> raceNodes = raceCfg.getNode("Races").getChildrenMap();
        for(Map.Entry<Object, ? extends ConfigurationNode> entry : raceNodes.entrySet())
        {
            logger.info("Loading a race...");
            if(entry.getKey() instanceof String)
            {
                ConfigurationNode node = entry.getValue();
                try
                {
                    Race newRace = node.getValue(TypeToken.of(Race.class));
                    raceMap.put(newRace.getRaceName().toString().toLowerCase(),newRace);

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

    public ECharacter newPlayer(Player p)
    {
        p.sendMessage(Text.of("Hello, " + p.getName() + "!  Welcome to Eclipse, please choose a race by doing /chooserace."));
        logger.info("Setting " + p.getName() + "'s race to the default...");

        ECharacter newCharacter = ECharacter.builder().player(p).race(Race.NONE);
        logger.info(p.offer(EKeys.RACE, Race.NONE).getType().name());
        return newCharacter;
    }

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

    public Race getRace(String i)
    {
        return this.raceMap.get(i);
    }

    public void addRace(Race r)
    {
        raceMap.put(r.getRaceName().toPlain(), r);
    }

    public void addEffect(IRacialEffect e)
    {
        effectMap.put(e.getKey(),e);
    }

    public List<String> getEffectTypes()
    {
        return new ArrayList<>(effectMap.keySet());
    }

    public boolean hasRace(String s)
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
