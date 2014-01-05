package com.virtivia.minecraft.bukkitplugins.dropprotect;

import org.bukkit.plugin.java.JavaPlugin;

public class DropProtect extends JavaPlugin {
	public static final int NUM_INVENTORY_SLOTS = 36;
	public static final int NUM_HOTBAR_SLOTS = 9;
	private static final String NUM_PROTECTED_SLOTS_KEY = "protected-slots";
	
	@Override
	public void onEnable() {
		int numProtectedSlots = this.getConfig().getInt(NUM_PROTECTED_SLOTS_KEY);
		
		if (numProtectedSlots < 0) {
			numProtectedSlots = 0;
		} else if (numProtectedSlots > NUM_INVENTORY_SLOTS) {
			numProtectedSlots = NUM_INVENTORY_SLOTS;
		}
		
		this.getLogger().info("DropProtect enabled with " + numProtectedSlots + " protected slots.");
		
		// Write the config to disk
		this.getConfig().options().copyDefaults(true);
		this.getConfig().options().copyHeader(true);
		
		this.saveConfig();
		
		// Add the /dropprotect command handler
		this.getCommand("dropprotect").setExecutor(new DropProtectCommandExecutor(this, numProtectedSlots));
		
		// Register the death and respawn event listener
		this.getServer().getPluginManager().registerEvents(new EntityDeathRespawnListener(this, numProtectedSlots) , this);
	}
	
	@Override
	public void onDisable() {
		this.getLogger().info("DropProtect disabled");
	}
}
