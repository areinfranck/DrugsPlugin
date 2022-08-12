package me.theminddroid.drugs;

import me.theminddroid.drugs.models.Drug;
import me.theminddroid.drugs.models.DrugEffect;
import me.theminddroid.drugs.models.DrugRecipe;
import me.theminddroid.drugs.models.DrugType;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class DrugManager {
    private static final Map<String, Drug> byDisplayName = new HashMap<>();
    private static final Map<String, Drug> byLowerCaseName = new HashMap<>();
    private static final Map<Drug, Supplier<Boolean>> checkIfEnabled = new HashMap<>();

    static
    {
        // Place drugs into it
        var drugs = Arrays.asList(
                new Drug(
                    "Narcan",
                    Material.MILK_BUCKET,
                    "narcan",
                    new DrugType.Narcan(),
                    new DrugRecipe.VerticalShaped(Material.ENDER_EYE, Material.DIAMOND, Material.BUCKET)),
                new Drug(
                        "Cocaine",
                        Material.SUGAR,
                        "speed",
                        new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                                new DrugEffect(PotionEffectType.SPEED, "gained a speed buff"),
                                new DrugEffect(PotionEffectType.SLOW, "gained a speed reduction")),
                        new DrugRecipe.VerticalShaped(Material.SUGAR, Material.COCOA_BEANS, Material.PAPER)),
                new Drug(
                        "Heroin",
                        Material.WHITE_DYE,
                        "regeneration",
                        new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                                new DrugEffect(PotionEffectType.REGENERATION, "gained a regeneration buff"),
                                new DrugEffect(PotionEffectType.WEAKNESS, "have become weak")),
                        new DrugRecipe.VerticalShaped(Material.SUGAR, Material.WHEAT_SEEDS, Material.PAPER)),
                new Drug(
                        "Adderall",
                        Material.GOLD_NUGGET,
                        "haste",
                        new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                                new DrugEffect(PotionEffectType.FAST_DIGGING, "gained a digging speed buff"),
                                new DrugEffect(PotionEffectType.SLOW_DIGGING, "gained a digging speed reduction")),
                        new DrugRecipe.VerticalShaped(Material.SUGAR, Material.HONEYCOMB, Material.PAPER)),
                new Drug(
                        "Steroids",
                        Material.PRISMARINE_CRYSTALS,
                        "strength",
                        new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                                new DrugEffect(PotionEffectType.INCREASE_DAMAGE, "gained a strength buff"),
                                new DrugEffect(PotionEffectType.WEAKNESS, "are weakened")),
                        new DrugRecipe.VerticalShaped(Material.SUGAR, Material.BONE_MEAL, Material.PAPER)),
                new Drug(
                        "Hennessy",
                        Material.HONEY_BOTTLE,
                        "luck",
                        new DrugType.PsychoActive(Sound.ENTITY_GENERIC_DRINK,
                                new DrugEffect(PotionEffectType.LUCK, "gained luck"),
                                new DrugEffect(PotionEffectType.UNLUCK, "are unlucky")),
                        new DrugRecipe.VerticalShaped(Material.WHEAT, Material.SWEET_BERRIES, Material.GLASS_BOTTLE)),
                new Drug(
                        "Ketamine",
                        Material.LIGHT_GRAY_DYE,
                        "jump boost",
                        new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                                new DrugEffect(PotionEffectType.JUMP, "gained a jump boost"),
                                new DrugEffect(PotionEffectType.SLOW, "gained a speed reduction")),
                        new DrugRecipe.VerticalShaped(Material.QUARTZ, Material.GUNPOWDER, Material.PAPER)),
                new Drug(
                        "Marijuana",
                        Material.GREEN_DYE,
                        "resistance",
                        new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                                new DrugEffect(PotionEffectType.DAMAGE_RESISTANCE, "gained damage resistance"),
                                new DrugEffect(PotionEffectType.WEAKNESS, "have become weak")),
                        new DrugRecipe.VerticalShaped(Material.GREEN_DYE, Material.WHEAT, Material.PAPER)),
                new Drug(
                        "LSD",
                        Material.PAPER,
                        "night vision",
                        new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                                new DrugEffect(PotionEffectType.NIGHT_VISION, "gained night vision"),
                                new DrugEffect(PotionEffectType.BLINDNESS, "have become blind")),
                        new DrugRecipe.VerticalShaped(Material.SPIDER_EYE, Material.BLACK_DYE, Material.PAPER)),
                new Drug(
                        "Ecstasy",
                        Material.PHANTOM_MEMBRANE,
                        "levitation",
                        new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                                new DrugEffect(PotionEffectType.LEVITATION, "gained levitation"),
                                new DrugEffect(PotionEffectType.SLOW_FALLING, "gained slow falling")),
                        new DrugRecipe.VerticalShaped(Material.CHORUS_FRUIT, Material.PRISMARINE_CRYSTALS, Material.PAPER)),
                new Drug(
                        "Ayahuasca",
                        Material.BEETROOT_SOUP,
                        "invisibility",
                        new DrugType.PsychoActive(Sound.ENTITY_GENERIC_DRINK,
                                new DrugEffect(PotionEffectType.INVISIBILITY, "gained invisibility"),
                                new DrugEffect(PotionEffectType.GLOWING, "gained glowing")),
                        new DrugRecipe.VerticalShaped(Material.BEETROOT, Material.VINE, Material.BOWL)),
                new Drug(
                        "Shrooms",
                        Material.RED_MUSHROOM,
                        "health boost",
                        new DrugType.PsychoActive(Sound.ENTITY_PLAYER_BURP,
                                new DrugEffect(PotionEffectType.HEALTH_BOOST, "gained health boost"),
                                new DrugEffect(PotionEffectType.POISON, "gained poison")),
                        new DrugRecipe.VerticalShaped(Material.RED_MUSHROOM, Material.BLAZE_POWDER, Material.BOWL))
        );

        drugs.forEach(drug -> {
            byDisplayName.put(drug.getDisplayName(), drug);
            byLowerCaseName.put(drug.name().toLowerCase(), drug);
            checkIfEnabled.put(drug, () -> DrugsPlugin.getInstance().getConfig().getBoolean(drug.getDrugName() + "Use.enabled"));
        });
    }

    public static Drug getByDisplayName(String name)
    {
        return byDisplayName.get(name);
    }

    public static Drug getByNameCaseInsensitive(String name)
    {
        return byLowerCaseName.get(name.toLowerCase());
    }

    public static Collection<Drug> getActiveDrugs()
    {
        return byDisplayName.values();
    }

    static void addDrug(Drug drug, Supplier<Boolean> isDrugEnabled)
    {
        byDisplayName.putIfAbsent(drug.getDisplayName(), drug);
        byLowerCaseName.putIfAbsent(drug.name().toLowerCase(), drug);
        checkIfEnabled.putIfAbsent(drug, isDrugEnabled);
    }

    static void removeDrug(Drug drug)
    {
        byDisplayName.remove(drug.getDisplayName());
        byLowerCaseName.remove(drug.name().toLowerCase());
        checkIfEnabled.remove(drug);
    }

    public static boolean isDrugEnabled(Drug drug)
    {
        return checkIfEnabled.getOrDefault(drug, () -> false).get();
    }
}