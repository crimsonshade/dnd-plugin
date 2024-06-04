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

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RollInteractListener implements Listener {
    private final FileConfiguration config;

    public RollInteractListener(FileConfiguration config) {
        this.config = config;
    }

    @EventHandler
    public void onItemInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();


        if (checkForItemInHand(event)) {
            event.setCancelled(true);
            createClickableDice(player, config);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', config.getString("dice.menu.title")))) {
                if (event.isRightClick()) return;

                event.setCancelled(true);

                Random random = new Random();
                String itemName = ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName());

                switch (itemName) {
                    case "W4":
                        int rand_d4 = random.nextInt(4) + 1;
                        player.sendMessage(ChatColor.RED + "W4: " + rand_d4);
                        break;
                    case "W6":
                        int rand_d6 = random.nextInt(6) + 1;
                        player.sendMessage(ChatColor.AQUA + "W6: " + rand_d6);
                        break;
                    case "W8":
                        int rand_d8 = random.nextInt(8) + 1;
                        player.sendMessage(ChatColor.GREEN + "W8: " + rand_d8);
                        break;
                    case "W10":
                        int rand_d10 = random.nextInt(10) + 1;
                        player.sendMessage(ChatColor.LIGHT_PURPLE + "W10: " + rand_d10);
                        break;
                    case "W12":
                        int rand_d12 = random.nextInt(12) + 1;
                        player.sendMessage(ChatColor.DARK_BLUE + "W12: " + rand_d12);
                        break;
                    case "W20":
                        int rand_d20 = random.nextInt(20) + 1;
                        player.sendMessage(ChatColor.GOLD + "W20: " + rand_d20);
                        break;
                    case "W100":
                        int rand_d100 = random.nextInt(100) + 1;
                        player.sendMessage(ChatColor.DARK_GRAY + "W100: " + rand_d100);
                        break;
                }
            }
        }
    }

    private void createClickableDice(Player player, FileConfiguration config) {
        Inventory menu = Bukkit.createInventory(player, 27, ChatColor.translateAlternateColorCodes('&', config.getString("dice.menu.title")));

        ItemStack d4 = createDiceItem(config.getString("dice.die.d4.title"), config.getString("dice.die.d4.skin"), config.getStringList("dice.die.d4.lore"));
        ItemStack d6 = createDiceItem(config.getString("dice.die.d6.title"), config.getString("dice.die.d6.skin"), config.getStringList("dice.die.d6.lore"));
        ItemStack d8 = createDiceItem(config.getString("dice.die.d8.title"), config.getString("dice.die.d8.skin"), config.getStringList("dice.die.d8.lore"));
        ItemStack d10 = createDiceItem(config.getString("dice.die.d10.title"), config.getString("dice.die.d10.skin"), config.getStringList("dice.die.d10.lore"));
        ItemStack d12 = createDiceItem(config.getString("dice.die.d12.title"), config.getString("dice.die.d12.skin"), config.getStringList("dice.die.d12.lore"));
        ItemStack d20 = createDiceItem(config.getString("dice.die.d20.title"), config.getString("dice.die.d20.skin"), config.getStringList("dice.die.d20.lore"));
        ItemStack d100 = createDiceItem(config.getString("dice.die.d100.title"), config.getString("dice.die.d100.skin"), config.getStringList("dice.die.d100.lore"));

        menu.setItem(10, d4);
        menu.setItem(11, d6);
        menu.setItem(12, d8);
        menu.setItem(13, d10);
        menu.setItem(14, d12);
        menu.setItem(15, d20);
        menu.setItem(16, d100);

        player.openInventory(menu);
    }

    private ItemStack createDiceItem(String displayName, String url, List<String> lore) {
        ItemStack dice = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) dice.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        meta.setLore(lore);
        meta.setOwnerProfile(createProfile(url));
        dice.setItemMeta(meta);

        return dice;
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
