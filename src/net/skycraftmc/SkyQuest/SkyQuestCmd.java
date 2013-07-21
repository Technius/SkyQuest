package net.skycraftmc.SkyQuest;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SkyQuestCmd implements CommandExecutor 
{
	private SkyQuestPlugin plugin;
	public SkyQuestCmd(SkyQuestPlugin plugin)
	{
		this.plugin = plugin;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		//TODO Help
		//TODO Assign command
		if(args.length >= 1)
		{
			if(args[0].equalsIgnoreCase("questlog"))
			{
				if(noPerm(sender, "skyquest.cmd.questlog"))return true;
				Player target = null;
				if(args.length == 1)
				{
					if(!(sender instanceof Player))return usage(sender, label + " questlog <player>");
					target = (Player)sender;
				}
				else if(args.length == 2)
				{
					target = plugin.getServer().getPlayer(args[1]);
					if(target == null)
						return msg(sender, ChatColor.RED + args[1] + " cannot be found.");
				}
				else return usage(sender, label + " questlog [player]");
				if(target.getInventory().addItem(plugin.createQuestLogItem()).isEmpty())
					return msg(sender, ChatColor.GREEN + (target == sender ? "You have" : args[1] + " has") 
						+ " been given a quest log.");
				else return msg(sender, ChatColor.RED + (target == sender ? "Your" : args[1] + "'s") 
					+ " inventory is full.");
			}
			else sender.sendMessage(ChatColor.GOLD + "Unrecognized command.  Type " + ChatColor.AQUA + 
					slash(sender) + "quest help" + ChatColor.GOLD + " for help.");
		}
		else
		{
			sender.sendMessage(ChatColor.AQUA + "SkyQuest version " + plugin.getDescription().getVersion());
			sender.sendMessage(ChatColor.GOLD + "Type " + ChatColor.AQUA + slash(sender) + "quest help" + 
				ChatColor.GOLD + " for help.");
		}
		return true;
	}
	private boolean noPerm(CommandSender sender, String perm)
	{
		if(!sender.hasPermission(perm))
			return msg(sender, ChatColor.RED + perm);
		return false;
	}
	private boolean msg(CommandSender sender, String msg)
	{
		sender.sendMessage(msg);
		return true;
	}
	private boolean usage(CommandSender sender, String usage)
	{
		return msg(sender, ChatColor.RED + "Usage: " + slash(sender) + usage);
	}
	private String slash(CommandSender sender)
	{
		return sender instanceof Player ? "/" : "";
	}
}
