package me.theminddroid.drugs.models;

import me.theminddroid.drugs.DrugsPlugin;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class DrugItems
{
    public static ItemStack createItemStackForDrug(Drug drug)
    {
        FileConfiguration messageConfig = DrugsPlugin.getPlugin(DrugsPlugin.class).getConfig();

        String[] messages = getDrugMessages(drug, messageConfig);

        return getItemStack(
                drug.getMaterial(),
                drug.getDisplayName(),
                messages);
    }

    public static Drug tryGetDrug(ItemStack itemStack)
    {
        ItemMeta itemMeta = itemStack.getItemMeta();

        if (itemMeta == null) return null;

        String itemName = itemMeta.getDisplayName();
        Drug drug = Drug.getByDisplayName(itemName);

        if (drug == null) return null;
        if (itemStack.getType() != drug.getMaterial()) return null;

        return drug;
    }

    private static String[] getDrugMessages(Drug drug, FileConfiguration messageConfig) throws NotImplementedException
    {
        if (drug.getDrugType() instanceof DrugType.PsychoActive) return getPsychoActiveMessages(drug, messageConfig);
        else if (drug.getDrugType() instanceof DrugType.Narcan) return new String[] {messageConfig.getString("narcanMessage")};
        else throw new NotImplementedException("Getting the messages for drug type " + drug.getDrugType().getClass().getName() + " is not implemented");
    }

    private static String[] getPsychoActiveMessages(Drug drug, FileConfiguration messageConfig)
    {
        return new String[] {messageConfig.getString("messageStartPsych") + drug.getEffectName() + messageConfig.getString("messageEndPsych"),
                messageConfig.getString("useInstructionPsych")};
    }

    private static ItemStack getItemStack(Material material, String displayName, String... message)
    {
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
