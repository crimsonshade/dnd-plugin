package xyz.crmsn.minecraft.dnd.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodCommand implements CommandExecutor {
    private final String pluginName;

    public GodCommand(String pluginName) {
        this.pluginName = pluginName;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (player.isInvulnerable()) {
                player.setInvulnerable(false);
                player.sendMessage(ChatColor.YELLOW + "[" + pluginName + "] You are no longer in God Mode");
            } else {
                player.setInvulnerable(true);
                player.sendMessage(ChatColor.YELLOW + "[" + pluginName + "] You are now in God Mode");
            }
        }
        return true;
    }
}
