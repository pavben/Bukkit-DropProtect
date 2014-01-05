package com.virtivia.minecraft.bukkitplugins.dropprotect;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class DropProtectCommandExecutor implements CommandExecutor {
	private JavaPlugin plugin;
	private int numProtectedSlots;
	
	public DropProtectCommandExecutor(JavaPlugin plugin, int numProtectedSlots) {
		this.plugin = plugin;
		this.numProtectedSlots = numProtectedSlots;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		sender.sendMessage("DropProtect v" + plugin.getDescription().getVersion() + " is " + ChatColor.GREEN + "enabled" + ChatColor.RESET + ".");
		sender.sendMessage("--------------------------------");
		sender.sendMessage("On death, the following item slots are protected and will not drop:");
		sender.sendMessage("- Armor: Helmet, Chestplate, Leggings, Boots");
		
		int numProtectedHotbarSlots = Math.min(this.numProtectedSlots, DropProtect.NUM_HOTBAR_SLOTS);
		
		if (numProtectedHotbarSlots > 0) {
			sender.sendMessage("- " + (this.numProtectedSlots >= DropProtect.NUM_HOTBAR_SLOTS ? "All " : "") + ChatColor.GREEN + numProtectedHotbarSlots + ChatColor.RESET + " hotbar slot" + (numProtectedHotbarSlots >= 2 ? "s" : ""));
		}
		
		int numProtectedInventorySlots = Math.max(this.numProtectedSlots - DropProtect.NUM_HOTBAR_SLOTS, 0);
		
		if (numProtectedInventorySlots > 0) {
			String prefix = "", suffix = "";
			
			if (numProtectedInventorySlots == DropProtect.NUM_INVENTORY_SLOTS - DropProtect.NUM_HOTBAR_SLOTS) {
				prefix = "All ";
				suffix = "slots";
			} else if (numProtectedInventorySlots >= 2) {
				suffix = "slots, starting at the top left and going right.";
			} else if (numProtectedInventorySlots == 1) {
				suffix = "slot at the top left";
			}
			
			sender.sendMessage("- " + prefix + ChatColor.GREEN + numProtectedInventorySlots + ChatColor.RESET + " inner inventory " + suffix);
		}
		
		return true;
	}
}
