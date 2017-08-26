package io.github.eclipseironworks.mc.eclipseraces;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class EclipseLocation
{
    private double x;
    private double y;
    private double z;

    public EclipseLocation(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;

    }

    public Location<World> getLocation(String worldname)
    {
        World world = Sponge.getServer().getWorld(worldname).get();
        Location<World> loc = new Location<World>(world, this.x,this.y,this.z);
        return loc;
    }

}
