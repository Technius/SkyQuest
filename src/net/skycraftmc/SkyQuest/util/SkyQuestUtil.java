package net.skycraftmc.SkyQuest.util;

import java.util.ArrayList;

import net.skycraftmc.SkyQuest.quest.Objective;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
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
		return e.getType().toString();
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
	public static void applyRewards(Player player, Objective o)
	{
		for(String s:o.getRewards())
		{
			String[] tokens = s.split("[:]", 2);
			if(tokens.length != 2)continue;
			if(tokens[0].equalsIgnoreCase("i"))
			{
				int t;
				Material type;
				short dur = 0;
				int a;
				String[] d = tokens[1].replaceAll(" ", "").split("[:#]", 3);
				if(d.length != 2 && d.length != 3)continue;
				try{t=Integer.parseInt(d[0]);
				type = Material.getMaterial(t);}
				catch(NumberFormatException nfe){type = Material.getMaterial(d[0]);}
				if(type == null)continue;
				try
				{
					if(d.length == 2)
					{
						dur = Short.parseShort(d[1]);
						a = Integer.parseInt(d[2]);
					}
					else a = Integer.parseInt(d[1]);
				}catch(NumberFormatException nfe){continue;}
				ItemStack i = new ItemStack(type, a, dur);
				player.getInventory().addItem(i);
			}
		}
	}
	public boolean rewardSyntax(String s)
	{
		String[] tokens = s.split("[:]", 2);
		if(tokens.length != 2)return false;
		if(tokens[0].equalsIgnoreCase("i"))
		{
			int t;
			Material type;
			String[] d = tokens[1].replaceAll(" ", "").split("[:#]", 3);
			if(d.length != 2 && d.length != 3)return false;
			try{t=Integer.parseInt(d[0]);
			type = Material.getMaterial(t);}
			catch(NumberFormatException nfe){type = Material.getMaterial(d[0]);}
			if(type == null)return false;
			try
			{
				if(d.length == 2)
				{
					Short.parseShort(d[1]);
					Integer.parseInt(d[2]);
				}
				else Integer.parseInt(d[1]);
			}catch(NumberFormatException nfe){return false;}
			return true;
		}
		else return false;
	}
}
