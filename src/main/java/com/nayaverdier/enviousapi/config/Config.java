package com.nayaverdier.enviousapi.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;

/**
 * Copyright (c) 2015 nverdier
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to
 * do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF
 * OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
public class Config extends YamlConfiguration
{
    private Plugin plugin;
    private String fileName;
    private File   configFile;

    /**
     * Create a new Config.
     *
     * @param plugin The class extending JavaPlugin.
     * @param fileName The name of the file being created.
     */
    public Config(Plugin plugin, String fileName)
    {
        this(plugin, fileName, new File(plugin.getDataFolder(), fileName + (fileName.endsWith(".yml") ? "" : ".yml")));
    }

    /**
     * Creates a new Config.
     *
     * @param plugin The class extending JavaPlugin.
     * @param fileName The name of the file being created.
     * @param parent The path of the parent
     */
    public Config(Plugin plugin, String fileName, String parent)
    {
        this(plugin, fileName, new File(plugin.getDataFolder() + File.separator + parent, fileName + (fileName.endsWith(".yml") ? "" : ".yml")));
    }

    /**
     * Creates a new Config.
     *
     * @param plugin The class extending JavaPlugin.
     * @param fileName The name of the file being created.
     * @param file The output file.
     */
    private Config(Plugin plugin, String fileName, File file)
    {
        this.plugin     = plugin;
        this.fileName   = fileName + (fileName.endsWith(".yml") ? "" : ".yml");
        this.configFile = file;

        create();
    }

    /**
     * Saves the FileConfiguration to the file.
     */
    public void save()
    {
        try
        {
            save(configFile);
        }
        catch (IOException e)
        {
            plugin.getLogger().log(Level.SEVERE, "Error saving config file \"" + fileName + "\"!");
        }
    }

    /**
     * Load the contents of the file to the FileConfiguration.
     */
    public void reload()
    {
        try
        {
            load(configFile);
        }
        catch (IOException | InvalidConfigurationException e)
        {
            plugin.getLogger().log(Level.SEVERE, "Error creating config file \"" + fileName + "\"!");
        }
    }

    private void create()
    {
        try
        {
            if (!configFile.exists())
            {
                if (plugin.getResource(fileName) != null)
                {
                    copy(fileName, configFile.getParentFile());
                    load(configFile);
                }
                else
                {
                    save(configFile);
                }
            }
            else
            {
                load(configFile);
                save(configFile);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            plugin.getLogger().log(Level.SEVERE, "Error creating config file \"" + fileName + "\"!");
        }
    }

    /**
     * Gets a string from the config and translates all of the colors.
     *
     * @param path The path to get the colored string from.
     * @return The string from the config at specified path, with replaced colors.
     */
    public String getColored(String path)
    {
        String atPath = getString(path);
        return atPath == null ? null : ChatColor.translateAlternateColorCodes('&', atPath);
    }

    private void copy(String resourcePath, File parent)
    {
        try
        {
            Files.copy(plugin.getResource(resourcePath), new File(parent, resourcePath).toPath());
        }
        catch (IOException e)
        {
            plugin.getLogger().log(Level.SEVERE, "Error copying file with path " + resourcePath + "!");
        }
    }
}