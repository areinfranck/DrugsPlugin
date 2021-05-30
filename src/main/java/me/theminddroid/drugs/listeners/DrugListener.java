package me.theminddroid.drugs.listeners;

import me.theminddroid.drugs.models.Drug;
import me.theminddroid.drugs.states.DrugUsageState;
import me.theminddroid.drugs.DrugsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import static me.theminddroid.drugs.DrugUtilities.*;

public class DrugListener implements Listener {

    @EventHandler
    public void onDrugUse(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemInMainHand.getItemMeta();

        if (itemMeta == null) {
            return;
        }

        String itemName = itemMeta.getDisplayName();

        if (!player.isSneaking()) {
            return;
        }

        Drug drug = Drug.getByDisplayName(itemName);

        if (drug == null) {
            return;
        }

        if (itemInMainHand.getType() != drug.getMaterial()) {
            return;
        }

        if (player.hasPermission("drugs.disable")) {
            player.sendMessage(ChatColor.RED + "You do not have the required permissions to take drugs.");
            return;
        }

        if (player.getGameMode() == GameMode.SURVIVAL) {
            int itemAmount = itemInMainHand.getAmount();
            itemInMainHand.setAmount(itemAmount - 1);
        }

        player.playSound(player.getLocation(), drug.getConsumeSound(), 10.f, (float) (1.7 + .2 * Math.random()));
        player.addPotionEffect((new PotionEffect(drug.getEffect().getEffectType(), 2400, 2)));
        player.sendMessage(ChatColor.DARK_GREEN + "§lYou took " + ChatColor.GOLD + "§l" + drug.name().toLowerCase()
                + ChatColor.DARK_GREEN + " §land " + drug.getEffect().getMessage() + " for " + ChatColor.GOLD
                + "§l2 minutes" + ChatColor.DARK_GREEN + "§l!");

        DrugUsageState drugUsageState = getDrugUsage(player, drug);

        int amountOfTimesUsed = 1;

        if (drugUsageState != null) {
            amountOfTimesUsed += drugUsageState.getAmountOfTimesUsed();
            drugUsageState.getWithdrawalTask().cancel();
            player.removeMetadata(getPreviousDrugUsageKey(drug), DrugsPlugin.getInstance());
        }

        if (amountOfTimesUsed == 4) {
            player.sendMessage(ChatColor.RED + "I feel like passing out...");
            player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 10.f, 0.5f);
        }

        //number of times to take the drug before it kills you
        if (amountOfTimesUsed >= 5) {
            player.setHealth(0);
            player.sendMessage(ChatColor.RED + "You overdosed and died...");
            return;
        }

        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskLater(DrugsPlugin.getInstance(), () -> {

            if (player.isOnline()) {

                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_HURT, 10.f, 1.7f);
                player.addPotionEffect((new PotionEffect(drug.getWithdrawalEffect().getEffectType(), 600, 2)));
                player.addPotionEffect((new PotionEffect(PotionEffectType.CONFUSION, 200, 3)));
                player.sendMessage(ChatColor.RED + "" + ChatColor.ITALIC + "Uh oh, I don't feel so good...");
                player.sendMessage(ChatColor.DARK_GREEN + "§lYou're suffering from " + ChatColor.GOLD + "§lwithdrawal"
                        + ChatColor.DARK_GREEN + " §land " + drug.getWithdrawalEffect().getMessage() + " for "
                        + ChatColor.GOLD + "§l30 seconds" + ChatColor.DARK_GREEN + "§l...");
                player.removeMetadata(getPreviousDrugUsageKey(drug), DrugsPlugin.getInstance());
                setPlayerWithdrawal(player, drug);
            }
        }, 2401);

        player.setMetadata(getPreviousDrugUsageKey(drug), new FixedMetadataValue(DrugsPlugin.getInstance(),
                new DrugUsageState(amountOfTimesUsed, bukkitTask)));
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        for (Drug drug : Drug.values()) {

            DrugUsageState drugUsageState = getDrugUsage(event.getEntity(), drug);

            if (drugUsageState != null) {
                drugUsageState.getWithdrawalTask().cancel();
            }
        }
    }
}