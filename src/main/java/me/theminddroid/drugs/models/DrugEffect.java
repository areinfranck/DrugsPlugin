package me.theminddroid.drugs.models;

import org.bukkit.potion.PotionEffectType;

public class DrugEffect {

    private final PotionEffectType effectType;
    private final String message;

    public DrugEffect(PotionEffectType effectType, String message) {

        this.effectType = effectType;
        this.message = message;
    }

    public PotionEffectType getEffectType() {
        return effectType;
    }

    public String getMessage() {
        return message;
    }
}