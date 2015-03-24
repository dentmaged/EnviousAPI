package com.nayaverdier.enviousapi.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
            System.out.println("Error saving config file \"" + fileName + "\"!");
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
            System.out.println("Error reloading config file \"" + fileName + "\"!");
        }
    }

    /**
     * Creates the file, or copies it if the jar contains it.
     */
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
            System.out.println("Error creating config file \"" + fileName + "\"!");
        }
    }

    private void copy(String resourcePath, File parent)
    {
        try
        {
            Files.copy(plugin.getResource(resourcePath), new File(parent, resourcePath).toPath());
        }
        catch (IOException e)
        {
            System.out.println("Error copying file with path " + resourcePath + "!");
        }
    }
}