package me.theminddroid.drugs;

import me.theminddroid.drugs.models.Drug;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class AutoTab implements TabCompleter {

    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        final ArrayList<String> arguments = new ArrayList<>();
        arguments.add("Narcan");

        for (Drug value : Drug.values()) {
            arguments.add(value.getDrugName());
        }

        final ArrayList<String> result = new ArrayList<>();

        if (args.length == 1) {
            for (String arg : arguments) {
                if (arg.toLowerCase().startsWith(args[0].toLowerCase()))
                    result.add(arg);
            }
            return result;
        }
        return null;
    }
}