package xyz.crmsn.minecraft.dnd.listeners;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class StickDiceListener implements Listener {

    private final FileConfiguration config;

    public StickDiceListener(FileConfiguration config) {
        this.config = config;
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (event.getItemDrop().getItemStack().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', config.getString("dice.menu.title")))) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void onMove(InventoryClickEvent event) {
        ItemStack currentItem = event.getCurrentItem();
        if (currentItem != null && currentItem.hasItemMeta() && currentItem.getItemMeta().hasDisplayName()) {
            String displayName = currentItem.getItemMeta().getDisplayName();
            String configTitle = ChatColor.translateAlternateColorCodes('&', config.getString("dice.menu.title"));

            if (displayName.equals(configTitle)) {
                event.setCancelled(true);
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
