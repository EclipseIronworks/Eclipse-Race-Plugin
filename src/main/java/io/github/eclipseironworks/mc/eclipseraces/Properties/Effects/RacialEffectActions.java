package io.github.eclipseironworks.mc.eclipseraces.Properties.Effects;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectTypes;
import org.spongepowered.api.entity.living.player.Player;

import java.util.*;

public class RacialEffectActions implements Action
{

    private static Map<String, Action> actionMap = new HashMap<>();

    public static final double DEFAULT_SPEED = .1;

    public static final Action NONE = new Action()
    {
        @Override
        public void perform(Player p)
        {
        }

        @Override
        public void remove(Player player)
        {
        }
    };


    public static final Action FAST = new Action()
    {
        @Override
        public void perform(Player p)
        {
            p.offer(Keys.WALKING_SPEED, 1.5);
        }

        @Override
        public void remove(Player p)
        {
            p.offer(Keys.WALKING_SPEED, DEFAULT_SPEED);
        }
    };

    public static final Action SLOW = new Action()
    {
        @Override
        public void perform(Player p)
        {
            p.offer(Keys.WALKING_SPEED, .75);
        }

        @Override
        public void remove(Player p)
        {
            p.offer(Keys.WALKING_SPEED, DEFAULT_SPEED);
        }
    };
    public static final Action TOUGH = new RacialHealthModifier(1.5);
    /*new Action()
    {
        @Override
        public void perform(Player player)
        {
                Optional<HealthData> healthDataOptional = player.get(HealthData.class);
                if(healthDataOptional.isPresent())
                {
                    HealthData healthData = healthDataOptional.get();
                    double delta = healthData.maxHealth().get() * 1.5;
                    MutableBoundedValue<Double> newHealth = healthData.maxHealth();
                    newHealth.set(delta);
                    healthData.set(newHealth);
                    newHealth = healthData.health();
                    newHealth.set(delta);
                    healthData.set(newHealth);
                    player.offer(healthData);
                }

        }

        @Override
        public void remove(Player player)
        {
            Optional<HealthData> healthDataOptional = player.get(HealthData.class);
            if(healthDataOptional.isPresent())
            {
                HealthData healthData = healthDataOptional.get();
                double delta = 20.0;
                MutableBoundedValue<Double> newHealth = healthData.maxHealth();
                newHealth.set(delta);
                healthData.set(newHealth);
                newHealth = healthData.health();
                newHealth.set(delta);
                healthData.set(newHealth);
                player.offer(healthData);
            }
        }
    };
    */

    public static final Action WEAK = new RacialHealthModifier(.75);/*Action()
    {
        @Override
        public void perform(Player player)
        {
            Optional<HealthData> healthDataOptional = player.get(HealthData.class);
            if(healthDataOptional.isPresent())
            {
                HealthData healthData = healthDataOptional.get();
                double delta = healthData.maxHealth().get() * .75;
                MutableBoundedValue<Double> newHealth = healthData.maxHealth();
                newHealth.set(delta);
                healthData.set(newHealth);
                newHealth = healthData.health();
                newHealth.set(delta);
                healthData.set(newHealth);
                player.offer(healthData);
            }

        }

        @Override
        public void remove(Player player)
        {
            Optional<HealthData> healthDataOptional = player.get(HealthData.class);
            if(healthDataOptional.isPresent())
            {
                HealthData healthData = healthDataOptional.get();
                double delta = 20.0;
                MutableBoundedValue<Double> newHealth = healthData.maxHealth();
                newHealth.set(delta);
                healthData.set(newHealth);
                newHealth = healthData.health();
                newHealth.set(delta);
                healthData.set(newHealth);
                player.offer(healthData);
            }
        }
    };
    */
    public static final Action FAST_MINE = new Action()
    {

        private PotionEffect effect = PotionEffect.builder().potionType(PotionEffectTypes.HASTE).duration(1000000).ambience(false).particles(false).build();
        @Override
        public void perform(Player player)
        {
                PotionEffectData effectData = player.getOrCreate(PotionEffectData.class).get();
                effectData.addElement(effect);
                player.offer(effectData);
        }

        @Override
        public void remove(Player player)
        {
            PotionEffectData effectData = player.getOrCreate(PotionEffectData.class).get();
            effectData.remove(effect);
            player.offer(effectData);
        }
    };
    public static final Action SLOW_MINE = new Action()
    {

        private PotionEffect effect = PotionEffect.builder().potionType(PotionEffectTypes.MINING_FATIGUE).duration(1000000).ambience(false).particles(false).build();
        @Override
        public void perform(Player player)
        {
            PotionEffectData effectData = player.getOrCreate(PotionEffectData.class).get();
            effectData.addElement(effect);
            player.offer(effectData);
        }

        @Override
        public void remove(Player player)
        {
            PotionEffectData effectData = player.getOrCreate(PotionEffectData.class).get();
            effectData.remove(effect);
            player.offer(effectData);
        }
    };

    public static Action getPremade(String s)
    {
        switch (s.toUpperCase())
        {
            case "FAST": return FAST;
            case "SLOW": return SLOW;
            case "TOUGH": return TOUGH;
            case "WEAK": return WEAK;
            case "FASTMINE": return FAST_MINE;
            case "SLOWMINE": return SLOW_MINE;
        }
        return NONE;
    }
    public static Action getFromMap(String s)
    {
        return actionMap.get(s);
    }
    public static Map<String, Action> addToMap(String s, Action a)
    {
        actionMap.put(s, a);
        return actionMap;
    }


    @Override
    public void perform(Player P)
    {

    }

    @Override
    public void remove(Player player)
    {

    }
/*
    public static final RacialEffect ELF_SPEED = new RacialEffect()
    {
        @Override
        public String getName()
        {
            return "Elf Speed";
        }

        @Override
        public void onApply(Player player)
        {
            player.offer(Keys.WALKING_SPEED, player.get(Keys.WALKING_SPEED).get() * 1.3);
        }
    };

    public static final RacialEffect DWARF_MINING = new RacialEffect()
    {
        @Override
        public String getName()
        {
            return "Dwarf Mining";
        }

        @Override
        public void onApply(Player player)
        {
        }
    };

    public static final RacialEffect HUMAN_MUNDANE = new RacialEffect()
    {
        @Override
        public String getName()
        {
            return "Human Mundane";
        }

        @Override
        public void onApply(Player player)
        {

        }
    };
*/

}
