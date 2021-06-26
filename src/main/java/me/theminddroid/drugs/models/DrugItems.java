package me.theminddroid.drugs.models;

import me.theminddroid.drugs.DrugsPlugin;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class DrugItems {
    public static ItemStack createItemStackForDrug(Drug drug) {
        String[] messages = getDrugMessages(drug);

        return getItemStack(
                drug.getMaterial(),
                drug.getDisplayName(),
                messages);
    }

    public static Drug tryGetDrug(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) {
            return null;
        }

        String itemName = itemMeta.getDisplayName();

        Drug drug = Drug.getByDisplayName(itemName);

        if (drug == null) {
            return null;
        }

        if (itemStack.getType() != drug.getMaterial()) {
            return null;
        }

        return drug;
    }

    private static String[] getDrugMessages(Drug drug) throws NotImplementedException {
        if (drug.getDrugType() instanceof DrugType.PsychoActive) {
            return getPsychoActiveMessages(drug);
        } else if (drug.getDrugType() instanceof DrugType.Narcan) {
            return getNarcanMessages();
        } else {
            throw new NotImplementedException("Getting the messages for drug type " + drug.getDrugType().getClass().getName() + " is not implemented");
        }
    }

    private static String[] getNarcanMessages() {
        return new String[]{ChatColor.DARK_GREEN
                + "Removes all " + ChatColor.GOLD + "drug " + ChatColor.DARK_GREEN + "and" + ChatColor.GOLD
                + " withdrawal " + ChatColor.DARK_GREEN + "effects."};
    }

    private static String[] getPsychoActiveMessages(Drug drug) {
        return new String[]{ChatColor.DARK_GREEN + "Grants " + ChatColor.GOLD + drug.getEffectName() + ChatColor.DARK_GREEN
                + " for two minutes.",
                ChatColor.DARK_GREEN + "Shift click to use."};
    }

    private static ItemStack getItemStack(Material material, String displayName, String... message) {
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        assert meta != null;
        meta.addEnchant(DrugsPlugin.getInstance().createGlowEnchant(), 1, true);
        meta.setDisplayName(displayName);
        meta.setLore(Arrays.asList(message));
        stack.setItemMeta(meta);
        return stack;
    }
}
