package net.skycraftmc.SkyQuest;

import net.skycraftmc.SkyQuest.quest.QuestManager;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class SkyQuestMain extends JavaPlugin
{
	Logger log = this.getLogger();
	public ConfigManager cm = new ConfigManager(this);
	public QuestManager qm = new QuestManager(this);
	public void onEnable() 
	{
		if(!cm.checkFiles())cm.createFiles(false);
		log.info("Version " + getDescription().getVersion() + " enabled!");
	}
	public void onDisable() 
	{
		log.info("Version " + getDescription().getVersion() + " disabled!");
	}
}
