package edgruberman.bukkit.silkpoke;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.block.BlockBreakEvent;

/** block about to be dropped by a player using an item with a Silk Touch enchantment */
public class SilkTouchBlockBreak extends BlockBreakEvent implements Cancellable {

    public SilkTouchBlockBreak(final Block block, final Player player) {
        super(block, player);
    }

}
