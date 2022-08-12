package me.theminddroid.drugs.listeners;

import me.theminddroid.drugs.DrugManager;
import me.theminddroid.drugs.models.Drug;
import me.theminddroid.drugs.models.DrugItems;
import me.theminddroid.drugs.models.DrugType;
import me.theminddroid.drugs.states.DrugUsageState;
import me.theminddroid.drugs.DrugsPlugin;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

import static me.theminddroid.drugs.DrugUtilities.*;

public class PsychoactiveDrugListener implements Listener
{

    FileConfiguration messageConfig = DrugsPlugin.getPlugin(DrugsPlugin.class).getConfig();

    @EventHandler
    public void onDrugConsume(PlayerItemConsumeEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        Drug drug = tryGetPsychoActiveDrug(itemInMainHand);
        if (drug == null) return;

        player.sendMessage(Objects.requireNonNull(messageConfig.getString("verifyUsage")));
        event.setCancelled(true);
    }

    @EventHandler
    public void onDrugUse(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        if (!player.isSneaking()) return;

        Drug drug = tryGetPsychoActiveDrug(itemInMainHand);
        if (drug == null) return;

        if (player.hasPermission("drugs.disable") && !player.isOp())
        {
            player.sendMessage(Objects.requireNonNull(messageConfig.getString("drugPermission")));
            return;
        }

        if (!DrugManager.isDrugEnabled(drug))
        {
            if (messageConfig.getBoolean("drugMessage.enabled"))
            {
                player.sendMessage(Objects.requireNonNull(messageConfig.getString("drugDisabled")));
            }
            return;
        }

        if (player.getGameMode() == GameMode.SURVIVAL && !player.isDead())
        {
            int itemAmount = itemInMainHand.getAmount();
            itemInMainHand.setAmount(itemAmount - 1);
        }

        DrugType.PsychoActive drugType = (DrugType.PsychoActive) drug.drugType();

        player.playSound(player.getLocation(), drugType.getConsumeSound(), 10.f, (float) (1.7 + .2 * Math.random()));
        player.addPotionEffect((new PotionEffect(drugType.getEffect().effectType(), 2400, 2)));
        player.sendMessage(Objects.requireNonNull(messageConfig.getString("consumeStart"))
                + drug.name()
                + Objects.requireNonNull(messageConfig.getString("consumeMiddle"))
                + drugType.getEffect().message()
                + Objects.requireNonNull(messageConfig.getString("consumeEnd"))
        );

        DrugUsageState drugUsageState = getDrugUsage(player, drug);

        int amountOfTimesUsed = 1;

        if (drugUsageState != null)
        {
            amountOfTimesUsed += drugUsageState.getAmountOfTimesUsed();
            drugUsageState.getWithdrawalTask().cancel();
            player.removeMetadata(getPreviousDrugUsageKey(drug), DrugsPlugin.getInstance());
        }

        if (amountOfTimesUsed == 4)
        {
            player.sendMessage(Objects.requireNonNull(messageConfig.getString("passingMessage")));
            player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 10.f, 0.5f);
        }

        //number of times to take the drug before it kills you
        if (amountOfTimesUsed >= 5)
        {
            Bukkit.getScheduler().runTaskLater(DrugsPlugin.getInstance(), () -> player.setHealth(0), 1);
            player.sendMessage(Objects.requireNonNull(messageConfig.getString("overdoseMessage")));
            return;
        }

        BukkitTask bukkitTask = Bukkit.getScheduler().runTaskLater(DrugsPlugin.getInstance(), () -> {

            if (player.isOnline()) {
                player.playSound(player.getLocation(), Sound.ENTITY_GENERIC_HURT, 10.f, 1.7f);
                player.addPotionEffect((new PotionEffect(drugType.getWithdrawalEffect().effectType(), 600, 2)));
                player.addPotionEffect((new PotionEffect(PotionEffectType.CONFUSION, 200, 3)));
                player.sendMessage(Objects.requireNonNull(messageConfig.getString("dizzyMessage")));
                player.sendMessage(Objects.requireNonNull(messageConfig.getString("withdrawalMessageStart"))
                        + drugType.getWithdrawalEffect().message()
                        + Objects.requireNonNull(messageConfig.getString("withdrawalMessageEnd"))
                );
                player.removeMetadata(getPreviousDrugUsageKey(drug), DrugsPlugin.getInstance());

                setPlayerWithdrawal(player, drug);
            }
        }, 2401);

        player.setMetadata(getPreviousDrugUsageKey(drug), new FixedMetadataValue(DrugsPlugin.getInstance(),
                new DrugUsageState(amountOfTimesUsed, bukkitTask)));
    }

    private Drug tryGetPsychoActiveDrug(ItemStack itemStack)
    {
        Drug drug = DrugItems.tryGetDrug(itemStack);
        if (drug == null) return null;
        if (!(drug.drugType() instanceof DrugType.PsychoActive)) return null;

        return drug;
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event)
    {
        for (Drug drug : DrugManager.getActiveDrugs())
        {
            DrugUsageState drugUsageState = getDrugUsage(event.getEntity(), drug);
            if (drugUsageState != null) drugUsageState.getWithdrawalTask().cancel();
        }
    }
}
