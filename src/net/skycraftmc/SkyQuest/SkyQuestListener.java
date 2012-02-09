package net.skycraftmc.SkyQuest;

import org.bukkit.ChatColor;
import org.bukkit.entity.Creature;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class SkyQuestListener implements Listener
{
	private SkyQuestMain plugin;
	public SkyQuestListener(SkyQuestMain plugin)
	{
		this.plugin = plugin;
	}
	@EventHandler
	public void onSignChange(SignChangeEvent event)
	{
		if(event.getLine(0).toLowerCase().equalsIgnoreCase("[accept quest]"))
		{
			event.setLine(0, ChatColor.GOLD + "[Accept Quest]");
		}
		if(event.getLine(0).toLowerCase().equalsIgnoreCase("[finish quest]"))
		{
			event.setLine(0, ChatColor.GOLD + "[Finish Quest]");
		}
	}
	@EventHandler
	public void KillObjective(EntityDeathEvent event)
	{
		
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		plugin.cm.loadData(event.getPlayer());
	}
}
