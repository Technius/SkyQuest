package net.skycraftmc.SkyQuest;

import net.skycraftmc.SkyQuest.quest.QuestManager;

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
	public void menu(CommandSender sender, int page)
	{
		switch(page)
		{
		case 1:
			sender.sendMessage(ChatColor.AQUA + "| - - - - {SkyQuest " + plugin.getDescription().getVersion() + "} - - - - |");
			sender.sendMessage(ChatColor.AQUA + "|"+ ChatColor.GOLD + " /quest - Displays help menu. " + ChatColor.GOLD + " |");
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
				menu(sender, 1);
			}
			else if(args.length >= 3)
			{
				if(args[0].equalsIgnoreCase("give"))
				{
					Player target = plugin.getServer().getPlayer(args[1]);
					if(target == null)sender.sendMessage(ChatColor.RED + "No such player!");
					else
					{
						String s = args[2];
						int a = 0;
						for(String sa:args)
						{
							if(a < 3)
							{
								a = a + 1;
								continue;
							}
							s = s + " " + sa;
						}
						if(plugin.qm.getQuest(s) != null)
						{
							
						}
						else sender.sendMessage(ChatColor.RED + "No such quest: " + s);
					}
				}
				else menu(sender, 1);
			}
			else menu(sender, 1);
			return true;
		}
		return true;
	}
}
