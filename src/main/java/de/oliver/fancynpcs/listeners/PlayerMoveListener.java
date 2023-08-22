package de.oliver.fancynpcs.listeners;

import de.oliver.fancynpcs.FancyNpcs;
import de.oliver.fancynpcs.api.Npc;
import de.oliver.fancynpcs.api.NpcData;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Collection;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Collection<Npc> npcs = FancyNpcs.getInstance().getNpcManagerImpl().getAllNpcs();
        int visibilityDistance = FancyNpcs.getInstance().getFancyNpcConfig().getVisibilityDistance();
        int turnToPlayerDistance = FancyNpcs.getInstance().getFancyNpcConfig().getTurnToPlayerDistance();

        Player player = event.getPlayer();
        Location playerLocation = player.getLocation();

        for (Npc npc : npcs) {
            NpcData npcData = npc.getData();
            Location npcLocation = npcData.getLocation();

            if (!npcData.isSpawnEntity() || playerLocation.getWorld() != npcLocation.getWorld()) {
                continue;
            }

            double distance = playerLocation.distance(npcLocation);
            if (Double.isNaN(distance)) {
                continue;
            }

            boolean isCurrentlyVisible = npc.getIsVisibleForPlayer().getOrDefault(player.getUniqueId(), false);
            if (distance > visibilityDistance && isCurrentlyVisible) {
                npc.remove(player);
            } else if (distance < visibilityDistance && !isCurrentlyVisible) {
                npc.spawn(player);
            }

            if (npcData.isTurnToPlayer() && distance < turnToPlayerDistance) {
                Location newLoc = playerLocation.clone();
                newLoc.setDirection(newLoc.subtract(npcLocation).toVector());
                npc.lookAt(player, newLoc);
            }
        }
    }
}