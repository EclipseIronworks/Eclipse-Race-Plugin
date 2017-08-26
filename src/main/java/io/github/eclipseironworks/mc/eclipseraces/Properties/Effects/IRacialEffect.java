package io.github.eclipseironworks.mc.eclipseraces.Properties.Effects;


import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.property.PropertyHolder;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.living.player.Player;

import java.util.List;

public interface IRacialEffect
{
    String getKey();

    void onApply(Player player);

    void onRemove(Player player);

    Action getAction();
}
