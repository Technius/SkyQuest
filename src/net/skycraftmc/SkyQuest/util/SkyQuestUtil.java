package net.skycraftmc.SkyQuest.util;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;

public class SkyQuestUtil 
{
	public static CreatureType getType(String s)
	{
		CreatureType type = null;
		try
		{
			type = CreatureType.valueOf(s.replaceAll(" ", "_").toUpperCase());
		}
		catch(IllegalArgumentException iae)
		{
			if(s.toUpperCase().replaceAll("_", " ").equalsIgnoreCase("ZOMBIE PIGMAN"))return CreatureType.PIG_ZOMBIE;
		}
		return type;
	}
	public static String getType(Entity e)
	{
		if(e instanceof Creeper)return "Creeper";
		if(e instanceof Player)return "Player";
		if(e instanceof Zombie)return "Zombie";
		if(e instanceof Skeleton)return "Skeleton";
		if(e instanceof Enderman)return "Enderman";
		if(e instanceof Spider)return "Spider";
		if(e instanceof CaveSpider)return "CaveSpider";
		if(e instanceof Blaze)return "Blaze";
		return null;
	}
	public static CreatureType getTypeFromEntity(Entity e)
	{
		if(e instanceof Creeper)return CreatureType.CREEPER;
		if(e instanceof Zombie)return CreatureType.ZOMBIE;
		if(e instanceof Skeleton)return CreatureType.SKELETON;
		if(e instanceof Enderman)return CreatureType.ENDERMAN;
		if(e instanceof Spider)return CreatureType.SPIDER;
		if(e instanceof CaveSpider)return CreatureType.CAVE_SPIDER;
		if(e instanceof Blaze)return CreatureType.BLAZE;
		if(e instanceof Pig)return CreatureType.PIG;
		return null;
	}
	public static String[] argsWithQuotes(String[] args)
	{
		String s = "";
		boolean in = false;
		ArrayList<String> sa = new ArrayList<String>();
		for(String x:args)
		{
			if(x.startsWith("\""))in = true;
			if(in)s = s + x;
			if(x.endsWith("\""))
			{
				in = false;
				sa.add(s.replaceAll("\"", ""));
				s = "";
			}
			if(!in)sa.add(x);
		}
		return sa.toArray(new String[sa.size()]);
	}
	public static boolean isSign(Block b)
	{
		return(b.getType() == Material.SIGN || b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN);
	}
	public static boolean isSign(Material m)
	{
		return(m == Material.SIGN || m == Material.SIGN_POST || m == Material.WALL_SIGN);
	}
}
