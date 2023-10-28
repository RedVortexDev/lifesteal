package redvortex.lifesteal;

import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import redvortex.lifesteal.commands.KmsCommand;
import redvortex.lifesteal.commands.WithdrawCommand;
import redvortex.lifesteal.listeners.PlayerClickInventoryListener;
import redvortex.lifesteal.listeners.PlayerDeathListener;
import redvortex.lifesteal.listeners.PlayerInteractionListener;
import redvortex.lifesteal.listeners.PlayerJoinListener;

import java.util.List;

public final class LifeSteal extends JavaPlugin {
    public static JavaPlugin plugin;
    public static ItemStack HEART_ITEM = new ItemStack(Material.COMMAND_BLOCK);
    public static ItemStack REVIVE_BEACON = new ItemStack(Material.REPEATING_COMMAND_BLOCK);

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        // HEART ITEM
        ItemMeta meta = HEART_ITEM.getItemMeta();
        meta.lore(List.of(
                Component.text("§r§7Right-click to obtain."),
                Component.text("§r§7Used to craft a Revive Beacon.")
        ));
        meta.displayName(Component.text("§r§cHeart"));
        HEART_ITEM.setItemMeta(meta);

        // HEART RECIPE
        NamespacedKey key = new NamespacedKey(this, "heart");
        ShapedRecipe recipe = new ShapedRecipe(key, HEART_ITEM);
        recipe.shape("RDR",
                     "DTD",
                     "RDR");

        recipe.setIngredient('R', Material.REDSTONE_BLOCK);
        recipe.setIngredient('D', Material.DIAMOND_BLOCK);
        recipe.setIngredient('T', Material.TOTEM_OF_UNDYING);

        Bukkit.addRecipe(recipe);

        // REVIVE BEACON ITEM
        meta = REVIVE_BEACON.getItemMeta();
        meta.lore(List.of(
                Component.text("§r§7Right-click to revive a player."))
        );
        meta.displayName(Component.text("§r§cRevive Beacon"));
        REVIVE_BEACON.setItemMeta(meta);

        // REVIVE BEACON RECIPE
        key = new NamespacedKey(this, "revive_beacon");
        recipe = new ShapedRecipe(key, REVIVE_BEACON);
        recipe.shape("HHH",
                     "HEH",
                     "OOO" );

        recipe.setIngredient('H', Material.COMMAND_BLOCK);
        recipe.setIngredient('E', Material.ELYTRA);
        recipe.setIngredient('O', Material.OBSIDIAN);

        Bukkit.addRecipe(recipe);

        // REGISTER LISTENERS AND COMMANDS
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractionListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerClickInventoryListener(), this);
        getCommand("withdraw").setExecutor(new WithdrawCommand());
        getCommand("kms").setExecutor(new KmsCommand());
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
