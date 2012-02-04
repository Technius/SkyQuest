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
				br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
				int objective = 1;
				String name = null;
				List<String> rewards = new ArrayList<String>();
				String text = null;
				while((l=br.readLine()) != null)
				{
					if(l.startsWith("#"))continue;
					String[] tokens = l.split("[:]", 2);
					if(tokens.length != 2)continue;
					if(tokens[0].startsWith("objective"))
					{
						String[] tok = tokens[0].split("[ ]", 2);
						if(tok.length == 2)
						{
							try{objective = Integer.parseInt(tok[1]);}catch(NumberFormatException nfe){}
						}
						name = tokens[1].trim();
					}
					if(name != null && text != null)
					{
						
					}
				}
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
		return true;
	}
}
