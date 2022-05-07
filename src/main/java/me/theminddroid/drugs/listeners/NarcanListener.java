package me.theminddroid.drugs.listeners;

import me.theminddroid.drugs.models.Drug;
import me.theminddroid.drugs.states.DrugUsageState;
import me.theminddroid.drugs.DrugsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

import java.util.Arrays;
import java.util.Objects;

import static me.theminddroid.drugs.DrugUtilities.*;

public class NarcanListener implements Listener {
    @EventHandler
    public void onDrinkMilk(PlayerItemConsumeEvent event) {

        FileConfiguration messageConfig = DrugsPlugin.getPlugin(DrugsPlugin.class).getConfig();

        if (event.getItem().getType() != Material.MILK_BUCKET) {
            return;
        }

        if (Objects.requireNonNull(event.getItem().getItemMeta()).getDisplayName().equals(ChatColor.RED + "Narcan")) {
            useNarcan(event, messageConfig);
            return;
        }
        cancelIfPlayerOnDrugs(event, messageConfig);
    }

    private void cancelIfPlayerOnDrugs(PlayerItemConsumeEvent event, FileConfiguration messageConfig) {
        Player player = event.getPlayer();
        if (Arrays.stream(Drug.values()).noneMatch(drug -> playerIsOnDrugs(player, drug) || playerIsOnDrugWithdrawal(player, drug))) {
            return;
        }

        event.setCancelled(true);
        player.sendMessage(Objects.requireNonNull(messageConfig.getString("milkMessage")));
    }

    private void useNarcan(PlayerItemConsumeEvent event, FileConfiguration messageConfig) {
        Player player = event.getPlayer();

        if (!messageConfig.getBoolean("NarcanUse.enabled")) {

            if (messageConfig.getBoolean("drugMessage.enabled")) {
                event.getPlayer().sendMessage(Objects.requireNonNull(messageConfig.getString("drugDisabled")));
            }

            event.setCancelled(true);
            return;
        }

        for (Drug drug : Drug.values()) {
            DrugUsageState drugUsageState = getDrugUsage(player, drug);
            if (drugUsageState == null) {
                continue;
            }

            drugUsageState.getWithdrawalTask().cancel();
            player.removeMetadata(getPreviousDrugUsageKey(drug), DrugsPlugin.getInstance());

        }
        player.sendMessage(Objects.requireNonNull(messageConfig.getString("consumeNarcan")));
    }
}