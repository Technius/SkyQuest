package net.skycraftmc.SkyQuest;

import net.skycraftmc.SkyQuest.quest.Objective;
import net.skycraftmc.SkyQuest.quest.ObjectiveType;
import net.skycraftmc.SkyQuest.quest.Quest;

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
	public void killObjective(EntityDeathEvent e)
	{
		if(!(e instanceof LivingEntity))return;
		EntityDamageEvent ea = e.getEntity().getLastDamageCause();
		if(!(ea instanceof EntityDamageByEntityEvent))return;
		EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)ea;
		if(!(event.getDamager() instanceof Player))return;
		Player player = (Player)event.getDamager();
		for(Quest quest: plugin.getQuestManager().getData(player).getQuests())
		{
			for(Objective o:quest.getObjectives())
			{
				if(o.getType() != ObjectiveType.KILL)continue;
				int p = Integer.parseInt(o.getProgress());
				String[] t = o.getTarget().split("[ ]+", 2);
				if(t.length != 2)continue;
				EntityType type = EntityType.fromName(t[0].replaceAll(" ", ""));
				if(type == null)continue;
				if(event.getEntity().getType() != type)continue;
				o.setProgress("" + (p + 1));
				player.sendMessage("Progress-" + o.getName() + ": " + p + "/" + t[1]);
			}
		}
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void playerJoin(PlayerJoinEvent event)
	{
		if(!event.getPlayer().isOnline())return;
		plugin.getQuestManager().addData(event.getPlayer());
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void playerLeave(PlayerQuitEvent event)
	{
		plugin.getQuestManager().removeData(event.getPlayer());
	}
}
