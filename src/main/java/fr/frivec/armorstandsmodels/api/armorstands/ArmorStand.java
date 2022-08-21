package fr.frivec.armorstandsmodels.api.armorstands;

import fr.frivec.armorstandsmodels.ArmorStandsModels;
import fr.frivec.armorstandsmodels.api.items.ItemCreator;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class ArmorStand {

    private int id;
    private String name = "";
    private ItemStack rightHand = null, leftHand = null, helmet = null, chestplate = null, leggings = null, boots = null;
    private boolean plate = false, hands = false, gravity = false, invisible = false;
    private int size;
    private Location currentLocation;

    private transient org.bukkit.entity.ArmorStand stand;

    public ArmorStand(String name) {

        this.name = name;

    }

    public void spawn(final Location location) {

        this.stand = (org.bukkit.entity.ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        this.currentLocation = location;

    }

    /**
     * Set the size of the armor stand
     * @param size The size of the armor stand between 0 and 3
     * @return updated ArmorStand object
     */
    public ArmorStand size(final int size) {

        this.size = size;

        return this;
    }

    /**
     * Choose if the armor stand will have arms or not
     * @param hands True if you want the armor stand to have arms
     * @return updated ArmorStand object
     */
    public ArmorStand hands(final boolean hands) {

        this.hands = hands;

        return this;
    }

    /**
     * Choose if the armor stand will have its baseplate
     * @param plate True if you want the baseplate to be present
     * @return updated ArmorStand object
     */
    public ArmorStand plate(final boolean plate) {

        this.plate = plate;

        return this;
    }

    /**
     * Choose if the armor stand will be invisible or not.
     * @param invisible True if you want the armor stand to be invisible
     * @return updated ArmorStand object
     */
    public ArmorStand invisible(final boolean invisible) {

        this.invisible = invisible;

        return this;
    }

    /**
     * Choose if the armor stand will be affected by gravity.
     * Disable it if the armor stand will be above the ground.
     * @param gravity True if you want the armor stand to be invisible
     * @return updated ArmorStand object
     */
    public ArmorStand gravity(final boolean gravity) {

        this.gravity = gravity;

        return this;
    }

    /**
     * Set the item that will be placed in armorstand's right hand
     * @param item The ItemStack object that will be placed
     * @return updated ArmorStand object
     */
    public ArmorStand rightHand(final ItemStack item) {

        this.rightHand = item;

        return this;
    }

    /**
     * Set the item that will be placed in armorstand's left hand
     * @param item The ItemStack object that will be placed
     * @return updated ArmorStand object
     */
    public ArmorStand leftHand(final ItemStack item) {

        this.leftHand = item;

        return this;
    }

    /**
     * Set the item that will be placed in armorstand's helmet slot
     * @param item The ItemStack object that will be placed
     * @return updated ArmorStand object
     */
    public ArmorStand helmet(final ItemStack item) {

        this.helmet = item;

        return this;
    }

    /**
     * Set the item that will be placed in armorstand's chestplate slot
     * @param item The ItemStack object that will be placed
     * @return updated ArmorStand object
     */
    public ArmorStand chestplate(final ItemStack item) {

        this.chestplate = item;

        return this;
    }

    /**
     * Set the item that will be placed in armorstand's leggings slot
     * @param item The ItemStack object that will be placed
     * @return updated ArmorStand object
     */
    public ArmorStand leggings(final ItemStack item) {

        this.leggings = item;

        return this;
    }

    /**
     * Set the item that will be placed in armorstand's boots slot
     * @param item The ItemStack object that will be placed
     * @return updated ArmorStand object
     */
    public ArmorStand boots(final ItemStack item) {

        this.boots = item;

        return this;
    }

    public org.bukkit.entity.ArmorStand getStand() {
        return stand;
    }
}
