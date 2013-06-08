package net.skycraftmc.SkyQuest.action;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageAction extends ActionType
{
	public boolean apply(String player, String action) 
	{
		if(!isValid(action))
			throw new IllegalArgumentException("action is not valid");
		Player p = Bukkit.getServer().getPlayerExact(player);
		if(p == null)return false;
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', action));
		return true;
	}

	public boolean isValid(String action) 
	{
		if(action == null)return false;
		return !action.trim().isEmpty();
	}

	public String getName() 
	{
		return "Message";
	}

	public boolean requiresPlayer() 
	{
		return true;
	}
	
}
