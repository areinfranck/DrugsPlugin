package me.theminddroid.drugs;

import me.theminddroid.drugs.models.Drug;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class DrugTabCompleter implements TabCompleter
{
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args)
    {
        if (args.length != 1) return null;

        final ArrayList<String> result = new ArrayList<>();
        for (Drug value : Drug.values())
        {
            String arg = value.getDrugName();
            if (arg.toLowerCase().startsWith(args[0].toLowerCase())) result.add(arg);
        }

        return result;
    }
}