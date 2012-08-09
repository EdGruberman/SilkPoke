package edgruberman.bukkit.silkpoke;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        if (this.getConfig().getBoolean("vaporizer")) Bukkit.getPluginManager().registerEvents(new Vaporizer(), this);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(final BlockBreakEvent event) {
        if (!event.getPlayer().getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;

        if (!event.getPlayer().hasPermission("silkpoke.material." + event.getBlock().getType().name())) return;

        event.setCancelled(true);
        final int id = event.getBlock().getTypeId();
        final byte damage = event.getBlock().getState().getRawData();
        final ItemStack item = new ItemStack(id, 1, damage, damage);
        event.getBlock().setTypeIdAndData(Material.AIR.getId(), (byte) 0, true);
        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), item);
    }

}
