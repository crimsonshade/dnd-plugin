package xyz.crmsn.minecraft.dnd.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import xyz.crmsn.minecraft.dnd.CRMSN;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RollInteractListener implements Listener {
    private final CRMSN plugin;

    public RollInteractListener(CRMSN plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemInteract(PlayerInteractEvent event) {
        FileConfiguration config = plugin.getConfig();

        Player player = event.getPlayer();


        if (checkForItemInHand(event)) {
            event.setCancelled(true);
            createClickableDice(player, config);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        FileConfiguration config = plugin.getConfig();

        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', config.getString("dice.menu.title")))) {
                if (event.isRightClick()) return;

                event.setCancelled(true);

                Random random = new Random();

                switch (event.getCurrentItem().getItemMeta().getDisplayName()) {
                    case "D4":
                        int rand_d4 = random.nextInt(4) + 1;
                        player.sendMessage(ChatColor.RED + "D4: " + rand_d4);
                        break;
                    case "D6":
                        int rand_d6 = random.nextInt(6) + 1;
                        player.sendMessage(ChatColor.AQUA + "D6: " + rand_d6);
                        break;
                    case "D8":
                        int rand_d8 = random.nextInt(8) + 1;
                        player.sendMessage(ChatColor.GREEN + "D8: " + rand_d8);
                        break;
                    case "D10":
                        int rand_d10 = random.nextInt(10) + 1;
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "D10: " + rand_d10);
                        break;
                    case "D12":
                        int rand_d12 = random.nextInt(12) + 1;
                        player.sendMessage(ChatColor.DARK_BLUE + "D12: " + rand_d12);
                        break;
                    case "D20":
                        int rand_d20 = random.nextInt(20) + 1;
                        player.sendMessage(ChatColor.GOLD + "D20: " + rand_d20);
                        break;
                    case "D100":
                        int rand_d100 = random.nextInt(100) + 1;
                        player.sendMessage(ChatColor.DARK_GRAY + "D100: " + rand_d100);
                        break;
                }
            }
        }
    }

    private void createClickableDice(Player player, FileConfiguration config) {
        Inventory menu = Bukkit.createInventory(player, 27, ChatColor.translateAlternateColorCodes('&', config.getString("dice.menu.title")));

        ItemStack d4 = new ItemStack(Material.PLAYER_HEAD);
        ItemStack d6 = new ItemStack(Material.PLAYER_HEAD);
        ItemStack d8 = new ItemStack(Material.PLAYER_HEAD);
        ItemStack d10 = new ItemStack(Material.PLAYER_HEAD);
        ItemStack d100 = new ItemStack(Material.PLAYER_HEAD);
        ItemStack d20 = new ItemStack(Material.PLAYER_HEAD);
        ItemStack d12 = new ItemStack(Material.PLAYER_HEAD);

        // d100.setItemMeta(addItemMeta(d100, "D100", "https://textures.minecraft.net/texture/915f7c313bca9c2f958e68ab14ab393867d67503affff8f20cb13fbe917fd31"));

        menu.setItem(10, d4);
        menu.setItem(11, d6);
        menu.setItem(12, d8);
        menu.setItem(13, d10);
        menu.setItem(14, d12);
        menu.setItem(15, d20);
        menu.setItem(16, d100);

        player.openInventory(menu);
    }

    private SkullMeta addItemMeta(ItemStack dice, String display_name, List lore, String url) {
        SkullMeta meta = (SkullMeta) dice.getItemMeta();
        meta.setDisplayName(display_name);
        meta.setLore(lore);
        meta.setOwnerProfile(createProfile(url));
        return meta;
    }

    private PlayerProfile createProfile(String url) {
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();

        URL urlObject;

        try {
            urlObject = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        textures.setSkin(urlObject);
        profile.setTextures(textures);

        return profile;
    }

    private boolean checkForItemInHand(PlayerInteractEvent event) {
        return event.getItem() != null && event.getItem().getType() == Material.PLAYER_HEAD && (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK));
    }
}
