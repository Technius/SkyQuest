package net.skycraftmc.SkyQuest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SkyQuestCmd implements CommandExecutor
{
	private SkyQuestMain plugin;
	public SkyQuestCmd(SkyQuestMain p)
	{
		plugin = p;
	}
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		return true;
	}
}
