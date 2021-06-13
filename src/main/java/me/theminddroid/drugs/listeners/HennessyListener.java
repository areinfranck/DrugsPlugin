package me.theminddroid.drugs.listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.Objects;

public class HennessyListener implements Listener {

    @EventHandler
    public void onDrinkHennessy(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.HONEY_BOTTLE) {
            return;
        }

        if (Objects.requireNonNull(event.getItem().getItemMeta()).getDisplayName().equals(ChatColor.RED + "Hennessy")) {
            Player player = event.getPlayer();
            player.sendMessage(ChatColor.RED + "You must SHIFT + RIGHT-CLICK to consume drugs...");
            event.setCancelled(true);
        }
    }
}