package me.theminddroid.drugs.models;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public enum Drug {
    Narcan(
            Material.MILK_BUCKET,
            "narcan",
            new DrugType.Narcan(),
            new DrugRecipe.None()),
    Cocaine(
            Material.SUGAR,
            "speed",
            new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                    new DrugEffect(PotionEffectType.SPEED, "gained a speed buff"),
                    new DrugEffect(PotionEffectType.SLOW, "gained a speed reduction")),
            new DrugRecipe.VerticalShaped(Material.SUGAR, Material.COCOA_BEANS, Material.PAPER)),
    Heroin(
            Material.WHITE_DYE,
            "regeneration",
            new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                    new DrugEffect(PotionEffectType.REGENERATION, "gained a regeneration buff"),
                    new DrugEffect(PotionEffectType.WEAKNESS, "have become weak")),
            new DrugRecipe.VerticalShaped(Material.SUGAR, Material.WHEAT_SEEDS, Material.PAPER)),
    Adderall(
            Material.GOLD_NUGGET,
            "haste",
            new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                    new DrugEffect(PotionEffectType.FAST_DIGGING, "gained a digging speed buff"),
                    new DrugEffect(PotionEffectType.SLOW_DIGGING, "gained a digging speed reduction")),
            new DrugRecipe.VerticalShaped(Material.SUGAR, Material.HONEYCOMB, Material.PAPER)),
    Steroids(
            Material.PRISMARINE_CRYSTALS,
            "strength",
            new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                    new DrugEffect(PotionEffectType.INCREASE_DAMAGE, "gained a strength buff"),
                    new DrugEffect(PotionEffectType.WEAKNESS, "are weakened")),
            new DrugRecipe.VerticalShaped(Material.SUGAR, Material.BONE_MEAL, Material.PAPER)),
    Hennessy(
            Material.HONEY_BOTTLE,
            "luck",
            new DrugType.PsychoActive(Sound.ENTITY_GENERIC_DRINK,
                    new DrugEffect(PotionEffectType.LUCK, "gained luck"),
                    new DrugEffect(PotionEffectType.POISON, "are poisoned")),
            new DrugRecipe.VerticalShaped(Material.WHEAT, Material.SWEET_BERRIES, Material.GLASS_BOTTLE));

    private static final Map<String, Drug> byDisplayName = new HashMap<>();
    private static final Map<String, Drug> byLowerCaseName = new HashMap<>();

    static {
        for (Drug value : values()) {
            byDisplayName.put(value.getDisplayName(), value);
            byLowerCaseName.put(value.name().toLowerCase(), value);
        }
    }

    private final Material material;
    private final String effectName;
    private final DrugType drugType;
    private final DrugRecipe recipe;

    Drug(Material material, String effectName, DrugType drugType, DrugRecipe recipe) {
        this.effectName = effectName;
        this.drugType = drugType;
        this.recipe = recipe;
        this.material = material;
    }

    public String getDrugName() {
        return this.name();
    }

    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return ChatColor.RED + name();
    }

    public String getEffectName() {
        return effectName;
    }

    public DrugType getDrugType() {
        return drugType;
    }

    public DrugRecipe getRecipe() {
        return recipe;
    }

    public static Drug getByDisplayName(String name) {
        return byDisplayName.get(name);
    }

    public static Drug getByNameCaseInsensitive(String name) {
        return byLowerCaseName.get(name.toLowerCase());
    }
}