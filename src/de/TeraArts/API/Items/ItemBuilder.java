package de.TeraArts.API.Items;

import java.util.Arrays;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemBuilder {

	private ItemStack item;
	
	public ItemBuilder(Material Material) {
		
		this.item = new ItemStack(Material);
				
	}
	
	public ItemBuilder(String ID) {
		
		String[] s = ID.split(":");

		int type = Integer.parseInt(s[0]);

		int data = Integer.parseInt(s[1]);
		
		this.item = new ItemStack(type, 1, (short) data);
				
	}
	

	public ItemBuilder(Material Material, int Amount, short Data) {
		
		this.item = new ItemStack(Material, Amount, Data);
		
	}

	
	public ItemBuilder setDisplayName(String Name) {
		
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Name);
		this.item.setItemMeta(meta);
		
		return this;
	}
	
	public ItemBuilder setLore(String... Lore) {
		
		ItemMeta meta = item.getItemMeta();		
		meta.setLore(Arrays.asList(Lore));
		this.item.setItemMeta(meta);
		
		return this;
	}
	
		
	public ItemBuilder addEnchantment(Enchantment Enchantment, int Level) {
		
		ItemMeta meta = item.getItemMeta();		
		meta.addEnchant(Enchantment, Level, true);
		this.item.setItemMeta(meta);
		
		return this;
	}
	
	public ItemBuilder setUnbreakable(boolean Unbreakable) {
		
		ItemMeta meta = item.getItemMeta();		
		meta.spigot().setUnbreakable(Unbreakable);
		this.item.setItemMeta(meta);
		
		return this;
		
	}
	
	public ItemBuilder setMeta(ItemMeta Meta) {
		
		this.item.setItemMeta(Meta);
		
		return this;
		
	}
	
	public ItemBuilder setAmount(int Amount) {
		
		ItemMeta meta = item.getItemMeta();		
		this.item.setAmount(Amount);
		item.setItemMeta(meta);
		
		return this;
		
	}

	public ItemBuilder setDurability(short Durability) {
		
		ItemMeta meta = item.getItemMeta();	
		this.item.setDurability(Durability);
		item.setItemMeta(meta);
		
		return this;
		
	}
	
	public ItemBuilder addGlow() {

        ItemMeta meta = this.item.getItemMeta();
        meta.addEnchant(Enchantment.DURABILITY, 0, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.item.setItemMeta(meta);
		
		return this;
		
	}
	
	public ItemBuilder hideEnchantments() {

        ItemMeta meta = this.item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.item.setItemMeta(meta);
		
		return this;
		
	}
	
	public ItemBuilder setSkullOwner(String Name) {
		
		SkullMeta meta = (SkullMeta) this.item.getItemMeta();
		meta.setOwner(Name);
		this.item.setItemMeta(meta);
		
		return this;
		
	}
	
	public ItemBuilder setColor(Color Color) {
		
		LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
		meta.setColor(Color);
		this.item.setItemMeta(meta);
		
		return this;
		
	}
	
	public ItemBuilder setColor (int R, int G, int B) {
		
		LeatherArmorMeta meta = (LeatherArmorMeta) this.item.getItemMeta();
		meta.setColor(Color.fromRGB(R, G, B));
		this.item.setItemMeta(meta);
		
		return this;
		
	}
	
	public ItemStack build() {
		
		return item;
		
	}
	
	
}
