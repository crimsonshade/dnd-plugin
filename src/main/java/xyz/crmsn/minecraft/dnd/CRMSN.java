package xyz.crmsn.minecraft.dnd;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.crmsn.minecraft.dnd.commands.FeedCommand;
import xyz.crmsn.minecraft.dnd.commands.GodCommand;
import xyz.crmsn.minecraft.dnd.listeners.JoinListener;
import xyz.crmsn.minecraft.dnd.listeners.RollInteractListener;
import xyz.crmsn.minecraft.dnd.listeners.StickDiceListener;

public final class CRMSN extends JavaPlugin {

    @Override
    public void onEnable() {
        // Save the default config if it doesn't already exist
        saveDefaultConfig();

        FileConfiguration config = getConfig();
        String pluginName = this.getName();
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new JoinListener(config), this);
        getServer().getPluginManager().registerEvents(new RollInteractListener(config), this);
        getServer().getPluginManager().registerEvents(new StickDiceListener(config), this);

        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("god").setExecutor(new GodCommand(pluginName));
    }
}
