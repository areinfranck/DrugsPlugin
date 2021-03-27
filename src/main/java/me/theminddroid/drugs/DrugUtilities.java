package me.theminddroid.drugs;

import me.theminddroid.drugs.models.Drug;
import me.theminddroid.drugs.states.DrugUsageState;
import me.theminddroid.drugs.states.DrugWithdrawalState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;

import java.util.List;
import java.util.Objects;

public class DrugUtilities {
    public static boolean playerIsOnDrugs(Player player, Drug drug) {
        return getDrugUsage(player, drug) != null;
    }

    public static boolean playerIsOnDrugWithdrawal(Player player, Drug drug) {
        DrugWithdrawalState drugWithdrawalState = getDrugWithdrawal(player, drug);
        return drugWithdrawalState != null && drugWithdrawalState.withdrawalIsActive(getFullTime());
    }

    private static long getFullTime() {
        return Bukkit.getServer().getWorlds().get(0).getFullTime();
    }

    public static void setPlayerWithdrawal(Player player, Drug drug) {
        player.setMetadata(getDrugWithdrawalKey(drug), new FixedMetadataValue(DrugsPlugin.getInstance(), new DrugWithdrawalState(getFullTime())));
    }

    public static String getPreviousDrugUsageKey(Drug drug) {
        return "previousDrugUsage" + drug.name();
    }

    public static String getDrugWithdrawalKey(Drug drug) {
        return "drugWithdrawal" + drug.name();
    }

    public static DrugUsageState getDrugUsage(Metadatable metadatable, Drug drug) {
        MetadataValue previousDrugUsageValue = getOwnMetaData(metadatable, getPreviousDrugUsageKey(drug));
        if (previousDrugUsageValue == null) {
            return null;
        }
        return (DrugUsageState) previousDrugUsageValue.value();
    }

    public static DrugWithdrawalState getDrugWithdrawal(Metadatable metadatable, Drug drug) {
        MetadataValue drugWithdrawalValue = getOwnMetaData(metadatable, getDrugWithdrawalKey(drug));
        if (drugWithdrawalValue == null) {
            return null;
        }
        return (DrugWithdrawalState) drugWithdrawalValue.value();
    }

    public static MetadataValue getOwnMetaData(Metadatable metadatable, String key) {
        List<MetadataValue> metadataValues = metadatable.getMetadata(key);
        for (MetadataValue metadataValue : metadataValues) {
            if (Objects.equals(metadataValue.getOwningPlugin(), DrugsPlugin.getInstance())) {
                return metadataValue;
            }
        }
        return null;
    }
}