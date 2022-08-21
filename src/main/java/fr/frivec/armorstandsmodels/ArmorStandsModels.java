package fr.frivec.armorstandsmodels;

import fr.frivec.armorstandsmodels.api.armorstands.ArmorStand;
import fr.frivec.armorstandsmodels.commands.DevCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;

public final class ArmorStandsModels extends JavaPlugin {

    private static ArmorStandsModels instance;

    private LinkedHashMap<Integer, ArmorStand> armorstands;

    @Override
    public void onEnable() {

        instance = this;

        //Lists
        this.armorstands = new LinkedHashMap<>();

        //Commands
        this.getCommand("dev").setExecutor(new DevCommand());

    }

    @Override
    public void onDisable() {}

    public static ArmorStandsModels getInstance() {
        return instance;
    }

    public LinkedHashMap<Integer, ArmorStand> getArmorstands() {
        return armorstands;
    }
}
