package me.theminddroid.drugs.models;

import org.bukkit.potion.PotionEffectType;

public record DrugEffect(PotionEffectType effectType, String message) {}