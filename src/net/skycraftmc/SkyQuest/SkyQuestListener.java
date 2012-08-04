package net.skycraftmc.SkyQuest;

import net.skycraftmc.SkyQuest.quest.Objective;
import net.skycraftmc.SkyQuest.quest.ObjectiveType;
import net.skycraftmc.SkyQuest.quest.Quest;
import net.skycraftmc.SkyQuest.util.SkyQuestUtil;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SkyQuestListener implements Listener
{
	private SkyQuestMain plugin;
	public SkyQuestListener(SkyQuestMain plugin)
	{
		this.plugin = plugin;
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void killObjective(EntityDeathEvent event)
	{
		Player player = event.getEntity().getKiller();
		if(player == null)return;
		for(Quest quest: plugin.getQuestManager().getData(player).getQuests())
		{
			for(Objective o:quest.getUnlocked())
			{
				if(o.getType() != ObjectiveType.KILL)continue;
				if(o.isComplete())continue;
				int p = Integer.parseInt(o.getProgress().replaceAll(" ", ""));
				String[] t = o.getTarget().split("[ ]+", 2);
				if(t.length != 2)continue;
				EntityType type = EntityType.fromName(t[0].toUpperCase());
				if(type == null)continue;
				if(event.getEntity().getType() != type)continue;
				o.setProgress("" + (p + 1));
				player.sendMessage(o.getName() + "(" + o.getProgress() + "/" + t[1] + ")");
				if(o.checkComplete())
				{
					player.sendMessage("Objective Completed: " + o.getName());
					SkyQuestUtil.applyRewards(player, o);
					if(quest.checkComplete())
					{
						player.sendMessage("Quest complete: " + quest.getName());
						plugin.getQuestManager().getData(player).removeQuest(quest);
					}
				}
			}
		}
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void playerJoin(PlayerJoinEvent event)
	{
		if(!event.getPlayer().isOnline())return;
		plugin.getDataManager().loadData(event.getPlayer());
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void playerLeave(PlayerQuitEvent event)
	{
		plugin.getDataManager().saveData(event.getPlayer());
		plugin.getQuestManager().removeData(event.getPlayer());
	}
}
