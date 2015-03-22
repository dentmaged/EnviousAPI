package com.nayaverdier.enviousapi.itemstackbuilder;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;
import java.util.List;

public class ItemStackBuilder
{
    private ItemStack    itemStack;
    private ItemMeta     itemMeta;
    private List<String> lore;

    public ItemStackBuilder(Material material)
    {
        this(new ItemStack(material));
    }

    public ItemStackBuilder(ItemStack itemStack)
    {
        this.itemStack = itemStack;
        this.itemMeta  = itemStack.getItemMeta();
        this.lore      = (itemMeta.getLore() == null ? itemMeta.getLore() : new ArrayList<String>());
    }

    public ItemStackBuilder setAmount(int amount)
    {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder setName(String name)
    {
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        return this;
    }

    public ItemStackBuilder addLore(String line)
    {
        lore.add(line);
        return this;
    }

    public ItemStackBuilder setDurability(short durability)
    {
        itemStack.setDurability(durability);
        return this;
    }

    public ItemStackBuilder setData(int data)
    {
        itemStack.setData(new MaterialData(itemStack.getType(), (byte) data));
        return this;
    }

    public ItemStackBuilder addEnchant(Enchantment enchantment)
    {
        return enchant(enchantment, 1);
    }

    public ItemStackBuilder enchant(Enchantment enchantment, int level)
    {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStackBuilder setMaterial(Material material)
    {
        itemStack.setType(material);
        return this;
    }

    public ItemStackBuilder clearLore()
    {
        lore.clear();
        return this;
    }

    public ItemStackBuilder clearEnchantments()
    {
        itemStack.getEnchantments().clear();
        return this;
    }

    public ItemStack get()
    {
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}