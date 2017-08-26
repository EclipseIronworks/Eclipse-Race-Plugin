package io.github.eclipseironworks.mc.eclipseraces.Properties.Effects;

import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.living.player.Player;

public class CustomEffectAction implements Action
{
    private PotionEffect effect;

    public CustomEffectAction(PotionEffect effect)
    {
        this.effect = effect;
    }

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


}
