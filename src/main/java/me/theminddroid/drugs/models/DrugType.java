package me.theminddroid.drugs.models;

import org.bukkit.Sound;

public abstract class DrugType
{
    public static class PsychoActive extends DrugType
    {
        private final Sound consumeSound;
        private final DrugEffect effect;
        private final DrugEffect withdrawalEffect;

        public PsychoActive(Sound consumeSound, DrugEffect effect, DrugEffect withdrawalEffect)
        {
            this.consumeSound = consumeSound;
            this.effect = effect;
            this.withdrawalEffect = withdrawalEffect;
        }

        public Sound getConsumeSound()
        {
            return consumeSound;
        }

        public DrugEffect getEffect()
        {
            return effect;
        }

        public DrugEffect getWithdrawalEffect()
        {
            return withdrawalEffect;
        }
    }

    public static class Narcan extends DrugType
    {

    }
}
