package xyz.crmsn.minecraft.dnd.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import xyz.crmsn.minecraft.dnd.CRMSN;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

public class JoinListener implements Listener {
    private final FileConfiguration config;

    public JoinListener(FileConfiguration config) {
        this.config = config;
    }

    private final UUID randomID = UUID.randomUUID();

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String url = "https://textures.minecraft.net/texture/8a084d0a1c6fc2163de30d8b148ab4d363220d5c972d5f88eb8dc86176ccdb3e";

        player.getInventory().setItem(8, createCustomHead(url));
    }

    public PlayerProfile createProfile(String url) {
        PlayerProfile profile = Bukkit.createPlayerProfile(randomID);
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

    public ItemStack createCustomHead(String url) {
        FileConfiguration config = config.getConfig();
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta rollMeta = (SkullMeta) head.getItemMeta();
        assert rollMeta != null;
        rollMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', config.getString("dice.menu.title")));
        rollMeta.setLore(config.getStringList("dice.menu.lore"));
        rollMeta.setOwnerProfile(createProfile(url));
        head.setItemMeta(rollMeta);

        return head;
    }
}
