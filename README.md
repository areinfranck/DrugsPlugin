# Drugs Plugin

Source code for the [Drugs plugin][Spigot]. 

[Spigot]: https://www.spigotmc.org/resources/drugs.90645/

---
# Branch Information
This branch allows users to create their own drugs by creating a plugin.

Initial Setup:
1. Create plugin with Drugs as listed dependency
2. Use Maven/Gradle to import Drugs.jar as a dependency

For end-users to extend functionality:
```java
public final class MyPlugin extends JavaPlugin {
    private Optional<DrugsService> drugsService;
    private Supplier<Boolean> isDrugEnabled;
    private Drug drug;

    @Override
    public void onEnable() {
        drugsService = Optional.ofNullable(Bukkit.getServicesManager().getRegistration(DrugsService.class))
                .map(RegisteredServiceProvider::getProvider);

        drug = new Drug(/*TODO*/);
        isDrugEnabled = () -> { /*TODO*/ };

        drugsService.ifPresent(service -> service.registerDrug(drug, isDrugEnabled));
    }

    @Override
    public void onDisable() {
        drugsService.ifPresent(service -> service.unregisterDrug(drug));
    }
}
```
