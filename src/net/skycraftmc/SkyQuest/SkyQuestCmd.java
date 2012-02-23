package net.skycraftmc.SkyQuest;

import java.util.ArrayList;

import net.skycraftmc.SkyQuest.quest.Objective;
import net.skycraftmc.SkyQuest.quest.Quest;
import net.skycraftmc.SkyQuest.quest.QuestManager;
import net.skycraftmc.SkyQuest.util.SkyQuestUtil;

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
			if(sender.hasPermission("skyquest.give"))sender.sendMessage(ChatColor.AQUA + "|"+ ChatColor.GOLD + " /quest give <player> - Gives a quest to a player");
			if(sender.hasPermission("skyquest.create"))sender.sendMessage(ChatColor.AQUA + "|"+ ChatColor.GOLD + " /quest create <name> - Creates a quest with given name");
			sender.sendMessage(ChatColor.AQUA + "|" + ChatColor.GOLD + "/quest info <quest> - Shows summerized info about a quest");
			sender.sendMessage(ChatColor.AQUA + "|" + ChatColor.GOLD + "/quest list - Lists your currently assigned quests(all if console)");
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
			else if(args.length >= 2)
			{
				if(args[0].equalsIgnoreCase("give"))
				{
					if(args.length <= 2)sender.sendMessage(ChatColor.RED + "Usage: /quest give <player> <quest>");
					if(player != null)
					{
						if(!player.hasPermission("skyquest.give"))player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
					}
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
				
							plugin.qm.addQuest(target, plugin.qm.getQuest(s));
							sender.sendMessage(ChatColor.GREEN + s + " has been given to " + target.getName());
						}
						else sender.sendMessage(ChatColor.RED + "No such quest: " + s);
					}
				}
				else if(args[0].equalsIgnoreCase("create"))
				{
					if(player != null)
					{
						if(!player.hasPermission("skyquest.create"))player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
					}
					String s = args[1];
					boolean a = false;
					for(String sa:args)
					{
						if(!a)
						{
							a = true;
							continue;
						}
						s = s + " " + sa;
					}
					Quest q = new Quest(null, new ArrayList<Objective>(), s);
					plugin.qm.addQuest(q);
					sender.sendMessage(ChatColor.GREEN + "Quest created: " + s);
					if(player != null)plugin.log.info(player.getName() + " created quest: " + s);
				}
				else if(args[0].equalsIgnoreCase("info"))
				{
					if(player == null)sender.sendMessage("You cannot use this command!");
					else
					{
						String[] na = SkyQuestUtil.argsWithQuotes(args);
						if(na.length != 2)menu(player, 1);
						else
						{
							Quest q = plugin.qm.getQuest(na[1]);
							if(q != null)
							{
								if(!plugin.qm.hasQuest(player, q.getName()))
								{
									player.sendMessage(ChatColor.RED + "You don't have this quest");
									return true;
								}
								Quest pq = plugin.qm.getQuest(player, q.getTitle());
								player.sendMessage(ChatColor.GOLD + "|===( Quest Info: " + ChatColor.RED + pq.getTitle() + " )===|");
								player.sendMessage(ChatColor.AQUA + "Objective: " + ChatColor.GREEN + pq.getCurrentObjective().getLabel());
								player.sendMessage(ChatColor.AQUA + "Description: " + ChatColor.GREEN + pq.getCurrentObjective().getText());
								player.sendMessage(ChatColor.AQUA + "Target: " + ChatColor.GREEN + pq.getCurrentObjective().getTarget());
								player.sendMessage(ChatColor.AQUA + "Progress: " + ChatColor.GREEN + pq.getCurrentObjective().getProgressAsString());
							}
							else player.sendMessage(ChatColor.RED + "No such quest: " + na[1]);
						}
					}
				}
				else menu(sender, 1);
			}
			else if(args.length >= 1)
			{
				if(args[0].equalsIgnoreCase("list"))
				{
					if(player == null)
					{
						ArrayList<Quest> q = (ArrayList<Quest>) QuestManager.allquests;
						sender.sendMessage("|=====(All Quests)=====|");
						for(Quest x:q)
						{
							sender.sendMessage(x.getName());
						}
					}
					else
					{
						ArrayList<Quest> q = (ArrayList<Quest>) plugin.qm.getQuests(player);
						player.sendMessage(ChatColor.GOLD + "|=====(Your Quests)=====|");
						for(Quest qx: q)
						{
							player.sendMessage(ChatColor.RED + qx.getTitle());
						}
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
