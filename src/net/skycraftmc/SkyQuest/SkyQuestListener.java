package net.skycraftmc.SkyQuest;

import net.skycraftmc.SkyQuest.quest.Objective.ObjectiveType;
import net.skycraftmc.SkyQuest.quest.Quest;
import net.skycraftmc.SkyQuest.quest.KillObjective;
import net.skycraftmc.SkyQuest.util.SkyQuestUtil;

import org.bukkit.ChatColor;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
		String s = SkyQuestUtil.getType(event.getEntity());
		if(s == null)return;
		CreatureType c = SkyQuestUtil.getType(s.toUpperCase());
		if(c == null)return;
		if(event.getEntity().getLastDamageCause() == null)return;
		if(!(event.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent))return;
		if((((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager() instanceof Player))return;
		Player player = (Player)((EntityDamageByEntityEvent)event.getEntity().getLastDamageCause()).getDamager();
		for(Quest q:plugin.qm.getQuests(player))
		{
			if(q.getCurrentObjective().getType() != ObjectiveType.KILL)continue;
			KillObjective ko = (KillObjective)q.getCurrentObjective();
			if(ko.isComplete())return;
			if(ko.getTargetType() == null)return;
			if(c != ko.getTargetType())return;
			ko.setProgress(ko.getProgress() + 1);
			player.sendMessage(ko.getTarget());
		}
	}
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		plugin.cm.loadData(event.getPlayer());
	}
}
