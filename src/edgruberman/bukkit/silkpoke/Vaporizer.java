package edgruberman.bukkit.silkpoke;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFadeEvent;

/** adds a visual and sound effect for any ice that is broken or melts in the nether */
class Vaporizer implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockBreak(final BlockBreakEvent event) {
        if (event.getBlock().getWorld().getEnvironment() != Environment.NETHER) return;

        if (event.getBlock().getTypeId() != Material.ICE.getId()) return;

        event.getBlock().getWorld().playEffect(event.getBlock().getLocation(), Effect.EXTINGUISH, 0);
        event.getBlock().getWorld().playEffect(event.getBlock().getLocation(), Effect.SMOKE, BlockFace.UP);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBlockFade(final BlockFadeEvent event) {
        if (event.getBlock().getWorld().getEnvironment() != Environment.NETHER) return;

        if (event.getBlock().getTypeId() != Material.ICE.getId()) return;

        event.getBlock().getWorld().playEffect(event.getBlock().getLocation(), Effect.EXTINGUISH, 0);
        event.getBlock().getWorld().playEffect(event.getBlock().getLocation(), Effect.SMOKE, BlockFace.UP);
    }

}
