package me.theminddroid.drugs;

import me.theminddroid.drugs.models.Drug;
import me.theminddroid.drugs.models.DrugItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class DrugCommandExecutor implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        FileConfiguration messageConfig = DrugsPlugin.getPlugin(DrugsPlugin.class).getConfig();

        if (!(sender instanceof Player player))
        {
            Bukkit.getLogger().info("You must be a player to run this command.");
            return true;
        }

        if (!(player.hasPermission("drugs.admin")))
        {
            player.sendMessage(Objects.requireNonNull(messageConfig.getString("verifyPermission")));
            return true;
        }

        if (args.length == 0)
        {
            sendHelp(player, messageConfig);
            return true;
        }

        Drug drug = Drug.getByNameCaseInsensitive(args[0]);

        if (drug == null)
        {
            player.sendMessage(Objects.requireNonNull(messageConfig.getString("verifyDrug")));
            return true;
        }

        player.sendMessage(messageConfig.getString("announceDrug") + ChatColor.GOLD + drug.name() + ChatColor.DARK_GREEN + ".");
        ItemStack item = DrugItems.createItemStackForDrug(drug);

        player.getInventory().addItem(item);
        player.updateInventory();

        return true;
    }

    private void sendHelp(Player player, FileConfiguration messageConfig)
    {
        player.sendMessage(Objects.requireNonNull(messageConfig.getString("selectDrug")));

        for (Drug value : Drug.values())
            player.sendMessage(ChatColor.DARK_GREEN + " - " + ChatColor.GOLD + value.name());

        player.sendMessage(Objects.requireNonNull(messageConfig.getString("usageMessage")));
    }
}