package io.github.eclipseironworks.mc.eclipseraces;


import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;

import javax.annotation.Nullable;
import java.util.List;

public class EKeys
{
    public static final Key<Value<Boolean>> MENU_COMMAND = KeyFactory.makeSingleKey(
            TypeToken.of(Boolean.class),
            new TypeToken<Value<Boolean>>() {},
            DataQuery.of("MenuCommand"), "eRaces:menu_command", "Menu Command");


    public static final Key<Value<Boolean>> MENU_INVENTORY = KeyFactory.makeSingleKey(
            TypeToken.of(Boolean.class),
            new TypeToken<Value<Boolean>>() {},
            DataQuery.of("MenuInventory"), "eRaces:menu_inventory", "Menu Inventory");
        public static final Key<Value<Race>> RACE = KeyFactory.makeSingleKey(
            TypeToken.of(Race.class),
            new TypeToken<Value<Race>>() {},
            DataQuery.of("Race"), "eRaces:race", "Race");


    public static final Key<Value<Race>> DEFAULT_RACE = KeyFactory.makeSingleKey(
            TypeToken.of(Race.class),
            new TypeToken<Value<Race>>() {},
            DataQuery.of("DefaultRace"), "eraces:default_race", "Default Race" );
}
