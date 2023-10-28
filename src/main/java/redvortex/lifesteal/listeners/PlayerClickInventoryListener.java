package redvortex.lifesteal.listeners;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PlayerClickInventoryListener implements Listener {
    @EventHandler
    public void onPlayerClickInventory(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (player.getOpenInventory().title().equals(Component.text("Revive Beacon"))) {
            if (event.getCurrentItem().getType() != Material.PLAYER_HEAD) return;
            event.setCancelled(true);

            String name = event.getCurrentItem().getItemMeta().getDisplayName();

            player.getOpenInventory().close();
            player.sendMessage(Component.text("§aRevived " + name + "!"));
            player.playSound(player.getLocation(), Sound.ITEM_TOTEM_USE, 1, 1);

            BanList banlist = player.getServer().getBanList(BanList.Type.NAME);
            banlist.pardon(name);

            if (player.getInventory().getItemInMainHand().getType() == Material.REPEATING_COMMAND_BLOCK) {
                player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
            } else if (player.getInventory().getItemInOffHand().getType() == Material.REPEATING_COMMAND_BLOCK) {
                player.getInventory().getItemInOffHand().setAmount(player.getInventory().getItemInOffHand().getAmount() - 1);
            }
        }
    }
}