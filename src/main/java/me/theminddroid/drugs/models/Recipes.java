package me.theminddroid.drugs.models;

import me.theminddroid.drugs.DrugCommandExecutor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class Recipes {

    private ShapedRecipe drugRecipe;
    private final String drugName;
    private final Material materialOne;
    private final Material materialTwo;
    private final Material materialThree;

    public Recipes(String drugName, Material materialOne, Material materialTwo, Material materialThree) {
        this.drugName = drugName;
        this.materialOne = materialOne;
        this.materialTwo = materialTwo;
        this.materialThree = materialThree;
    }

    private void buildRecipe(String drugName, Material materialOne, Material materialTwo, Material materialThree) {

        Drug drug = Drug.getByNameCaseInsensitive(drugName);
        ItemStack drugStack = new DrugCommandExecutor().createItemStackForDrug(drug);
        drugRecipe = new ShapedRecipe(NamespacedKey.minecraft(drugName.toLowerCase()), drugStack);

        drugRecipe.shape(" X ", " Y ", " Z ");
        drugRecipe.setIngredient('X', materialOne);
        drugRecipe.setIngredient('Y', materialTwo);
        drugRecipe.setIngredient('Z', materialThree);
    }

    public ShapedRecipe getDrugRecipe() {
        buildRecipe(drugName, materialOne, materialTwo, materialThree);
        return drugRecipe;
    }
}