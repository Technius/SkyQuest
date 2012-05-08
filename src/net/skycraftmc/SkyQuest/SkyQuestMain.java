package net.skycraftmc.SkyQuest;

import net.skycraftmc.SkyQuest.quest.QuestManager;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class SkyQuestMain extends JavaPlugin
{
	Logger log;
	//public SkyQuestListener l = new SkyQuestListener(this);
	//public ConfigManager cm = new ConfigManager(this);
	private final QuestManager qm = new QuestManager(this);
	private final SkyQuestData files = new SkyQuestData(this);
	public SkyQuestCmd command = new SkyQuestCmd(this);
	public void onEnable() 
	{
		log = this.getLogger();
		getCommand("quest").setExecutor(command);
		files.loadQuests();
		for(Player player:getServer().getOnlinePlayers())
		{
			qm.addData(player);
			files.loadData(player);
		}
		getServer().getPluginManager().registerEvents(new SkyQuestListener(this), this);
		log.info("Version " + getDescription().getVersion() + " enabled!");
	}
	public void onDisable() 
	{
		for(Player player:getServer().getOnlinePlayers())files.saveData(player);
		qm.clearQuests();
		qm.clearData();
		log.info("Version " + getDescription().getVersion() + " disabled!");
	}
	public QuestManager getQuestManager()
	{
		return qm;
	}
	public SkyQuestData getDataManager()
	{
		return files;
	}
}
