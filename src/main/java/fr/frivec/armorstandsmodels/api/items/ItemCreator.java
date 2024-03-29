package fr.frivec.armorstandsmodels.api.items;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import fr.frivec.armorstandsmodels.ArmorStandsModels;
import net.kyori.adventure.text.Component;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemCreator {
	
    private static final Base64 base64 = new Base64();
	
	private ItemStack itemStack;
	private ItemMeta itemMeta;
	
	/**
	 * 
	 * Create item by using a material and how many items you want
	 * @param material the material of the item
	 * @param amount the amount of the itemstack
	 * 
	 */
	
	public ItemCreator(Material material, int amount) {
		this.itemStack = new ItemStack(material, amount);
		this.itemMeta = this.itemStack.getItemMeta();
	}
	
	public ItemCreator(ItemStack itemStack) {
		this.itemStack = itemStack;
		this.itemMeta = this.itemStack.getItemMeta();
	}
	
	/**
	 * 
	 * Create a skull itemstack
	 * @param material Leather piece of armor you want
	 * @param color The color of the piece of amor
	 * @return a new itemcreator
	 * 
	 */
	
	public ItemCreator leatherArmor(final Material material, final Color color) {
		
		final ItemStack itemStack = new ItemStack(material, 1);
		final LeatherArmorMeta itemMeta = (LeatherArmorMeta) itemStack.getItemMeta();
		
		itemMeta.setColor(color);
		
		if(this.itemMeta.displayName() != null && !this.itemMeta.displayName().toString().equalsIgnoreCase(""))
			
			itemMeta.displayName(this.itemMeta.displayName());
			
		itemStack.setItemMeta(itemMeta);
		
		this.itemStack = itemStack;
		this.itemMeta = itemMeta;
		
		return this;
		
	}
	
	public ItemCreator firework(final int power, final FireworkEffect... effects) {
		
		final ItemStack itemStack = new ItemStack(Material.FIREWORK_ROCKET, 1);
		final FireworkMeta meta = (FireworkMeta) itemStack.getItemMeta();
		meta.addEffects(effects);
		meta.setPower(power);
		
		itemStack.setItemMeta(meta);
		
		this.itemStack = itemStack;
		this.itemMeta = meta;
		
		return this;
		
	}
	
	@SuppressWarnings("deprecation")
	public ItemCreator skull(String owner) {
		
		final ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, 1);
		final SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
		meta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
		itemStack.setItemMeta(meta);
		
		return new ItemCreator(itemStack);
		
	}
	
	/**
	 * 
	 * Create a skull itemstack
	 * @param owner the owner's uuid
	 * @return a new itemcreator
	 * 
	 */
	
	public ItemCreator skull(UUID owner) {
		
		final ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD, 1);
		final SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
		meta.setOwningPlayer(Bukkit.getOfflinePlayer(owner));
		itemStack.setItemMeta(meta);
		
		return new ItemCreator(itemStack);
		
	}
	
	public ItemCreator skullByUrl(String url) {
		
		return new ItemCreator(getCustomSkull(url));
		
	}
	
	public ItemCreator unbreakable(final boolean unbreakable) {
		
		this.itemMeta.setUnbreakable(unbreakable);
		
		return this;
		
	}
	
	public ItemCreator addItemFlag(final ItemFlag...flags) {
		
		this.itemMeta.addItemFlags(flags);
		
		return this;
		
	}
	
	/**
	 * 
	 * Method by TigerHix
	 * https://github.com/TigerHix/Hex-Utils/blob/master/hex/util/Skull.java
	 * 
	 */
	
	public ItemStack getCustomSkull(String url) {
		
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = profile.getProperties();
        
        if (propertyMap == null) {
            throw new IllegalStateException("Profile doesn't contain a property map");
        }
        
        byte[] encodedData = base64.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        
        propertyMap.put("textures", new Property("textures", new String(encodedData)));
        
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta headMeta = head.getItemMeta();
        
        Class<?> headMetaClass = headMeta.getClass();
        
        Reflections.getField(headMetaClass, "profile", GameProfile.class).set(headMeta, profile);
        
        head.setItemMeta(headMeta);
        
        return head;
    }
	
	public ItemCreator addTag(final String keyName, final String value) {
		
		this.itemMeta.getPersistentDataContainer().set(new NamespacedKey(ArmorStandsModels.getInstance(), keyName), PersistentDataType.STRING, value);
		
		return this;
	}
	
	public ItemCreator addTag(final String keyName, final double value) {
		
		this.itemMeta.getPersistentDataContainer().set(new NamespacedKey(ArmorStandsModels.getInstance(), keyName), PersistentDataType.DOUBLE, value);
		
		return this;
	}
	
	public ItemCreator addTag(final String keyName, final int value) {
		
		this.itemMeta.getPersistentDataContainer().set(new NamespacedKey(ArmorStandsModels.getInstance(), keyName), PersistentDataType.INTEGER, value);
		
		return this;
	}
	
	public ItemCreator addTag(final String keyName, final long value) {
		
		this.itemMeta.getPersistentDataContainer().set(new NamespacedKey(ArmorStandsModels.getInstance(), keyName), PersistentDataType.LONG, value);
		
		return this;
	}
	
	public ItemCreator setAmount(int amount) {
		this.itemStack.setAmount(amount);
		return this;
	}
	
	public ItemCreator setDisplayName(String name) {
		this.itemMeta.displayName(Component.text(name));
		return this;
	}
	
	public ItemCreator setDurability(int durability) {
		((Damageable) this.itemStack).setDamage(this.itemStack.getType().getMaxDurability() - durability);
		return this;
	}
	
	public ItemCreator setLores(String... lores) {
		
		final List<Component> list = new ArrayList<>();
		
		for(String line : lores)
			
			list.add(Component.text(line));
		
		return setLores(list);
		
	}
	
	public ItemCreator setLoresAsString(List<String> lores) {
		
		final List<Component> componentLores = new ArrayList<>();
		
		for(String str : lores)
			
			componentLores.add(Component.text(str));
		
		return setLores(componentLores);
		
	}
	
	public ItemCreator setLores(List<Component> lores) {
		
		this.itemMeta.lore(lores);
		
		return this;
		
	}
	
	public ItemCreator addUnsafeEnchantment(Enchantment enchantment, int level) {
		this.itemStack.addUnsafeEnchantment(enchantment, level);
		return this;
	}
	
	public ItemCreator addEnchantment(Enchantment enchantment, int level) {
		this.itemMeta.addEnchant(enchantment, level, false);
		return this;
	}
	
	public ItemCreator addItemFlag(ItemFlag flag) {
		this.itemMeta.addItemFlags(flag);
		return this;
	}
	
	public ItemStack build() {
		this.itemStack.setItemMeta(itemMeta);
		return this.itemStack;
	}

	/**
	 * Return the default NamespacedKey format with SpigotAPI as main instance
	 * @param keyName The name of the key that have been set to the item
	 * @return a new instance of NamespacedKey
	 */
	public static NamespacedKey getDefaultKey(final String keyName) {

		return new NamespacedKey(ArmorStandsModels.getInstance(), keyName);

	}
	
	/**
	 * Check if the item has a specific tag
	 * @param item - ItemStack - The item you want to use
	 * @param key - NamespacedKey - The key you put on the item
	 * @param value - String - The value of the container
	 * @return
	 */
	public static boolean hasTag(final ItemStack item, final NamespacedKey key, final String value) {
		
		final ItemMeta itemMeta = item.getItemMeta();
		
		if(itemMeta != null) {
			
			final PersistentDataContainer container = itemMeta.getPersistentDataContainer();
			
			if(container != null && container.has(key, PersistentDataType.STRING) && container.get(key, PersistentDataType.STRING).equals(value))
				
				return true;
			
		}
		
		return false;
		
	}
	
	/**
	 * Check if the item has a specific tag
	 * @param item - ItemStack - The item you want to use
	 * @param key - NamespacedKey - The key you put on the item
	 * @param value - Double - The value of the container
	 * @return
	 */
	public static boolean hasTag(final ItemStack item, final NamespacedKey key, final double value) {
		
		final ItemMeta itemMeta = item.getItemMeta();
		
		if(itemMeta != null) {
			
			final PersistentDataContainer container = itemMeta.getPersistentDataContainer();
			
			if(container != null && container.has(key, PersistentDataType.DOUBLE) && container.get(key, PersistentDataType.DOUBLE).equals(value))
				
				return true;
			
		}
		
		return false;
		
	}
	
	/**
	 * Check if the item has a specific tag
	 * @param item - ItemStack - The item you want to use
	 * @param key - NamespacedKey - The key you put on the item
	 * @param value - Integer - The value of the container
	 * @return
	 */
	public static boolean hasTag(final ItemStack item, final NamespacedKey key, final int value) {
		
		final ItemMeta itemMeta = item.getItemMeta();
		
		if(itemMeta != null) {
			
			final PersistentDataContainer container = itemMeta.getPersistentDataContainer();
			
			if(container != null && container.has(key, PersistentDataType.INTEGER) && container.get(key, PersistentDataType.INTEGER).equals(value))
				
				return true;
			
		}
		
		return false;
		
	}
	
	/**
	 * Check if the item has a specific tag
	 * @param item - ItemStack - The item you want to use
	 * @param key - NamespacedKey - The key you put on the item
	 * @param value - Long - The value of the container
	 * @return
	 */
	public static boolean hasTag(final ItemStack item, final NamespacedKey key, final long value) {
		
		final ItemMeta itemMeta = item.getItemMeta();
		
		if(itemMeta != null) {
			
			final PersistentDataContainer container = itemMeta.getPersistentDataContainer();
			
			if(container != null && container.has(key, PersistentDataType.LONG) && container.get(key, PersistentDataType.LONG).equals(value))
				
				return true;
			
		}
		
		return false;
		
	}
	
	/**
	 * Check if the item has a specific tag
	 * @param item - ItemStack - The item you want to use
	 * @param key - NamespacedKey - The key you put on the item
	 * @param value - Byte - The value of the container
	 * @return
	 */
	public static boolean hasTag(final ItemStack item, final NamespacedKey key, final byte value) {
		
		final ItemMeta itemMeta = item.getItemMeta();
		
		if(itemMeta != null) {
			
			final PersistentDataContainer container = itemMeta.getPersistentDataContainer();
			
			if(container != null && container.has(key, PersistentDataType.BYTE) && container.get(key, PersistentDataType.BYTE).equals(value))
				
				return true;
			
		}
		
		return false;
		
	}
	
	public ItemMeta getItemMeta() {
		return itemMeta;
	}
	
	public void setItemMeta(ItemMeta itemMeta) {
		this.itemMeta = itemMeta;
	}
	
}
