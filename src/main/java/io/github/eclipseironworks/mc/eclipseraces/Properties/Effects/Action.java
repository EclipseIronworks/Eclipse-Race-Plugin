package io.github.eclipseironworks.mc.eclipseraces.Properties.Effects;

import org.spongepowered.api.entity.living.player.Player;

public interface Action
{
    void perform(Player player);

    void remove(Player player);
}
