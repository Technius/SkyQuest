package net.skycraftmc.SkyQuest.action;


import org.bukkit.Bukkit;

public class ConsoleCommandAction extends ActionType
{
	public boolean apply(String player, String action) 
	{
		if(!isValid(action))
			throw new IllegalArgumentException("action is not valid");
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), action);
		return true;
	}

	public boolean isValid(String action) 
	{
		if(action == null)return false;
		return !action.trim().isEmpty();
	}

	public String getName() 
	{
		return "Console Command";
	}

	public boolean requiresPlayer() 
	{
		return false;
	}
	
}
