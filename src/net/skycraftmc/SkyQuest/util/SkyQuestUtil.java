package net.skycraftmc.SkyQuest.util;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;

public class SkyQuestUtil 
{
	public static EntityType getType(String s)
	{
		EntityType type = null;
		try
		{
			type = EntityType.valueOf(s.replaceAll(" ", "_").toUpperCase());
		}
		catch(IllegalArgumentException iae)
		{
			if(s.toUpperCase().replaceAll("_", " ").equalsIgnoreCase("ZOMBIE PIGMAN"))return EntityType.PIG_ZOMBIE;
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
	public static EntityType getTypeFromEntity(Entity e)
	{
		if(e instanceof Creeper)return EntityType.CREEPER;
		if(e instanceof Zombie)return EntityType.ZOMBIE;
		if(e instanceof Skeleton)return EntityType.SKELETON;
		if(e instanceof Enderman)return EntityType.ENDERMAN;
		if(e instanceof Spider)return EntityType.SPIDER;
		if(e instanceof CaveSpider)return EntityType.CAVE_SPIDER;
		if(e instanceof Blaze)return EntityType.BLAZE;
		if(e instanceof Pig)return EntityType.PIG;
		return null;
	}
	public static String[] argsWithQuotes(String[] args)
	{
		String a = null;
		for(String s:args)
		{
			if(a == null)a = s;
			else a = a + " " + s;
		}
		if(a == null)return new String[0];
		return argsWithQuotes(a);
	}
	public static String[] argsWithQuotes(String args)
	{
		ArrayList<String>r = new ArrayList<String>();
		String builder = "";
		String ebuilder = "";
		boolean build = false;
		for(char c:args.toCharArray())
		{
			if(c == '\"')
			{
				if(!build)
				{
					build = true;
					if(!ebuilder.isEmpty())r.add(ebuilder);
					ebuilder = "";
				}
				else
				{
					build = false;
					if(!builder.isEmpty())r.add(builder);
					builder = "";
				}
				continue;
			}
			if(build)builder += c;
			else if(c == ' ')
			{
				if(!ebuilder.isEmpty())r.add(ebuilder);
				ebuilder = "";
			}
			else if(c != ' ')ebuilder += c;
		}
		if(!ebuilder.isEmpty())r.add(ebuilder);
		ebuilder = "";
		builder = "";
		return r.toArray(new String[r.size()]);
	}
	public static boolean isSign(Block b)
	{
		return(b.getType() == Material.SIGN || b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN);
	}
	public static boolean isSign(Material m)
	{
		return(m == Material.SIGN || m == Material.SIGN_POST || m == Material.WALL_SIGN);
	}
	public static ItemStack parseItemReward(String s)
	{
		String[] tokens = s.split("[ ]+");
		if(tokens.length != 2)return null;
		if(!tokens[0].equalsIgnoreCase("i"))return null;
		String[] amount = tokens[1].split("[#]");
		if(amount.length != 2)return null;
		int id = 1;
		int a = 1;
		short dur = 0;
		String[] dura = amount[0].split("[:]");
		if(dura.length != 2 && dura.length != 1)return null;
		if(dura.length == 2)
		{
			try{dur = Short.parseShort(dura[1]);}catch(NumberFormatException nfe){}
		}
		try{a = Integer.parseInt(amount[1]);}catch(NumberFormatException nfe){}
		try{id = Integer.parseInt(dura[0]);}catch(NumberFormatException nfe){return null;}
		return new ItemStack(id, a, dur);
	}
}
