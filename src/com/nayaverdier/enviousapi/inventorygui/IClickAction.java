package com.nayaverdier.enviousapi.inventorygui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface IClickAction
{
    public void execute(Player player, Inventory inventory, int slot, ItemStack itemStack);
}