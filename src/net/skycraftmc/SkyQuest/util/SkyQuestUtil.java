package net.skycraftmc.SkyQuest.util;

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
}
