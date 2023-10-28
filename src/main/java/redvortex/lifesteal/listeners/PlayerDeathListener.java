package redvortex.lifesteal.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import redvortex.lifesteal.LifeSteal;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = event.getEntity().getKiller();
        int hearts = (int) player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
        int newHearts = hearts - 2;

        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(newHearts);
        if (player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 0) {
            Bukkit.getServer().getScheduler().runTaskLater(LifeSteal.plugin, () -> {
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(6);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + player.getName() + " You were killed by " + killer.getName() + ".");
            }, 5);
        }

        if (killer != null) {
            if ((killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() < 40) && (killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 2 <= 40)) {
                killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(killer.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() + 2);
            } else {
                ItemStack item = LifeSteal.HEART_ITEM;
                item.setAmount(1);
                Item itemEntity = Bukkit.getWorld(player.getWorld().getUID()).dropItemNaturally(player.getLocation(), item);
                itemEntity.customName(Component.text("Heart").color(TextColor.color(0xFF0000)));
                itemEntity.setCustomNameVisible(true);
            }
        }
    }
}
