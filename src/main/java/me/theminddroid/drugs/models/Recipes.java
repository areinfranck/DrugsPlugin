package me.theminddroid.drugs.models;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.Plugin;

public class Recipes
{
    public static Recipe getDrugRecipe(Plugin plugin, Drug drug)
    {
        ShapedRecipe drugRecipe = new ShapedRecipe(
                getKey(plugin, drug),
                DrugItems.createItemStackForDrug(drug)
        );

        if (drug.getRecipe() instanceof DrugRecipe.VerticalShaped recipe)
        {

            drugRecipe.shape(" X ", " Y ", " Z ");
            drugRecipe.setIngredient('X', recipe.getTop());
            drugRecipe.setIngredient('Y', recipe.getMiddle());
            drugRecipe.setIngredient('Z', recipe.getBottom());
            return drugRecipe;
        }

        return null;
    }

    public static NamespacedKey getKey(Plugin plugin, Drug drug)
    {
        return new NamespacedKey(plugin, drug.getDrugName());
    }
}