package me.theminddroid.drugs;

import me.theminddroid.drugs.listeners.DrugListener;
import me.theminddroid.drugs.listeners.NarcanListener;
import me.theminddroid.drugs.models.Glow;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Objects;

public final class DrugsPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Drugs plugin has started.");
        getServer().getPluginManager().registerEvents(new DrugListener(), this);
        getServer().getPluginManager().registerEvents(new NarcanListener(), this);
        Objects.requireNonNull(getCommand("drugs")).setExecutor(new DrugCommandExecutor());
        registerGlow();

        Metrics metrics = new Metrics(this, 10825);
    }

    public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            NamespacedKey key = new NamespacedKey(this, getDescription().getName());

            Glow glow = new Glow(key);
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException ignored){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public Glow createGlowEnchant() {
        NamespacedKey key = new NamespacedKey(this, getDescription().getName());
        return new Glow(key);
    }

    public static DrugsPlugin getInstance() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Drugs");
        if (!(plugin instanceof DrugsPlugin)) {
            throw new RuntimeException("'Drugs' not found. 'Drugs' plugin disabled?");
        }
        return ((DrugsPlugin) plugin);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Drugs plugin has terminated.");
    }
}