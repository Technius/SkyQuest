package net.skycraftmc.SkyQuest;

import java.io.File;

import org.bukkit.entity.Player;

public class SkyQuestData 
{
	private SkyQuestMain plugin;
	public SkyQuestData(SkyQuestMain plugin)
	{
		this.plugin = plugin;
	}
	public void saveData(Player player)
	{
		
	}
	public void loadQuests()
	{
		File file = plugin.getDataFolder();
		if(!file.exists())file.mkdir();
		File quests = new File(file.getPath() + File.separator + "Quests");
		if(!quests.exists())quests.mkdir();
		for(File f:quests.listFiles())
		{
			if(!f.getName().endsWith(".txt"))continue;
			if(f.getName().length() <= 4)continue;
		}
	}
}
