package me.theminddroid.drugs;

import me.theminddroid.drugs.models.Drug;

import java.util.function.Supplier;

public class DrugsService
{

    @SuppressWarnings("unused")
    public void registerDrug(Drug drug, Supplier<Boolean> isDrugEnabled)
    {
        DrugsPlugin.getInstance().registerDrugRecipe(drug);
        DrugManager.addDrug(drug, isDrugEnabled);
    }

    @SuppressWarnings("unused")
    public void unregisterDrug(Drug drug)
    {
        DrugsPlugin.getInstance().unregisterDrugRecipe(drug);
        DrugManager.removeDrug(drug);
    }
}