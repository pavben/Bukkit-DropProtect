package com.virtivia.minecraft.bukkitplugins.dropprotect;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DropProtectCommandExecutor implements CommandExecutor {
	private DropProtect dropProtect;
	
	public DropProtectCommandExecutor(DropProtect dropProtect) {
		this.dropProtect = dropProtect;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player)sender;
			
			int numProtectedSlots = this.dropProtect.getNumProtectedSlotsForPlayer(player);
			
			sender.sendMessage("DropProtect v" + dropProtect.getDescription().getVersion() + " is " + ChatColor.GREEN + "enabled" + ChatColor.RESET + ".");
			sender.sendMessage("--------------------------------");
			sender.sendMessage("On death, the following item slots are protected and will not drop:");
			sender.sendMessage("- Armor: Helmet, Chestplate, Leggings, Boots");
			
			int numProtectedHotbarSlots = Math.min(numProtectedSlots, DropProtect.NUM_HOTBAR_SLOTS);
			
			if (numProtectedHotbarSlots > 0) {
				sender.sendMessage("- " + (numProtectedSlots >= DropProtect.NUM_HOTBAR_SLOTS ? "All " : "") + ChatColor.GREEN + numProtectedHotbarSlots + ChatColor.RESET + " hotbar slot" + (numProtectedHotbarSlots >= 2 ? "s" : ""));
			}
			
			int numProtectedInventorySlots = Math.max(numProtectedSlots - DropProtect.NUM_HOTBAR_SLOTS, 0);
			
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
		} else {
			sender.sendMessage("This DropProtect command is player-specific. Run it from a player.");
		}
		
		return true;
	}
}
