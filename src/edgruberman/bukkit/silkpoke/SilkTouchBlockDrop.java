package edgruberman.bukkit.silkpoke;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;

/**
 * Block about to be dropped by a player using an item with a Silk Touch enchantment.
 */
public class SilkTouchBlockDrop extends BlockEvent  implements Cancellable {

    protected final Player player;

    public SilkTouchBlockDrop(final Block block, final Player player) {
        super(block);
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    // ---- Cancellable Event ----

    protected boolean cancelled = false;

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }

    // ---- Custom Event Handlers ----

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return SilkTouchBlockDrop.handlers;
    }

    public static HandlerList getHandlerList() {
        return SilkTouchBlockDrop.handlers;
    }

}
