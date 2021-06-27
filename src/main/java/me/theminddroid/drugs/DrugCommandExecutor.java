package me.theminddroid.drugs;

import me.theminddroid.drugs.models.Drug;
import me.theminddroid.drugs.models.DrugItems;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DrugCommandExecutor implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            System.out.println("You must be a player to run this command.");
            return true;
        }

        Player player = (Player) sender;
        if (!(player.hasPermission("drugs.admin"))) {
            player.sendMessage(ChatColor.RED + "You do not have the required permissions to run this command.");
            return true;
        }

        if (args.length == 0) {
            sendHelp(player);
            return true;
        }

        Drug drug = Drug.getByNameCaseInsensitive(args[0]);

        if (drug == null) {
            player.sendMessage(ChatColor.RED + "Please select a valid drug.");
            return true;
        }

        player.sendMessage(ChatColor.DARK_GREEN + "You spawned " + ChatColor.GOLD + drug.name() + ChatColor.DARK_GREEN + ".");
        ItemStack item = DrugItems.createItemStackForDrug(drug);

        player.getInventory().addItem(item);
        player.updateInventory();

        return true;
    }

    private void sendHelp(Player player) {
        player.sendMessage(ChatColor.DARK_GREEN + "§lPlease select one of the following drugs:");
        for (Drug value : Drug.values()) {
            player.sendMessage(ChatColor.DARK_GREEN + " - " + ChatColor.GOLD + value.name());
        }
        player.sendMessage(ChatColor.DARK_GREEN + " - " + ChatColor.YELLOW + "Narcan");
        player.sendMessage(ChatColor.DARK_GREEN + "Usage: " + ChatColor.GOLD + "/drugs <drug>" + ChatColor.DARK_GREEN
                + " to spawn.");
    }
}