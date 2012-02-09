package net.skycraftmc.SkyQuest;

import net.skycraftmc.SkyQuest.quest.QuestManager;

import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public class SkyQuestMain extends JavaPlugin
{
	Logger log;
	public SkyQuestListener l = new SkyQuestListener(this);
	public ConfigManager cm = new ConfigManager(this);
	public QuestManager qm = new QuestManager(this);
	public SkyQuestCmd command = new SkyQuestCmd(this);
	public void onEnable() 
	{
		log = this.getLogger();
		if(!cm.checkFiles())cm.createFiles(false);
		cm.loadFiles();
		for(org.bukkit.entity.Player p: this.getServer().getOnlinePlayers())cm.loadData(p);
		this.getServer().getPluginManager().registerEvents(l, this);
		getCommand("quest").setExecutor(command);
		log.info("Version " + getDescription().getVersion() + " enabled!");
	}
	public void onDisable() 
	{
		log.info("Version " + getDescription().getVersion() + " disabled!");
	}
}
