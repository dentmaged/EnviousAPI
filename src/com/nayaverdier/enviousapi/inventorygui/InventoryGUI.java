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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InventoryGUI implements Listener
{
    private Inventory                  inventory;
    private Map<Integer, IClickAction> actions;

    /**
     * Creates an InventoryGUI with a title and certain amount of rows.
     *
     * @param plugin Instance of the class extending JavaPlugin.
     * @param title The title of the GUI.
     * @param rows The number of rows to be in the inventory.
     */
    public InventoryGUI(Plugin plugin, String title, int rows)
    {
        this.inventory = Bukkit.createInventory(null, rows * 9, ChatColor.translateAlternateColorCodes('&', title));
        this.actions   = new HashMap<>();

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    /**
     * Puts an ItemStack in a certain slot, and adds an IClickAction to it.
     *
     * @param slot The slot you want to put the ItemStack in.
     * @param itemStack The ItemStack you are adding.
     * @param clickAction The IClickAction that runs when the ItemStack is clicked.
     * @return this (for method chaining)
     */
    public InventoryGUI setItem(int slot, ItemStack itemStack, IClickAction clickAction)
    {
        slot = slot > inventory.getSize() ? slot % inventory.getSize() : slot;
        inventory.setItem(slot, itemStack);
        actions.put(slot, clickAction);
        return this;
    }

    /**
     * Opens the inventory for a player.
     *
     * @param player The player to open the inventory for.
     * @return this (for method chaining)
     */
    public InventoryGUI openInventory(Player player)
    {
        player.openInventory(inventory);
        return this;
    }

    /**
     * Opens the inventory for a Player[]
     *
     * @param players The players to open the inventory for.
     * @return this (for method chaining)
     */
    public InventoryGUI openInventory(Player[] players)
    {
        for (Player player : players)
        {
            openInventory(player);
        }
        return this;
    }

    /**
     * Opens the inventory for a Collection<? extends Player>
     *
     * @param players The players to open the inventory for.
     * @return this (for method chaining)
     */
    public InventoryGUI openInventory(Collection<? extends Player> players)
    {
        for (Player player : players)
        {
            openInventory(player);
        }
        return this;
    }

    /**
     * Returns the created inventory.
     *
     * @return The final Inventory.
     */
    public Inventory get()
    {
        return this.inventory;
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
}