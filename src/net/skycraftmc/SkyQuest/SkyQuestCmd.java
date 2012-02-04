package net.skycraftmc.SkyQuest;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkyQuestCmd implements CommandExecutor
{
	private SkyQuestMain plugin;
	public SkyQuestCmd(SkyQuestMain plugin)
	{
		this.plugin = plugin;
	}
	public void menu(Player p, int page)
	{
		switch(page)
		{
		case 1:
			if(p == null)
			{
				plugin.log.info(ChatColor.AQUA + "| - - - - {SkyQuest " + plugin.getDescription().getVersion() + "} - - - - |");
				plugin.log.info(ChatColor.AQUA + "|"+ ChatColor.GOLD + " /quest - Displays help menu. " + ChatColor.GOLD + " |");
			}
			else
			{
				p.sendMessage(ChatColor.AQUA + "| - - - - {SkyQuest " + plugin.getDescription().getVersion() + "} - - - - |");
				p.sendMessage(ChatColor.AQUA + "|"+ ChatColor.GOLD + " /quest - Displays help menu. " + ChatColor.GOLD + " |");
			}
			return;
		}
	}
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		Player player = null;
		if(sender instanceof Player)player = (Player)sender;
		if(cmd.getName().equalsIgnoreCase("quest"))
		{
			if(args.length == 0)
			{
				menu(player, 0);
			}
			return true;
		}
		return false;
	}
}
