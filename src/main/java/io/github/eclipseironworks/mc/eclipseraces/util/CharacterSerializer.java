package io.github.eclipseironworks.mc.eclipseraces.util;

import com.google.common.reflect.TypeToken;
import io.github.eclipseironworks.mc.eclipseraces.ECharacter;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

import java.util.UUID;

public class CharacterSerializer implements TypeSerializer<ECharacter>
{
    @Override
    public ECharacter deserialize(TypeToken<?> typeToken, ConfigurationNode node) throws ObjectMappingException
    {
        UUID uuid = UUID.fromString(node.getString());
        int raceID = node.getNode("RaceID").getInt();

        return ECharacter.builder().uuid(uuid).raceID(raceID).build();
    }

    @Override
    public void serialize(TypeToken<?> typeToken, ECharacter eCharacter, ConfigurationNode node) throws ObjectMappingException
    {
            String uuid = eCharacter.getUUID().toString();
            int raceID = eCharacter.getRaceID();

            node.getNode(uuid, "RaceID").setValue(raceID);
    }
}
