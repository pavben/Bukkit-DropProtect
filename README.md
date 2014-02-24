### DropProtect

Don't want your players losing their entire inventory on death? **DropProtect protects** your **equipment** and **hotbar** slots, while allowing the inner inventory to drop as normal. You may configure it to reduce the number of protected hotbar slots or add additional protected inner inventory slots to suit your server.

Personally, I find the default of 9 (all armor + 9 hotbar slots) to be the sweet spot between losing it all and having no penalty on death. Still penalize them for dying, but don't make them rage if they get a lag spike and fall into lava with their enchanted diamond set.

### Download

- [DropProtect v0.4 (JAR file)](http://www.virtivia.com/minecraft/bukkitplugins/dropprotect/v0.4/DropProtect.jar) for Bukkit 1.7.2+

### Example (with the default setting of **9 protected slots**)

#### Inventory before and after death. The purple highlight (in this image only) indicates protected slots:
![Inventory before and after death](http://www.virtivia.com/minecraft/bukkitplugins/dropprotect/images/inventory.png)

#### And here's what we've left behind:
![Drops](http://www.virtivia.com/minecraft/bukkitplugins/dropprotect/images/drops.png)

### Simple Drop-in Installation

Place the **DropProtect.jar** file into your **/server/plugins** folder. Start the server.

Run **/dropprotect** to make sure it's installed correctly.

### Commands

#### **/dropprotect** will display which slots are protected. It looks like:
![/dropprotect command](http://www.virtivia.com/minecraft/bukkitplugins/dropprotect/images/dropprotect-command.png)

### Configuration

After running the server once with DropProtect installed, the config file **/server/plugins/DropProtect/config.yml** will be created.

You may change the **protected-slots** setting to your liking. There are some examples inside the file. You can run **/reload** or restart the server to apply the config change.

### Permissions

As of v0.4, you can assign a different number of protected slots based on permissions that a player has.

This is what your **/server/plugins/DropProtect/config.yml** file can look like:

    protected-slots: 3 # default if no permissions match
    permissions:
    - dropprotect.diamond: 8
    - dropprotect.platinum: 7
    - dropprotect.gold: 6
    - dropprotect.silver: 5
    - dropprotect.bronze: 4

Put them in the highest-first order since it matches from the top. Once it finds a match, that's the value it uses.

Also, those dropprotect.xxx names are just an example. You can call it whatever you like, and the dropprotect prefix is optional. You will need a system in place to assign these permissions to your players, but this is outside the scope of the plugin.
### Fan Videos

- German: [DropProtect Installation and Example (YouTube)](http://www.youtube.com/watch?v=CGN1wTuAz0Q)

### Compatibility with other plugins

DropProtect accesses the player's inventory on death and respawn events. If your server has other plugins that do the same, make sure to use DropProtect v0.3 as it has been fully redesigned to be as compatible as possible with other plugins.

Verified to be compatible with:

- MobArena v0.95.5
- xAuth (must have hide-inventory OFF or xAuth will wipe the player's inventory)
