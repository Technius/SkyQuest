package net.skycraftmc.SkyQuest;

import net.skycraftmc.SkyQuest.quest.Objective;
import net.skycraftmc.SkyQuest.quest.Quest;
import net.skycraftmc.SkyQuest.util.SkyQuestUtil;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkyQuestCmd implements CommandExecutor
{
	private SkyQuestMain plugin;
	public SkyQuestCmd(SkyQuestMain p)
	{
		plugin = p;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		Player player = null;
		if(sender instanceof Player)player = (Player)sender;
		if(args.length >= 1)
		{
			if(args[0].equalsIgnoreCase("info"))
			{
				if(args.length == 1)return usage(sender, "/quest info <quest>");
				String s = SkyQuestUtil.combine(args, 1);
				Quest quest;
				if((quest=plugin.getQuestManager().getQuest(s)) == null)return msgret(sender, ChatColor.RED + "No such quest: " + s);
				sender.sendMessage("Quest info: " + quest.getName());
				Objective[] oa = quest.getObjectives();
				int num = 1;
				for(Objective o:oa)
				{
					sender.sendMessage("Objective " + num + ":" + o.getName());
					sender.sendMessage("Type: " + o.getType().toString());
					sender.sendMessage("Target: " + o.getTarget());
					boolean a = false;
					for(String l:o.getDescription())
					{
						if(!a)
						{
							sender.sendMessage("Description: " + l);
							a = true;
						}
						else sender.sendMessage("    " + l);
					}
					sender.sendMessage("Optional: " + o.isOptional());
					num ++;
				}
				sender.sendMessage("Next Quests: ");
				for(String sa:quest.getNextQuests())sender.sendMessage("    " + sa);
			}
			else if(args[0].equalsIgnoreCase("grant"))
			{
				if(args.length == 1 || args.length == 2)return usage(sender, "/quest grant <player> <quest>");
				Player p = sender.getServer().getPlayer(args[1]);
				if(p == null)msgret(sender, ChatColor.RED + "No such player:" + args[1]);
				String q = SkyQuestUtil.combine(args, 2);
				Quest quest = plugin.getQuestManager().getQuest(q);
				if(quest == null)return msgret(sender, ChatColor.RED + "No such quest: " + q);
				if(plugin.getQuestManager().getData(p).hasQuest(quest))return msgret(sender, ChatColor.RED + p.getName() + " already has " + quest.getName() + "!");
				plugin.getQuestManager().getData(p).grantQuest(quest);
				sender.sendMessage(ChatColor.GREEN + p.getName() + " has receieved the quest " + q);
			}
			else if(args[0].equalsIgnoreCase("view"))
			{
				if(args.length == 1)return usage(sender, "/quest view <quest>");
				if(player == null)msgret(sender, "Console cannot use this command");
				String s = SkyQuestUtil.combine(args, 1);
				Quest quest;
				if((quest = plugin.getQuestManager().getData(player).getQuest(s)) == null)return msgret(sender, ChatColor.RED + "You don't have the quest: " + s);
				sender.sendMessage("Quest: " + quest.getName());
				Objective[] oa = quest.getUnlocked();
				int num = 1;
				for(Objective o:oa)
				{
					sender.sendMessage("Objective " + num + ":" + o.getName());
					sender.sendMessage("Type: " + o.getType().toString());
					sender.sendMessage("Target: " + o.getTarget());
					boolean a = false;
					for(String l:o.getDescription())
					{
						if(!a)
						{
							sender.sendMessage("Description: " + l);
							a = true;
						}
						else sender.sendMessage("    " + l);
					}
					sender.sendMessage("Optional: " + o.isOptional());
					num ++;
				}
			}
		}
		return true;
	}
	public boolean usage(CommandSender sender, String message)
	{
		sender.sendMessage(ChatColor.RED + "Usage: " + message);
		return true;
	}
	public boolean msgret(CommandSender sender, String message)
	{
		sender.sendMessage(message);
		return true;
	}
	public boolean noperm(CommandSender sender)
	{
		sender.sendMessage(ChatColor.RED + "You do not have the permissions to use this command!");
		return true;
	}
}
