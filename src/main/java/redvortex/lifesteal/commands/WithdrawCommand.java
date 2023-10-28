package redvortex.lifesteal.commands;

import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import redvortex.lifesteal.LifeSteal;

import java.util.HashMap;

public class WithdrawCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        
        if (sender instanceof Player) {
            Player player = (Player) sender;
            int amount = args.length > 0 ? Integer.parseInt(args[0])*2 : 2;
            ItemStack item = LifeSteal.HEART_ITEM;
            item.setAmount(amount/2);
            if (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= amount) {
                player.sendMessage("§cYou don't have enough hearts to withdraw.");
            } else {
                HashMap<Integer, ItemStack> hashmap =  player.getInventory().addItem(item);
                if (hashmap.isEmpty()) {
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() - amount);
                } else {
                    player.sendMessage("§cYou don't have enough space in your inventory to withdraw.");
                }
            }
            return true;
        } else {
            sender.sendMessage("§cThis command must be run by a player.");
        }
        
        return false;
    }
}
