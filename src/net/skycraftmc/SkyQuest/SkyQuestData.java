package net.skycraftmc.SkyQuest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import net.skycraftmc.SkyQuest.quest.Objective;
import net.skycraftmc.SkyQuest.quest.ObjectiveType;
import net.skycraftmc.SkyQuest.quest.PlayerQuestData;
import net.skycraftmc.SkyQuest.quest.Quest;
import net.skycraftmc.SkyQuest.util.SkyQuestDataLoader;

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
		File file = plugin.getDataFolder();
		if(!file.exists())file.mkdir();
		File f = new File(file.getPath() + File.separator + "Players");
		if(!f.exists())f.mkdir();
		File data = new File(f.getPath() + File.separator + player.getName() + ".txt");
		try
		{
			if(!data.exists())
			{
				FileOutputStream fos = new FileOutputStream(data);
				fos.flush();
				fos.close();
			}
			BufferedWriter bw= new BufferedWriter(new FileWriter(data));
			bw.write("#Player data, DO NOT EDIT AT ANY COST");
			bw.newLine();
			for(Quest quest: plugin.getQuestManager().getData(player).getQuests())
			{
				bw.write("quest: " + quest.getName());
				bw.newLine();
				int i = 1;
				for(Objective o:quest.getObjectives())
				{
					bw.write("    objective " + i + ": " + (o.isComplete() ? "Complete" : o.getProgress()));
					bw.newLine();
					i ++;
				}
				bw.write("end quest");
				bw.newLine();
			}
			bw.flush();
			bw.close();
		}
		catch(IOException ioe)
		{
			plugin.log.severe("Failed to save \"" + player.getName() + "\"'s data: " + ioe.getMessage());
		}
	}
	public void loadData(Player player)
	{
		File file = plugin.getDataFolder();
		if(!file.exists())file.mkdir();
		File quests = new File(file.getPath() + File.separator + "Quests");
		if(!quests.exists())quests.mkdir();
		File data = new File(quests.getPath() + File.separator + player.getName() + ".txt");
		if(!data.exists())
		{
			plugin.getQuestManager().addData(player);
			return;
		}
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(data));
			String l;
			boolean q = false;
			PlayerQuestData d = new PlayerQuestData(player);
			Quest quest = null;
			while((l=br.readLine()) != null)
			{
				if(l.startsWith("#"))continue;
				String[] tokens = l.split("[:]", 2);
				if(!q)
				{
					if(tokens.length != 2)continue;
					if(tokens[0].equalsIgnoreCase("quest"))
					{
						q = true;
						if((quest=plugin.getQuestManager().getQuest(tokens[1].trim())) != null)d.addQuest(quest.clone());
					}
				}
				else
				{
					if(tokens.length != 2)
					{
						if(l.replaceAll(" ", "").equalsIgnoreCase("endquest"))
						{
							q = false;
							quest = null;
						}
					}
					if(quest == null)return;
					String[] s = tokens[0].trim().split("[ ]+", 2);
					if(s.length != 2)continue;
					if(s[0].equalsIgnoreCase("objective"))
					{
						int objn;
						try{objn = Integer.parseInt(s[1]);
						}catch(NumberFormatException nfe){continue;}
						quest.getObjectives()[objn - 1].setProgress(tokens[1].trim());
					}
				}
			}
			br.close();
			plugin.getQuestManager().addData(d);
		}
		catch(IOException ioe)
		{
			plugin.log.severe("Failed to load player data of " + player.getName() + ": " + ioe.getMessage());
		}
	}
	@SuppressWarnings("unchecked")
	public void loadQuests()
	{
		ArrayList<HashMap<String, Object>> maps = new SkyQuestDataLoader(plugin.getDataFolder()).loadQuests();
		for(HashMap<String, Object> map: maps)
		{
			if(!map.containsKey("name"))continue;
			if(!map.containsKey("objectives"))continue;
			if(!map.containsKey("next"))continue;
			String name = (String)map.get("name");
			ArrayList<Objective> obj = (ArrayList<Objective>)map.get("objectives");
			ArrayList<String>next = (ArrayList<String>)map.get("next");
			Quest quest = new Quest(name, obj, next);
			plugin.getQuestManager().addQuest(quest);
		}
	}
}
