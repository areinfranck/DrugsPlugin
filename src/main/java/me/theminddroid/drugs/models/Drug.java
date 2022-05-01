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
            new DrugRecipe.VerticalShaped(Material.ENDER_EYE, Material.DIAMOND, Material.BUCKET)),
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
                    new DrugEffect(PotionEffectType.UNLUCK, "are unlucky")),
            new DrugRecipe.VerticalShaped(Material.WHEAT, Material.SWEET_BERRIES, Material.GLASS_BOTTLE)),
    Ketamine(
            Material.LIGHT_GRAY_DYE,
            "jump boost",
            new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                    new DrugEffect(PotionEffectType.JUMP, "gained a jump boost"),
                    new DrugEffect(PotionEffectType.SLOW, "gained a speed reduction")),
            new DrugRecipe.VerticalShaped(Material.QUARTZ, Material.GUNPOWDER, Material.PAPER)),
    Marijuana(
            Material.GREEN_DYE,
            "resistance",
            new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                    new DrugEffect(PotionEffectType.DAMAGE_RESISTANCE, "gained damage resistance"),
                    new DrugEffect(PotionEffectType.WEAKNESS, "have become weak")),
            new DrugRecipe.VerticalShaped(Material.GREEN_DYE, Material.WHEAT, Material.PAPER)),
    LSD(
            Material.PAPER,
            "night vision",
            new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                    new DrugEffect(PotionEffectType.NIGHT_VISION, "gained night vision"),
                    new DrugEffect(PotionEffectType.BLINDNESS, "have become blind")),
            new DrugRecipe.VerticalShaped(Material.SPIDER_EYE, Material.BLACK_DYE, Material.PAPER)),
    Ecstasy(
            Material.PHANTOM_MEMBRANE,
            "levitation",
            new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                    new DrugEffect(PotionEffectType.LEVITATION, "gained levitation"),
                    new DrugEffect(PotionEffectType.SLOW_FALLING, "gained slow falling")),
            new DrugRecipe.VerticalShaped(Material.CHORUS_FRUIT, Material.PRISMARINE_CRYSTALS, Material.PAPER)),
    Ayahuasca(
            Material.BEETROOT_SOUP,
            "invisibility",
            new DrugType.PsychoActive(Sound.ENTITY_GENERIC_DRINK,
                    new DrugEffect(PotionEffectType.INVISIBILITY, "gained invisibility"),
                    new DrugEffect(PotionEffectType.GLOWING, "gained glowing")),
            new DrugRecipe.VerticalShaped(Material.BEETROOT, Material.VINE, Material.BOWL)),
    Shrooms(
            Material.RED_MUSHROOM,
            "health boost",
                    new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                    new DrugEffect(PotionEffectType.HEALTH_BOOST, "gained health boost"),
                    new DrugEffect(PotionEffectType.POISON, "gained poison")),
            new DrugRecipe.VerticalShaped(Material.RED_MUSHROOM, Material.BLAZE_POWDER, Material.PAPER)
            );

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