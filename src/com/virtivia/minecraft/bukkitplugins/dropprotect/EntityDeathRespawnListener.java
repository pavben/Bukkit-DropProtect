package com.virtivia.minecraft.bukkitplugins.dropprotect;

import java.util.HashMap;

import org.bukkit.ChatColor;
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
	private int numProtectedSlots;
	private HashMap<String, ProtectedItemsSnapshot> playerItemSnapshots;
	
	public EntityDeathRespawnListener(JavaPlugin plugin, int numProtectedSlots) {
		this.plugin = plugin;
		this.numProtectedSlots = numProtectedSlots;
		this.playerItemSnapshots = new HashMap<String, ProtectedItemsSnapshot>();
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onEntityDeath(EntityDeathEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			
			// only active in survival mode
			if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
				return;
			}
			
			ProtectedItemsSnapshot protectedItemsSnapshot = new ProtectedItemsSnapshot(player, event.getDrops(), numProtectedSlots);
			
			// add the snapshot to load after the player respawns
			playerItemSnapshots.put(player.getName(), protectedItemsSnapshot);
		}
	}
    
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();
		String playerName = player.getName();
		
		ProtectedItemsSnapshot protectedItemsSnapshot = playerItemSnapshots.get(playerName);
		
		// if we have an inventory snapshot for this player
		if (protectedItemsSnapshot != null) {
			// merge the snapshot into the player's inventory, dropping overflow items at the respawn location
			protectedItemsSnapshot.mergeIntoPlayerInventory(player, event.getRespawnLocation());
			
			if (protectedItemsSnapshot.hasNonEmptyItems()) {
				player.sendMessage("[" + ChatColor.GREEN + "DropProtect" + ChatColor.RESET + "] Inventory restored. Type /dropprotect for details.");
			}
			
			// remove the snapshot since it has now been applied
			playerItemSnapshots.remove(playerName);
		}
	}
}
