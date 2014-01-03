package com.virtivia.minecraft.bukkitplugins.dropprotect;

import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EntityDeathRespawnListener implements Listener {
	@SuppressWarnings("unused")
	private JavaPlugin plugin;
	private HashMap<Player, EquippedItemsSnapshot> playerItemSnapshots;
	
	public EntityDeathRespawnListener(JavaPlugin plugin) {
		this.plugin = plugin;
		this.playerItemSnapshots = new HashMap<Player, EquippedItemsSnapshot>();
	}
	
	// TODO: Do we need synchronized?
	@EventHandler(priority=EventPriority.HIGHEST)
	public synchronized void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
				return;
			}
						
			EquippedItemsSnapshot equippedItemsSnapshot = new EquippedItemsSnapshot(player);
			
			// remove items in equippedItemsSnapshot from the drops
			equippedItemsSnapshot.filterItemStackList(event.getDrops());
			
			// add the snapshot to load after the player respawns
			playerItemSnapshots.put(player, equippedItemsSnapshot);
		}
	}
	
	@EventHandler(priority=EventPriority.NORMAL)
	public synchronized void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();

		EquippedItemsSnapshot equippedItemsSnapshot = playerItemSnapshots.get(player);
		
		// if we have an inventory snapshot for this player
		if (equippedItemsSnapshot != null) {
			// merge the snapshot into the player's inventory
			equippedItemsSnapshot.mergeToPlayerInventory(player);
			
			// remove the snapshot since it has now been applied
			playerItemSnapshots.remove(equippedItemsSnapshot);
		}
	}
}
