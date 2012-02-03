package net.skycraftmc.SkyQuest;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class SkyQuestMain extends JavaPlugin
{
	Logger log = this.getLogger();
	ConfigManager cm = new ConfigManager(this);
	public void onEnable() 
	{
		log.info("Version " + getDescription().getVersion() + " enabled!");
	}
	public void onDisable() 
	{
		if(cm.checkFiles() == false)cm.createFiles(false);
		log.info("Version " + getDescription().getVersion() + " disabled!");
	}
}
