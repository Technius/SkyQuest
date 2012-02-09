package net.skycraftmc.SkyQuest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import net.skycraftmc.SkyQuest.quest.KillObjective;
import net.skycraftmc.SkyQuest.quest.Objective;
import net.skycraftmc.SkyQuest.quest.Objective.ObjectiveType;
import net.skycraftmc.SkyQuest.quest.Quest;

public class ConfigManager 
{
	public SkyQuestMain main;
	public ConfigManager(SkyQuestMain plugin)
	{
		this.main = plugin;
	}
	public void createFiles(boolean refresh)
	{
		File dataFolder = new File(main.getDataFolder().getPath());
		if(!dataFolder.exists())dataFolder.mkdir();
		File players = new File(dataFolder.getPath() + File.separator + "Players");
		if(!players.exists())players.mkdir();
		if(!dataFolder.exists())dataFolder.mkdir();
		File quests = new File(dataFolder.getPath() + File.separator + "Quests");
		if(!quests.exists())quests.mkdir();
		File config = new File(dataFolder.getPath() + File.separator + "config.txt");
		try
		{
			if(!config.exists())
			{
				FileOutputStream fos = new FileOutputStream(config);
				fos.flush();
				fos.close();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(config));
			bw.write("#SkyQuest config");
			bw.newLine();
			bw.flush();
			bw.close();
		}
		catch(IOException ex)
		{
			main.log.severe("COULD NOT CREATE CONFIG! Expect errors.");
		}
	}
	public void loadData(Player player)
	{
		File dataFolder = new File(main.getDataFolder().getPath());
		if(!dataFolder.exists())dataFolder.mkdir();
		File players = new File(dataFolder.getPath() + File.separator + "Players");
		if(!players.exists())players.mkdir();
		File data = new File(players.getPath() + File.separator + player.getName() + ".txt");
		if(!data.exists())
		{
			main.qm.resetQuests(player);
			return;
		}
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(data)));
			String l;
			while((l=br.readLine()) != null)
			{
				if(l.startsWith("#"))continue;
			}
			br.close();
		}
		catch(IOException ioe)
		{
			main.log.severe("COULD NOT LOAD PLAYER DATA: " + player.getName());
			main.qm.resetQuests(player);
		}
	}
	public void loadFiles()
	{
		File dataFolder = new File(main.getDataFolder().getPath());
		File quests = new File(dataFolder.getPath() + File.separator + "Quests");
		File config = new File(dataFolder.getPath() + File.separator + "config.txt");
		try
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(config)));
			String l;
			while((l=br.readLine()) != null)
			{
				if(l.startsWith("#"))continue;
			}
			br.close();
			for(File f:quests.listFiles())
			{
				if(f.isDirectory())continue;
				if(!f.getName().endsWith(".txt"))continue;
				ArrayList<Objective> objectives = new ArrayList<Objective>();
				br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
				String objective = null;
				String name = null;
				List<String> rewards = new ArrayList<String>();
				String text = null;
				ObjectiveType type = null;
				while((l=br.readLine()) != null)
				{
					if(l.startsWith("#"))continue;
					String[] tokens = l.split("[:]", 2);
					if(tokens.length != 2)continue;
					if(tokens[0].equalsIgnoreCase("objective"))
					{
						objective = tokens[1];
					}
					else if(tokens[0].equalsIgnoreCase("label"))
					{
						name = tokens[1].trim();
					}
					else if(tokens[0].equalsIgnoreCase("text"))
					{
						text = tokens[1].trim();
					}
					else if(tokens[0].equalsIgnoreCase("reward"))
					{
						rewards.add(tokens[1].trim());
					}
					else if(tokens[0].equalsIgnoreCase("type"))
					{
						try
						{
							type = ObjectiveType.valueOf(tokens[1].trim().toUpperCase());
						}catch(IllegalArgumentException iae){}
					}
					if(name != null && text != null && type != null && objective != null)
					{
						if(type == ObjectiveType.KILL)
						{
							Objective o = new KillObjective(objective, name, rewards, text);
							objectives.add(o);
						}
						name = null;
						text = null;
						type = null;
						objective = null;
					}
				}
				Quest q = new Quest(null, objectives, f.getName().replaceAll(".txt", ""));
				main.qm.addQuest(q);
				main.log.info("Quest added: " + q.getName());
			}
		}
		catch(IOException ex)
		{
			main.log.severe("COULD NOT LOAD FILES! Expect errors.");
		}
	}
	public boolean checkFiles()
	{
		File dataFolder = new File(main.getDataFolder().getPath());
		if(!dataFolder.exists())return false;
		File config = new File(dataFolder.getPath() + File.separator + "config.txt");
		if(!config.exists())return false;
		File quests = new File(dataFolder.getPath() + File.separator + "Quests");
		if(!quests.exists())return false;
		File players = new File(dataFolder.getPath() + File.separator + "Players");
		if(!players.exists())return false;
		return true;
	}
}
