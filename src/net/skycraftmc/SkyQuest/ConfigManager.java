package net.skycraftmc.SkyQuest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

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
	public boolean checkFiles()
	{
		File dataFolder = new File(main.getDataFolder().getPath());
		if(!dataFolder.exists())return false;
		File config = new File(dataFolder.getPath() + File.separator + "config.txt");
		if(!config.exists())return false;
		return true;
	}
}
