package net.skycraftmc.SkyQuest;

import net.skycraftmc.SkyQuest.quest.QuestManager;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class SkyQuestMain extends JavaPlugin
{
	Logger log;
	//public SkyQuestListener l = new SkyQuestListener(this);
	//public ConfigManager cm = new ConfigManager(this);
	private static QuestManager qm = null;
	public SkyQuestCmd command = new SkyQuestCmd(this);
	public void onEnable() 
	{
		log = this.getLogger();
		qm = new QuestManager(this);
		getCommand("quest").setExecutor(command);
		log.info("Version " + getDescription().getVersion() + " enabled!");
	}
	public void onDisable() 
	{
		log.info("Version " + getDescription().getVersion() + " disabled!");
	}
	public static QuestManager getQuestManager()
	{
		return qm;
	}
}
