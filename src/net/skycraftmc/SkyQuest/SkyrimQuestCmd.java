package net.skycraftmc.SkyQuest;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SkyrimQuestCmd implements CommandExecutor
{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		if(cmd.getName().equalsIgnoreCase("quest"))
		{
			if(args.length == 0)
			{
				sender.sendMessage(ChatColor.AQUA + "| - - - - {SkyrimQuest} - - - - |");
				sender.sendMessage(ChatColor.AQUA + "|"+ ChatColor.GOLD + " /quest - Displays help menu. " + ChatColor.GOLD + " |");
			}
			return true;
		}
		return false;
	}

}
