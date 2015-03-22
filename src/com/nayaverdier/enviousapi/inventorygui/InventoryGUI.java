package com.nayaverdier.enviousapi.inventorygui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class InventoryGUI implements Listener
{
    private Inventory                       inventory;
    private Map<Integer, IClickAction>      actions;

    public InventoryGUI(Plugin plugin, String title, int rows)
    {

        this.inventory = Bukkit.createInventory(null, rows * 9, ChatColor.translateAlternateColorCodes('&', title));
        this.actions   = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public InventoryGUI setItem(int slot, ItemStack itemStack, IClickAction clickAction)
    {
        slot = slot > inventory.getSize() ? slot % inventory.getSize() : slot;
        inventory.setItem(slot, itemStack);
        actions.put(slot, clickAction);
        return this;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onInventoryClick(InventoryClickEvent e)
    {
        if (e.getInventory().equals(inventory))
        {
            e.setCancelled(true);
            IClickAction action = actions.get(e.getRawSlot());
            if (action != null)
            {
                action.execute((Player) e.getWhoClicked(), e.getInventory(), e.getRawSlot(), e.getInventory().getItem(e.getRawSlot()));
            }
        }
    }

    public void openInventory(Player p)
    {
        p.openInventory(inventory);
    }

    public Inventory getInventory()
    {
        return this.inventory;
    }

    @EventHandler
    public void onInventoryClick2(InventoryClickEvent event) //Register the listener for the actions
    {
        if (event.getInventory().equals(getInventory())) //Check if they're clicking our inventory
        {
            if (event.getInventory().getItem(event.getRawSlot()).getItemMeta().getDisplayName().equals(ChatColor.RED + "Close Inventory")) //Check if the clicked item is the "Close Inventory" item
            {
                event.getWhoClicked().closeInventory();
            }
            else if (event.getInventory().getItem(event.getRawSlot()).getItemMeta().getDisplayName().equals(ChatColor.DARK_RED + "Kill Yourself!"))
            {
                event.getWhoClicked().setHealth(0);
            }
        }
    }
}