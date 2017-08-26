package io.github.eclipseironworks.mc.eclipseraces.Properties.Effects;

import org.spongepowered.api.data.manipulator.mutable.entity.HealthData;
import org.spongepowered.api.data.value.mutable.MutableBoundedValue;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;

public class RacialHealthModifier implements Action
{
    public double getModifier()
    {
        return modifier;
    }

    private double modifier;

    public RacialHealthModifier(double value)
    {
        this.modifier = value;
    }

    @Override
    public void perform(Player player)
    {
        Optional<HealthData> healthDataOptional = player.get(HealthData.class);
        if(healthDataOptional.isPresent())
        {
            HealthData healthData = healthDataOptional.get();
            double delta = healthData.maxHealth().get() * this.modifier;
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
            MutableBoundedValue<Double> newHealth = healthData.maxHealth();
            newHealth.set(20D);
            healthData.set(newHealth);
            newHealth = healthData.health();
            newHealth.set(20D);
            healthData.set(newHealth);
            player.offer(healthData);
        }
    }
}
