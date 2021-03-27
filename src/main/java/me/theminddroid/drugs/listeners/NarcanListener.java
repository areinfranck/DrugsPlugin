package me.theminddroid.drugs.listeners;

import me.theminddroid.drugs.models.Drug;
import me.theminddroid.drugs.states.DrugUsageState;
import me.theminddroid.drugs.DrugsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
        if (event.getItem().getType() != Material.MILK_BUCKET) {
            return;
        }

        if (Objects.requireNonNull(event.getItem().getItemMeta()).getDisplayName().equals(ChatColor.RED + "Narcan")) {
            useNarcan(event);
            return;
        }
        cancelIfPlayerOnDrugs(event);
    }

    private void cancelIfPlayerOnDrugs(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        if (Arrays.stream(Drug.values()).noneMatch(drug -> playerIsOnDrugs(player, drug) || playerIsOnDrugWithdrawal(player, drug))) {
            return;
        }

        event.setCancelled(true);
        player.sendMessage(ChatColor.RED + "You must use Narcan to cancel the effects of drugs...");
    }

    private void useNarcan(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        for (Drug drug : Drug.values()) {
            DrugUsageState drugUsageState = getDrugUsage(player, drug);
            if (drugUsageState == null) {
                continue;
            }

            drugUsageState.getWithdrawalTask().cancel();
            player.removeMetadata(getPreviousDrugUsageKey(drug), DrugsPlugin.getInstance());

        }
        player.sendMessage(ChatColor.DARK_GREEN + "Your mind feels clear now...");
    }
}