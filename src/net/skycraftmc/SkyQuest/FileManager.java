package net.skycraftmc.SkyQuest;

import java.io.File;
import java.io.IOException;

public class FileManager 
{
	public void loadData(File file, QuestManager qm) throws IOException
	{
		if(!file.exists())return;
	}
	public void saveData(File file, QuestManager qm) throws IOException
	{
		if(!file.exists())file.mkdir();
	}
}
