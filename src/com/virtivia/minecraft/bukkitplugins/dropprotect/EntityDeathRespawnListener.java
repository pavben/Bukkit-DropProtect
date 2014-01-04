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
	private HashMap<String, EquippedItemsSnapshot> playerItemSnapshots;
	
	public EntityDeathRespawnListener(JavaPlugin plugin) {
		this.plugin = plugin;
		this.playerItemSnapshots = new HashMap<String, EquippedItemsSnapshot>();
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			// only active in survival mode
			if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
				return;
			}
						
			EquippedItemsSnapshot equippedItemsSnapshot = new EquippedItemsSnapshot(player);
			
			// remove items in equippedItemsSnapshot from the drops
			equippedItemsSnapshot.filterItemStackList(event.getDrops());
			
			// add the snapshot to load after the player respawns
			playerItemSnapshots.put(player.getName(), equippedItemsSnapshot);
		}
	}
	
	@EventHandler(priority=EventPriority.NORMAL)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();

		EquippedItemsSnapshot equippedItemsSnapshot = playerItemSnapshots.get(playerName);
		
		// if we have an inventory snapshot for this player
		if (equippedItemsSnapshot != null) {
			// merge the snapshot into the player's inventory
			equippedItemsSnapshot.mergeToPlayerInventory(player);
			
			// remove the snapshot since it has now been applied
			playerItemSnapshots.remove(playerName);
		}
	}
}
