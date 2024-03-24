package me.theminddroid.drugs;

import me.theminddroid.drugs.listeners.NarcanListener;
import me.theminddroid.drugs.listeners.PsychoactiveDrugListener;
import me.theminddroid.drugs.models.Drug;
import me.theminddroid.drugs.models.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class DrugsPlugin extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        Bukkit.getLogger().info("[Drugs] Drugs by TheMindDroid.");

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        FileConfiguration messageConfig = DrugsPlugin.getPlugin(DrugsPlugin.class).getConfig();

        getServer().getPluginManager().registerEvents(new PsychoactiveDrugListener(), this);
        getServer().getPluginManager().registerEvents(new NarcanListener(), this);

        Objects.requireNonNull(getCommand("drugs")).setExecutor(new DrugCommandExecutor());
        Objects.requireNonNull(this.getCommand("drugs")).setTabCompleter(new DrugTabCompleter());

        new Metrics(this, 10825);

        if (!messageConfig.getBoolean("allRecipes.enabled"))
        {
            Bukkit.getLogger().info("[Drugs] Drug recipes have been disabled. Visit config file to change.");
            return;
        }

        Bukkit.getLogger().info("[Drugs] Building drug recipes:");

        for (Drug recipe : Drug.values())
        {
            if (!messageConfig.getBoolean(recipe.getDrugName() + "Recipe.enabled")) continue;

            Bukkit.getLogger().info("[Drugs] Generating recipe for " + recipe.getDrugName() + "...");
            Recipe drugRecipe = Recipes.getDrugRecipe(this, recipe);
            if (drugRecipe != null) getServer().addRecipe(drugRecipe);
        }
    }

    public static DrugsPlugin getInstance()
    {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Drugs");
        if (!(plugin instanceof DrugsPlugin)) {
            throw new RuntimeException("'Drugs' not found. 'Drugs' plugin disabled?");
        }
        return ((DrugsPlugin) plugin);
    }

    @Override
    public void onDisable()
    {
        for (Drug recipe : Drug.values())
        {
            Bukkit.getLogger().info("[Drugs] Unregistering recipe for " + recipe.getDrugName() + "...");
            getServer().removeRecipe(Recipes.getKey(this, recipe));
        }
        Bukkit.getLogger().info("Drugs plugin has terminated.");
    }
}