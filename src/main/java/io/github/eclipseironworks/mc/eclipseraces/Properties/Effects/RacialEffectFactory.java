package io.github.eclipseironworks.mc.eclipseraces.Properties.Effects;

import org.spongepowered.api.entity.living.player.Player;

public class RacialEffectFactory
{

    public static Builder builder()
    {
        return new Builder();
    }

    protected static class Hidden
    {
        protected static class RacialEffectConstruct implements IRacialEffect
        {
            protected String key;
            protected Action action;


            private RacialEffectConstruct()
            {

            }
            protected RacialEffectConstruct(Builder builder)
            {
                this.key = builder.key;
                this.action = builder.action;
            }

            @Override
            public String getKey()
            {
                return key;
            }

            @Override
            public void onApply(Player player)
            {
                action.perform(player);

            }

            @Override
            public void onRemove(Player player)
            {
                action.remove(player);

            }

            @Override
            public Action getAction()
            {
                return action;
            }
        }

    }

    public static class Builder extends Hidden.RacialEffectConstruct
    {
            protected Builder()
            {
            }
            public Builder key(String in)
            {
                this.key=in;
                return this;
            }
            public Builder action(Action a)
            {
                this.action = a;
                return this;
            }
            public IRacialEffect build()
            {
                return new Hidden.RacialEffectConstruct(this);

            }
            public Builder reset()
            {
                return new Builder();
            }


    }




}
