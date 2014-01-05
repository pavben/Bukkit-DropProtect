### DropProtect for Bukkit (1.7.2 and 1.7.4)

Don't want your players losing their entire inventory on death? **DropProtect protects** your **equipment** and **hotbar** slots, while allowing the inner inventory to drop as normal. As of version v0.2, you may configure it to reduce the number of protected hotbar slots or add additional protected inner inventory slots to suit your server.

Personally, I find the default of 9 to be the sweet spot between losing it all and having no penalty on death. Still penalize them for dying, but don't make them rage if they get a lag spike and fall into lava with their enchanted diamond set.

### Example (with the default setting of **9 protected slots**)

#### Inventory before and after death. The purple highlight (in this image only) indicates protected slots:
![Inventory before and after death](http://www.virtivia.com/minecraft/bukkitplugins/dropprotect/images/inventory.png)

#### And here's what remains at the crime scene for pick-up:
![Drops](http://www.virtivia.com/minecraft/bukkitplugins/dropprotect/images/drops.png)

### Simple Drop-in Installation

Place the **DropProtect.jar** file into your **/server/plugins** folder. Start the server. You're done!

To make sure it's installed, put some items in your equipment slots, some in the hotbar, and some in the inner inventory. Then type /kill or find a more creative way to die. Your equipment (in the 4 equipment slots) and hotbar items should stay on you, while the remaining items will be on the ground where you died.

### Permissions

None. DropProtect will be enabled for all players in Survival mode.

### Commands

#### **/dropprotect** will display which slots are protected. It looks like:
![/dropprotect command](http://www.virtivia.com/minecraft/bukkitplugins/dropprotect/images/dropprotect-command.png)

### Configuration

After running the server once with DropProtect installed, the config file **/server/plugins/DropProtect/config.yml** will be created.

You may change the **protected-slots** setting to your liking. There are some examples inside the file. You can run **/reload** or restart the server to apply the config change.
