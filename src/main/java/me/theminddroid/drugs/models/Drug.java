package me.theminddroid.drugs.models;

import org.bukkit.ChatColor;
import org.bukkit.Material;

public record Drug(String name, Material material, String effectName, DrugType drugType, DrugRecipe recipe) {

    public String getDrugName() {
        return this.name;
    }

    public String getDisplayName() {
        return ChatColor.RED + name;
    }
}