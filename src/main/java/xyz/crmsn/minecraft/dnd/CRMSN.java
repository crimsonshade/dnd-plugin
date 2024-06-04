package xyz.crmsn.minecraft.dnd;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.crmsn.minecraft.dnd.commands.FeedCommand;
import xyz.crmsn.minecraft.dnd.listeners.JoinListener;
import xyz.crmsn.minecraft.dnd.listeners.RollInteractListener;
import xyz.crmsn.minecraft.dnd.listeners.StickDiceListener;

public final class CRMSN extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new RollInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new StickDiceListener(this), this);

        getCommand("feed").setExecutor(new FeedCommand());
    }
}
