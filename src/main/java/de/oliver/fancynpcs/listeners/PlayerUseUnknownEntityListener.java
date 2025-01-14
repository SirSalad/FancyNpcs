package de.oliver.fancynpcs.listeners;

import com.destroystokyo.paper.event.player.PlayerUseUnknownEntityEvent;
import de.oliver.fancynpcs.FancyNpcs;
import de.oliver.fancynpcs.api.Npc;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerUseUnknownEntityListener implements Listener {

    @EventHandler
    public void onPlayerUseUnknownEntity(PlayerUseUnknownEntityEvent event) {
        Npc npc = FancyNpcs.getInstance().getNpcManagerImpl().getNpc(event.getEntityId());
        if (npc == null) {
            return;
        }

        npc.interact(event.getPlayer(), event.isAttack(), event.getHand(), event.getClickedRelativePosition());
    }

}
