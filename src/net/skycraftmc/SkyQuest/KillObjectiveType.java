package net.skycraftmc.SkyQuest;

import org.bukkit.entity.EntityType;

public class KillObjectiveType extends ObjectiveType
{

	public boolean isComplete(String target, String progress) 
	{
		if(!isValid(target))
			throw new IllegalArgumentException("target is not valid");
		if(!isValid(progress))
			throw new IllegalArgumentException("target is not valid");
		String[] tokensp = progress.split(" ", 2);
		String[] tokenst = target.split(" ", 2);
		if(!tokenst[1].equalsIgnoreCase(tokensp[1]))return false;
		return tokenst[0].equals(tokensp[0]);
	}

	public boolean isValid(String progress)
	{
		String[] tokens = progress.split(" ", 2);
		if(tokens.length != 2)return false;
		try
		{
			Integer.parseInt(tokens[0]);
		}catch(NumberFormatException nfe)
		{
			return false;
		}
		if(SkyQuest.isOnServer())
		{
			EntityType et = EntityType.fromName(tokens[1].toUpperCase());
			if(et == null)return false;
			else if(et.isAlive())return true;
			else return false;
		}
		return true;
	}

	public String getName() 
	{
		return "Kill";
	}
	
}
