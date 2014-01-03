package com.virtivia.minecraft.bukkitplugins.dropprotect;

import org.bukkit.plugin.java.JavaPlugin;

public class DropProtect extends JavaPlugin {
	@Override
	public void onEnable() {
		getLogger().info("DropProtect enabled");
		
		getServer().getPluginManager().registerEvents(new EntityDeathRespawnListener(this) , this);
	}
	
	@Override
	public void onDisable() {
		getLogger().info("DropProtect disabled");
	}
}
