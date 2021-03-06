package edgruberman.bukkit.silkpoke;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.Plugin;

import edgruberman.bukkit.silkpoke.commands.Reload;
import edgruberman.bukkit.silkpoke.messaging.Courier.ConfigurationCourier;
import edgruberman.bukkit.silkpoke.util.CustomPlugin;

public final class Main extends CustomPlugin implements Listener {

    public static ConfigurationCourier courier;

    private final List<Permission> permissions = new ArrayList<Permission>();

    @Override
    public void onLoad() { this.putConfigMinimum("2.3.0"); }

    @Override
    public void onEnable() {
        this.setPathSeparator('|').reloadConfig();
        Main.courier = ConfigurationCourier.Factory.create(this).build();

        this.loadPermissions(this.getConfig().getConfigurationSection("permissions"));

        if (this.getConfig().getBoolean("vaporizer"))
            Bukkit.getPluginManager().registerEvents(new Vaporizer(), this);

        Bukkit.getPluginManager().registerEvents(this, this);

        this.getCommand("silkpoke:reload").setExecutor(new Reload(this));
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll((Plugin) this);

        for (final Permission permission : this.permissions)
            Bukkit.getPluginManager().removePermission(permission);

        Main.courier = null;
    }

    private void loadPermissions(final ConfigurationSection permissions) {
        if (permissions == null) return;

        for (final String name : permissions.getKeys(false)) {
            final ConfigurationSection permission = permissions.getConfigurationSection(name);
            final String description = permission.getString("description");
            final PermissionDefault permDefault = PermissionDefault.getByName(permission.getString("default", Permission.DEFAULT_PERMISSION.name()));
            final Map<String, Boolean> children = new HashMap<String, Boolean>();
            for (final String child : permission.getConfigurationSection("children").getKeys(false))
                children.put(child, permission.getConfigurationSection("children").getBoolean(child));

            final Permission created = new Permission(name, description, permDefault, children);
            this.permissions.add(created);
            Bukkit.getPluginManager().addPermission(created);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(final BlockBreakEvent event) {
        if (!event.getPlayer().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;

        final String permission = "silkpoke.material." + event.getBlock().getType().name();
        if (!event.getPlayer().hasPermission(permission)) return;

        event.setCancelled(true);

        final ItemStack drop = new ItemStack(event.getBlock().getType(), 1);
        drop.setData(event.getBlock().getState().getData());
        event.getBlock().setType(Material.AIR);
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), drop);

        this.getLogger().log(Level.FINEST, "Dropped {0}:{1} for {2} using {3} with {4}"
                , new Object[] { drop.getType().name(), (int) drop.getDurability()
                , event.getPlayer().getName(), event.getPlayer().getItemInHand().getType().name()
                , Enchantment.SILK_TOUCH.getName() });
    }

}
