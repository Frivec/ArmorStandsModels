package fr.frivec.armorstandsmodels.api.models;

import fr.frivec.armorstandsmodels.api.armorstands.ArmorStand;
import org.bukkit.Location;

import java.util.HashSet;

public class Model {

    private String name;
    private int id;

    private boolean spawned;

    private Location center;
    private float angle;

    private HashSet<ArmorStand> stands;

    public Model(final String name) {

        this.name = name;
        this.angle = 0.0f;

    }

    public boolean spawn(final Location location) {

        if(this.spawned)

            this.destroy();

        this.center = location;

        //TODO Add spawn of all

        return true;

    }

    public boolean destroy() {

        if(!this.spawned)

            return false;

        for(ArmorStand stands : this.stands)

            stands.getStand().remove();

        return true;

    }

    public Location getCenter() {
        return center;
    }

    public HashSet<ArmorStand> getStands() {
        return stands;
    }
}
