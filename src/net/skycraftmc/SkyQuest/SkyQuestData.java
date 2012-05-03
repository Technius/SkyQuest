package net.skycraftmc.SkyQuest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import net.skycraftmc.SkyQuest.quest.Objective;
import net.skycraftmc.SkyQuest.quest.ObjectiveType;
import net.skycraftmc.SkyQuest.quest.Quest;

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
			String qname = f.getName().substring(0, f.getName().length() - 3);
			try
			{
				BufferedReader br = new BufferedReader(new FileReader(f));
				String l;
				boolean obj = false;
				boolean desc = false;
				String name = null;
				ArrayList<String>d = new ArrayList<String>();
				ArrayList<String>next = new ArrayList<String>();
				String rewards = null;
				boolean opt = false;
				ObjectiveType type = null;
				String target = null;
				ArrayList<Objective> objs = new ArrayList<Objective>();
				while((l=br.readLine()) != null)
				{
					if(desc)
					{
						if(l.replaceAll(" ", "").equalsIgnoreCase("enddescription"))
						{
							desc = false;
							continue;
						}
						d.add(l);
					}
					else if(obj)
					{
						String[] tokens = l.split("[:]" ,2);
						String t = tokens[0].trim();
						if(t.equalsIgnoreCase("rewards"))rewards = tokens[1].trim();
						else if(t.equalsIgnoreCase("type"))type = ObjectiveType.getType(tokens[1]);
						else if(t.equalsIgnoreCase("target"))target = tokens[1].trim();
						else if(t.equalsIgnoreCase("description"))desc = true;
						else if(t.equalsIgnoreCase("optional"))opt = tokens[1].replaceAll(" ", "").equalsIgnoreCase("true");
						else if(tokens[0].replaceAll(" ", "").equalsIgnoreCase("endobjective"))
						{
							obj = false;
							if(rewards != null && type != null && target != null)
							{
								String[] r = rewards.split("[;]");
								ArrayList<String>ra = new ArrayList<String>();
								for(String x:r)ra.add(x);
								ArrayList<String>da = new ArrayList<String>();
								for(String x:d)da.add(x);
								Objective o = new Objective(name, target, da, ra, type, opt);
								objs.add(o);
							}
							d.clear();
							name = null;
							target = null;
							rewards = null;
						}
					}
					else
					{
						String[] tokens = l.split("[:]",2);
						if(tokens.length != 2)continue;
						if(tokens[0].startsWith("objective"))name = tokens[1].trim();
						if(tokens[0].equalsIgnoreCase("next"))next.add(tokens[1].trim());
					}
				}
				br.close();
				ArrayList<String>n = new ArrayList<String>();
				for(String x:next)n.add(x);
				Quest q = new Quest(qname, objs, n);
				plugin.getQuestManager().addQuest(q);
			}
			catch(IOException ioe)
			{
				plugin.log.severe("Failed to load quest \"" + qname + "\":" + ioe.getMessage());
			}
		}
	}
}
