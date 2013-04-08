package net.skycraftmc.SkyQuest;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandAction extends ActionType
{
	public boolean apply(String player, String action) 
	{
		if(!isValid(action))
			throw new IllegalArgumentException("action is not valid");
		Player p = Bukkit.getServer().getPlayerExact(player);
		if(p == null)return false;
		Bukkit.getServer().dispatchCommand(p, action);
		return true;
	}

	public boolean isValid(String action) 
	{
		if(action == null)return false;
		return !action.trim().isEmpty();
	}

	public String getName() 
	{
		return "Command";
	}

	public boolean requiresPlayer() 
	{
		return true;
	}
	
}
