package io.github.eclipseironworks.mc.eclipseraces;

import com.sun.corba.se.impl.orb.ParserTable;
import io.github.eclipseironworks.mc.eclipseraces.Properties.Effects.IRacialEffect;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.MemoryDataContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public interface Race extends DataSerializable
{
    static Race NONE = builder().name(Text.of("Default")).build();

   // ICharacterHealth getHealth();
    Text getRaceName();
    Location getSpawn();
    HashSet<IRacialEffect> getEffects();
    HashMap<String, IRacialEffect> getEffectMap();
    boolean addEffect(IRacialEffect e);
    void applyEffects(ECharacter c);
    boolean hasSpawn();

    class Hidden
    {
        protected static class DefaultRace implements  Race
        {
            protected Text raceName;

     //       protected ICharacterHealth health;
            protected Location spawnLoc;
            protected HashSet<IRacialEffect> racialEffects = new HashSet<>();
            protected boolean spawn = false;

            private DefaultRace(){
            }
            protected DefaultRace(Builder builder)
            {

                this.raceName = builder.raceName;
       //         this.health = builder.health;
                if(builder.spawn)
                {
                    this.spawn = builder.spawn;
                    this.spawnLoc = builder.spawnLoc;
                }
                if(!builder.racialEffects.isEmpty())
                    this.racialEffects = builder.racialEffects;

            }
/*
            @Override
            public ICharacterHealth getHealth()
            {
                return health;
            }
*/
            @Override
            public Text getRaceName()
            {
                return raceName;
            }

            @Override
            public Location getSpawn()
            {
                return spawnLoc;
            }
            public Location setSpawn(Location location)
            {
                this.spawnLoc = location;
                this.spawn = true;
                return this.spawnLoc;
            }

            @Override
            public boolean addEffect(IRacialEffect e)
            {

                if(racialEffects.contains(e))
                return false;
                racialEffects.add(e);
                return true;
            }

            @Override
            public HashSet<IRacialEffect> getEffects()
            {
                return racialEffects;
            }



            @Override
            public HashMap<String, IRacialEffect> getEffectMap()
            {
                HashMap<String, IRacialEffect> out = new HashMap<>();
                for(IRacialEffect e : racialEffects)
                {
                    out.put(e.getKey(), e);
                }

                return out;
            }

            @Override
            public void applyEffects(ECharacter c)
            {
                for(IRacialEffect e : racialEffects)
                {
                    c.apply(e);
                }
            }

            @Override
            public boolean hasSpawn()
            {
                return spawn;
            }


            @Override
            public int getContentVersion()
            {
                return 0;
            }

            @Override
            public DataContainer toContainer()
            {
                return new MemoryDataContainer()
                        .set(DataQuery.of("ID"), raceName);
            }
        }
    }

    public static Builder builder()
    {
        return new Builder();
    }
    static Race buildEmpty(Text t)
    {
        return builder().name(t).build();
    }


    class Builder extends Hidden.DefaultRace
    {
        public static Builder make()
        {
            return new Builder();
        }

        public Builder()
        {
        }

        public Race build()
        {
            return new Hidden.DefaultRace(this);
        }

        public Builder spawn(Location spawn)
        {
            this.spawn = true;
            this.spawnLoc = spawn;
            return this;
        }
        public Builder name(Text name)
        {
            this.raceName = name;
            return this;
        }
  /*
        public Builder health(ICharacterHealth health)
        {
            this.health = health;
            return this;
        }
   */
        public Builder effect(IRacialEffect effect)
        {
            this.racialEffects.add(effect);
            return this;
        }
        public Builder effects(Set<IRacialEffect> effectSet)
        {
            this.racialEffects.addAll(effectSet);
            return this;
        }

    }


}
