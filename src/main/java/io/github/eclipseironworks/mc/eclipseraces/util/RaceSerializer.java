package io.github.eclipseironworks.mc.eclipseraces.util;

import com.google.common.reflect.TypeToken;
import io.github.eclipseironworks.mc.eclipseraces.Properties.Effects.*;
import io.github.eclipseironworks.mc.eclipseraces.Race;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;


import java.lang.reflect.Field;
import java.util.HashSet;

public class RaceSerializer implements TypeSerializer<Race>
{
    @Override
    public Race deserialize(TypeToken<?> typeToken, ConfigurationNode node) throws ObjectMappingException
    {
        Race race;

        Text name = Text.of(node.getKey());
        int racialID = node.getNode("RacialID").getInt();
        HashSet<IRacialEffect> effects = new HashSet<>();
        boolean hasSpawn;
        Location<World> spawn;

        RacialEffectFactory.Builder builder = RacialEffectFactory.builder();
        //String type = node.getNode("EffectType").getString().toUpperCase();

        if(!node.getNode("Health").isVirtual())
        {
            effects.add(builder.key("Health").action(new RacialHealthModifier(node.getNode("Health", "Modifier").getDouble())).build());
            builder = builder.reset();
        }
        if (!node.getNode("Speed").isVirtual())
        {
            effects.add(builder.key("Speed").action(new CustomKeyAction(Keys.WALKING_SPEED, node.getNode("Speed", "Modifier").getDouble())).build());
            builder=builder.reset();
        }
        if(!node.getNode("Effects").isVirtual())
            if (node.getNode("Effects").hasListChildren())
            {
                for (ConfigurationNode nodel : node.getNode("Effects").getChildrenList())
                {
                    String effectName = nodel.getString();
                    PotionEffect.Builder effect = PotionEffect.builder();
                    try
                    {
                        Field f = PotionEffectType.class.getField(effectName);
                        if(f.get(PotionEffectType.class) instanceof PotionEffectType)
                        {
                            PotionEffectType p = (PotionEffectType) f.get(PotionEffectType.class);
                            effect.potionType(p).duration(9999999).particles(false);
                            builder.key(effectName).action(new CustomEffectAction(effect.build()));
                            effects.add(builder.build());
                            builder=builder.reset();
                        }
                    }
                    catch (NoSuchFieldException e)
                    {
                        e.printStackTrace();
                    }
                    catch (IllegalAccessException e)
                    {
                        e.printStackTrace();
                    }
                }
            }

        if(!node.getNode("Spawn").isVirtual())
        {
            ConfigurationNode nodel = node.getNode("Spawn");
            hasSpawn = true;
            World world = Sponge.getServer().getWorld(nodel.getNode("World").getString()).get();
            spawn = new Location<>(world,
                    nodel.getNode("X").getDouble(),
                    nodel.getNode("Y").getDouble(),
                    nodel.getNode("Z").getDouble());
            race = Race.builder().name(name).racialID(racialID).effects(effects).spawn(spawn).build();
        } else
        {
            race = Race.builder().name(name).racialID(racialID).effects(effects).build();
        }
        return race;
    }

    @Override
    public void serialize(TypeToken<?> typeToken, Race race, ConfigurationNode node) throws ObjectMappingException
    {
        node.getNode("RacialID").setValue(race.getRacialID());
        for(IRacialEffect out : race.getEffects())
        {
            if(out instanceof RacialHealthModifier)
            {
                node.getNode("Health", "Modifier").setValue(((RacialHealthModifier) out).getModifier());
            }

            if(out.getAction() instanceof CustomKeyAction)
            {
                node.getNode(out.getKey(), "Modifier").setValue(((CustomKeyAction)out.getAction()).getValue());
            }

            if(out.getAction() instanceof CustomEffectAction)
            {
                node.getNode("Effects", out.getKey()).setValue(true);
            }
        }

        if(race.hasSpawn())
        {
            ConfigurationNode nodel = node.getNode("Spawn");
            nodel.setValue(true);
            World world = (World) race.getSpawn().getExtent();
            nodel.getNode("World").setValue(world.getName());
            nodel.getNode("X").setValue(race.getSpawn().getX());
            nodel.getNode("Y").setValue(race.getSpawn().getY());
            nodel.getNode("Z").setValue(race.getSpawn().getZ());
        } else {
            node.getNode("Spawn").setValue(false);
        }


    }
}
