package edgruberman.bukkit.silkpoke;

import java.util.HashMap;
import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import edgruberman.bukkit.silkpoke.util.MaterialDataMatcher;

/** ensures dropped items are picked up with the same data they contain they were dropped as */
class DataPicker implements Listener {

    private final MaterialDataMatcher materials;

    DataPicker(final Plugin plugin, final List<String> materials) {
        this.materials = new MaterialDataMatcher();
        for (final String material : materials)
            try {
                this.materials.add(material);
            } catch (final Exception e) {
                plugin.getLogger().warning("Unable to identify material:data pair \"" + material + "\"; " + e);
            }
    }

    @EventHandler(ignoreCancelled = true)
    public void onPlayerPickupItem(final PlayerPickupItemEvent pickup) {
        final ItemStack item = pickup.getItem().getItemStack();
        if (!this.materials.contains(item.getTypeId(), item.getDurability())) return;

        pickup.setCancelled(true);
        final HashMap<Integer, ItemStack> remaining = pickup.getPlayer().getInventory().addItem(item);
        if (remaining.size() == 0) {
            pickup.getItem().remove();
            return;
        }

        // only possible to have one stack remaining since event is only fired per single stack picked up
        pickup.getItem().setItemStack(remaining.get(0));
        pickup.getItem().setPickupDelay(20);
    }

}
