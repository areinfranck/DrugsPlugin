package me.theminddroid.drugs.models;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public enum Drug {
    Cocaine(Material.SUGAR,
            "speed",
            Sound.ENTITY_PLAYER_BURP,
            new DrugEffect(PotionEffectType.SPEED, "gained a speed buff"),
            new DrugEffect(PotionEffectType.SLOW, "gained a speed reduction")),
    Heroin(Material.WHITE_DYE,
            "regeneration",
            Sound.ENTITY_PLAYER_BURP,
            new DrugEffect(PotionEffectType.REGENERATION, "gained a regeneration buff"),
            new DrugEffect(PotionEffectType.WEAKNESS, "have become weak")),
    Adderall(Material.GOLD_NUGGET,
            "haste",
            Sound.ENTITY_PLAYER_BURP,
            new DrugEffect(PotionEffectType.FAST_DIGGING, "gained a digging speed buff"),
            new DrugEffect(PotionEffectType.SLOW_DIGGING, "gained a digging speed reduction")),
    Steroids(Material.PRISMARINE_CRYSTALS,
            "strength",
            Sound.ENTITY_PLAYER_BURP,
            new DrugEffect(PotionEffectType.INCREASE_DAMAGE, "gained a strength buff"),
            new DrugEffect(PotionEffectType.WEAKNESS, "are weakened")),
    Hennessy(Material.HONEY_BOTTLE,
            "luck",
            Sound.ENTITY_GENERIC_DRINK,
            new DrugEffect(PotionEffectType.LUCK, "gained luck"),
            new DrugEffect(PotionEffectType.POISON, "are poisoned"));

    private static final Map<String, Drug> byDisplayName = new HashMap<>();
    private static final Map<String, Drug> byLowerCaseName = new HashMap<>();

    static {
        for (Drug value : values()) {
            byDisplayName.put(value.displayName, value);
            byLowerCaseName.put(value.name().toLowerCase(), value);
        }
    }

    private final Material material;
    private final String displayName;
    private final String effectName;
    private final Sound consumeSound;
    private final DrugEffect effect;
    private final DrugEffect withdrawalEffect;

    Drug(Material material, String effectName, Sound consumeSound, DrugEffect effect, DrugEffect withdrawalEffect) {
        this.effectName = effectName;
        this.consumeSound = consumeSound;
        this.effect = effect;
        this.withdrawalEffect = withdrawalEffect;
        this.displayName = ChatColor.RED + this.name();
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEffectName() {
        return effectName;
    }

    public Sound getConsumeSound() {
        return consumeSound;
    }

    public DrugEffect getEffect() {
        return effect;
    }

    public DrugEffect getWithdrawalEffect() {
        return withdrawalEffect;
    }

    public static Drug getByDisplayName(String name) {
        return byDisplayName.get(name);
    }

    public static Drug getByNameCaseInsensitive(String name) {
        return byLowerCaseName.get(name.toLowerCase());
    }
}