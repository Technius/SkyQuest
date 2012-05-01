package net.skycraftmc.SkyQuest;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class SkyQuestListener implements Listener
{
	@EventHandler(priority = EventPriority.MONITOR)
	public void killObjective(EntityDeathEvent e)
	{
		EntityDamageEvent ea = e.getEntity().getLastDamageCause();
		if(!(ea instanceof EntityDamageByEntityEvent))return;
		EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)ea;
	}
}
