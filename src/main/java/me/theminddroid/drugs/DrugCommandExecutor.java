package me.theminddroid.drugs;

import me.theminddroid.drugs.models.Drug;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

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
            player.sendMessage(ChatColor.DARK_GREEN + "§lPlease select one of the following drugs:");
            for (Drug value : Drug.values()) {
                player.sendMessage(ChatColor.DARK_GREEN + " - " + ChatColor.GOLD + value.name());
            }
            player.sendMessage(ChatColor.DARK_GREEN + " - " + ChatColor.YELLOW + "Narcan");
            player.sendMessage(ChatColor.DARK_GREEN + "Usage: " + ChatColor.GOLD + "/drugs <drug>" + ChatColor.DARK_GREEN
                    + " to spawn.");
            return true;
        }

        ItemStack item = null;
        if (args[0].toLowerCase().equals("narcan")) {
            item = createNarcanItem();
        } else {
            item = createDrugItem(player, args[0].toLowerCase());
        }

        if (null != item) {
            player.getInventory().addItem(item);
            player.updateInventory();
        }
        return true;
    }

    private ItemStack createNarcanItem() {
        return getItemStack(Material.MILK_BUCKET, ChatColor.RED + "Narcan", ChatColor.DARK_GREEN
                + "Removes all " + ChatColor.GOLD + "drug " + ChatColor.DARK_GREEN + "and" + ChatColor.GOLD
                + " withdrawal " + ChatColor.DARK_GREEN + "effects.");
    }

    private ItemStack createDrugItem(Player player, String name) {
        Drug drug = Drug.getByNameCaseInsensitive(name);

        if (drug == null) {
            player.sendMessage(ChatColor.RED + "Please select a valid drug.");
            return null;
        }

        player.sendMessage(ChatColor.DARK_GREEN + "You spawned " + ChatColor.GOLD + drug.name() + ChatColor.DARK_GREEN
                + ".");
        return createItemStackForDrug(drug);

    }

    private ItemStack createItemStackForDrug(Drug drug) {
        return getItemStack(
                drug.getMaterial(),
                drug.getDisplayName(),
                ChatColor.DARK_GREEN + "Grants " + ChatColor.GOLD + drug.getEffectName() + ChatColor.DARK_GREEN
                        + " for two minutes.",
                ChatColor.DARK_GREEN + "Shift click to use.");
    }

    private ItemStack getItemStack(Material material, String displayName, String... message) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        meta.addEnchant(DrugsPlugin.getInstance().createGlowEnchant(), 1 , true);
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(message));
        stack.setItemMeta(meta);
        return stack;
    }
}