package com.virtivia.minecraft.bukkitplugins.dropprotect;

import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class DropProtect extends JavaPlugin {
	public static final int NUM_INVENTORY_SLOTS = 36;
	public static final int NUM_HOTBAR_SLOTS = 9;
	private static final String PERMISSIONS_KEY = "permissions";
	private static final String NUM_PROTECTED_SLOTS_KEY = "protected-slots";
	
	@Override
	public void onEnable() {				
		// Write the config to disk
		//this.getConfig().options().copyDefaults(true);
		this.getConfig().options().copyHeader(true);
		
		this.saveConfig();
		
		// Add the /dropprotect command handler
		this.getCommand("dropprotect").setExecutor(new DropProtectCommandExecutor(this));
		
		// Register the death and respawn event listener
		this.getServer().getPluginManager().registerEvents(new EntityDeathRespawnListener(this) , this);

		this.getLogger().info("DropProtect enabled.");
	}
	
	@Override
	public void onDisable() {
		this.getLogger().info("DropProtect disabled");
	}
	
	// TODO: BAD CODE -- Abstract the config into a neatly-typed object
	public int getNumProtectedSlotsForPlayer(Player player) {
		FileConfiguration config = this.getConfig();
		
		// Get the default
		int numProtectedSlots = config.getInt(NUM_PROTECTED_SLOTS_KEY);
		
		if (numProtectedSlots < 0) {
			numProtectedSlots = 0;
		} else if (numProtectedSlots > NUM_INVENTORY_SLOTS) {
			numProtectedSlots = NUM_INVENTORY_SLOTS;
		}
		
		// Go through permission-specific settings
		try {
			@SuppressWarnings("unchecked")
			List<Map<String, Integer>> permissionsToNumSlots = (List<Map<String, Integer>>)config.getList(PERMISSIONS_KEY);
			
			if (permissionsToNumSlots != null) {
				for (Map<String, Integer> permissionToNumSlots : permissionsToNumSlots) {
					if (permissionToNumSlots.size() == 1) {						
						Map.Entry<String, Integer> entry = permissionToNumSlots.entrySet().iterator().next();
						
						String perm = entry.getKey();
						Integer slots = entry.getValue();
						
						// If the player matches this permission, the slots value applies to them
						if (player.hasPermission(perm)) {
							numProtectedSlots = slots;
							break;
						}
					} else {
						System.err.println("Your DropProtect permissions config is incorrect. Look at the plugin's readme for an example.");
						break;
					}
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error determining the number of protected slots for a player. This likely means your DropProtect permissions config is incorrect.");
		}
		
		return numProtectedSlots;
	}
}
