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

    /**
     * Creates an ItemStackBuilder with the given Material.
     *
     * @param material The material that the ItemStack will be.
     */
    public ItemStackBuilder(Material material)
    {
        this(new ItemStack(material));
    }

    /**
     * Creates an ItemStackBuilder with the given ItemStack.
     *
     * @param itemStack The ItemStack used for the builder.
     */
    public ItemStackBuilder(ItemStack itemStack)
    {
        this.itemStack = itemStack;
        this.itemMeta  = itemStack.getItemMeta();
        this.lore      = (itemMeta.getLore() == null ? itemMeta.getLore() : new ArrayList<String>());
    }

    /**
     * Sets the amount of the ItemStack.
     *
     * @param amount The amount for the ItemStack to be.
     * @return this (for method chaining)
     */
    public ItemStackBuilder setAmount(int amount)
    {
        itemStack.setAmount(amount);
        return this;
    }

    /**
     * Sets the name of the ItemStack.
     *
     * @param name The name to set the ItemStack to.
     * @return
     */
    public ItemStackBuilder setName(String name)
    {
        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        return this;
    }

    /**
     * Adds a line to the lore of the ItemStack.
     *
     * @param line The line to add to the lore.
     * @return this (for method chaining)
     */
    public ItemStackBuilder addLore(String line)
    {
        lore.add(ChatColor.translateAlternateColorCodes('&', line));
        return this;
    }

    /**
     * Sets the durability for the ItemStack.
     *
     * @param durability The durability to set the ItemStack.
     * @return this (for method chaining)
     */
    public ItemStackBuilder setDurability(short durability)
    {
        itemStack.setDurability(durability);
        return this;
    }

    /**
     * Sets the data for the ItemStack.
     *
     * @param data The data to set the ItemStack.
     * @return this (for method chaining)
     */
    public ItemStackBuilder setData(int data)
    {
        itemStack.setData(new MaterialData(itemStack.getType(), (byte) data));
        return this;
    }

    /**
     * Adds an Enchantment to the ItemStack.
     *
     * @param enchantment The Enchantment to add to the ItemStack.
     * @return this (for method chaining)
     */
    public ItemStackBuilder addEnchant(Enchantment enchantment)
    {
        return enchant(enchantment, 1);
    }

    /**
     * Adds an Enchantment to the ItemStack, with the given level.
     *
     * @param enchantment The Enchantment to add to the ItemStack.
     * @param level The level for the Enchantment to be.
     * @return this (for method chaining)
     */
    public ItemStackBuilder enchant(Enchantment enchantment, int level)
    {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Sets the material of the ItemStack.
     *
     * @param material
     * @return this (for method chaining)
     */
    public ItemStackBuilder setMaterial(Material material)
    {
        itemStack.setType(material);
        return this;
    }

    /**
     * Clears the lore of the ItemStack.
     *
     * @return this (for method chaining)
     */
    public ItemStackBuilder clearLore()
    {
        lore.clear();
        return this;
    }

    /**
     * Removes all Enchantments from the ItemStack.
     *
     * @return this (for method chaining)
     */
    public ItemStackBuilder clearEnchantments()
    {
        itemStack.getEnchantments().clear();
        return this;
    }

    /**
     * Gets the completed ItemStack.
     *
     * @return The final ItemStack.
     */
    public ItemStack get()
    {
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}