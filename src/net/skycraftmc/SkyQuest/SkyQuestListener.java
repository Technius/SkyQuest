package net.skycraftmc.SkyQuest;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class SkyQuestListener implements Listener
{
	SkyQuestPlugin plugin;
	QuestManager qm;
	public SkyQuestListener(SkyQuestPlugin plugin)
	{
		this.plugin = plugin;
		qm = plugin.getQuestManager();
	}
	@EventHandler
	public void killObj(EntityDeathEvent ev)
	{
		LivingEntity ent = ev.getEntity();
		if(ent.getKiller() == null)return;
		Player player = ent.getKiller();
		PlayerQuestLog log = qm.getQuestLog(player.getName());
		for(Quest q:log.getAssigned())
		{
			QuestData qd = log.getProgress(q);
			for(Objective o:q.getObjectives())
			{
				if(o.getType() != ObjectiveType.KILL)continue;
				String prog = qd.getProgress(o.getID());
				String targ = o.getTarget();
				if(!ObjectiveType.KILL.isSimilarType(targ, prog))continue;
				int p = ObjectiveType.KILL.getKills(prog);
				int t = ObjectiveType.KILL.getKills(targ);
				qd.setProgress(o.getID(), (p ++) + " " + ent.getType().name());
			}
		}
	}
}
