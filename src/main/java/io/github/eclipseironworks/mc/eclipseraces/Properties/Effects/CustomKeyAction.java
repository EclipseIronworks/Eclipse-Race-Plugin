package io.github.eclipseironworks.mc.eclipseraces.Properties.Effects;

import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.key.Keys.*;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.living.player.Player;

public class CustomKeyAction implements Action
{
    private Key<Value<Double>> effectKey;
    private double value;
    private double defVal;

    public CustomKeyAction(Key<Value<Double>> key, double value)
    {
        this.effectKey = key;
        this.value = value;
    }

    @Override
    public void perform(Player p)
    {
        if(p.get(this.effectKey).isPresent())
        {
            defVal = p.get(this.effectKey).get();
            double delta = defVal * value;
            p.offer(this.effectKey, this.value);
        }
    }

    public double getValue()
    {
        return value;
    }

    @Override
    public void remove(Player p)
    {
        p.offer(this.effectKey, defVal);
    }
}
