package net.skycraftmc.SkyQuest;

import java.io.IOException;

import net.skycraftmc.SkyQuest.action.ActionType;
import net.skycraftmc.SkyQuest.objective.ObjectiveType;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyQuestPlugin extends JavaPlugin
{
	private QuestManager qm;
	private FileManager fm;
	private QuestLogManager qlm;
	private static SkyQuestPlugin inst;
	public void onEnable()
	{
		inst = this;
		SkyQuest.onServer = true;
		qm = new QuestManager();
		fm = new FileManager();
		qlm = new QuestLogManager(this);
		getServer().getPluginManager().registerEvents(new SkyQuestListener(this), this);
		getServer().getPluginManager().registerEvents(qlm, this);
		for(Player p:getServer().getOnlinePlayers())
		{
			String name = p.getName();
			if(qm.getQuestLog(name) == null)
			{
				PlayerQuestLog log = new PlayerQuestLog(name);
				qm.addQuestLog(log);
			}
		}
		try {
			fm.loadData(getDataFolder(), qm);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public QuestManager getQuestManager()
	{
		return qm;
	}
	public QuestLogManager getQuestLogManager()
	{
		return qlm;
	}
	public static SkyQuestPlugin getPlugin()
	{
		return inst;
	}
}
