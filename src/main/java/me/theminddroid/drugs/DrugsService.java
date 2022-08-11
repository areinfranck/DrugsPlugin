package me.theminddroid.drugs;

import me.theminddroid.drugs.models.Drug;

public class DrugsService
{

    @SuppressWarnings("unused")
    public void registerDrug(Drug drug)
    {
        DrugsPlugin.getInstance().registerDrugRecipe(drug);
        DrugManager.addDrug(drug);
    }

    @SuppressWarnings("unused")
    public void unregisterDrug(Drug drug)
    {
        DrugsPlugin.getInstance().unregisterDrugRecipe(drug);
        DrugManager.removeDrug(drug);
    }
}