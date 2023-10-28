package redvortex.lifesteal.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import redvortex.lifesteal.LifeSteal;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PlayerInteractionListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getItem() != null) {
            if (event.getItem().getType() == Material.COMMAND_BLOCK) {
                Player player = event.getPlayer();
                if ((player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() < 40) && (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 2 <= 40)) {
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 2);
                    event.getItem().setAmount(event.getItem().getAmount() - 1);
                    player.sendMessage(Component.text("Replenished 1 heart!").color(TextColor.color(0x00FF00)));
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                } else {
                    player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_HURT_DROWN, 1, 1);
                }
            } else if (event.getItem().getType() == Material.REPEATING_COMMAND_BLOCK) {
                Player player = event.getPlayer();
                Inventory menu = Bukkit.createInventory(player, 27, Component.text("Revive Beacon"));
                Object[] banlist = player.getServer().getBanList(BanList.Type.NAME).getBanEntries().toArray();
                ItemStack playerItem = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta meta = (SkullMeta) playerItem.getItemMeta();
                for (Object name : banlist) {
                    meta.setDisplayName(((BanEntry) name).getTarget());
                    meta.setOwningPlayer(Bukkit.getOfflinePlayer(((BanEntry) name).getTarget()));
                    meta.lore(List.of(Component.text("§r§7Click to revive this player.")));
                    playerItem.setItemMeta(meta);
                    menu.addItem(playerItem);
                }
                player.playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE, 3, 2);
                player.openInventory(menu);
            }
        }
    }
}
