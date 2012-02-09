package net.skycraftmc.SkyQuest.util;

import java.util.ArrayList;

import org.bukkit.entity.CreatureType;

public class SkyQuestUtil 
{
	public static CreatureType getType(String s)
	{
		CreatureType type = null;
		try
		{
			type = CreatureType.valueOf(s.replaceAll(" ", "_"));
		}
		catch(IllegalArgumentException iae)
		{
			if(s.toUpperCase().replaceAll("_", " ").equalsIgnoreCase("ZOMBIE PIGMAN"))return CreatureType.PIG_ZOMBIE;
		}
		return type;
	}
	public static String[] argsWithQuotes(String[] args)
	{
		String s = "";
		boolean in = false;
		ArrayList<String> sa = new ArrayList<String>();
		for(String x:args)
		{
			if(x.startsWith("\""))in = true;
			s = s + x;
			if(x.endsWith("\""))
			{
				in = false;
				sa.add(s.replaceAll("\"", ""));
			}
			if(!in)sa.add(x);
		}
		return sa.toArray(new String[sa.size()]);
	}
}
