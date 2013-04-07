package net.skycraftmc.SkyQuest;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class QuestData
{
	private Quest q;
	private String stage;
	private ArrayList<String> unassigned = new ArrayList<String>();
	private HashMap<String, String> objprog = new HashMap<String, String>();
	private String player;
	private PlayerQuestLog log;
	private boolean settingstage = false;
	private String lateststage;
	protected QuestData(PlayerQuestLog log, Quest quest)
	{
		q = quest;
		this.log = log;
		player = log.getPlayer();
		for(Objective o:q.getObjectives())unassigned.add(o.getID());
	}
	public void assign(String oid)
	{
		if(unassigned.contains(oid))
		{
			unassigned.remove(oid);
			Objective o = q.getObjective(oid);
			ObjectiveType type = o.getType();
			objprog.put(oid, type.createProgress(type.getData(o.getTarget())));
			if(SkyQuest.isOnServer())
			{
				Player p = Bukkit.getServer().getPlayerExact(player);
				if(p != null)p.sendMessage(o.getName());
			}
		}
	}
	public String getProgress(String oid)
	{
		Objective o = q.getObjective(oid);
		if(o == null)
			throw new IllegalArgumentException("Quest \"" + q.getName() + "\" has no such objective: " + oid);
		return objprog.get(oid);
	}
	public void setProgress(String oid, String progress)
	{
		Objective o = q.getObjective(oid);
		if(o == null)
			throw new IllegalArgumentException("Quest \"" + q.getName() + "\" has no such objective: " + oid);
		if(!o.getType().isValid(progress))
			throw new IllegalArgumentException("progress is not valid for type " + o.getType().getName());
		if(o.getType().isComplete(o.getTarget(), progress))
		{
			objprog.remove(oid);
			if(SkyQuest.isOnServer())
			{
				Player p = Bukkit.getServer().getPlayerExact(player);
				if(p != null)p.sendMessage(ChatColor.GREEN + "Objective completed: " + o.getName());
				for(QuestAction r:o.getRewards())r.apply(player);
			}
		}
		else objprog.put(oid, progress);
		if(isComplete())
		{
			log.complete(q);
			if(SkyQuest.isOnServer())
			{
				Player p = Bukkit.getServer().getPlayerExact(player);
				if(p != null)p.sendMessage(ChatColor.GREEN + "Quest completed: " + q.getName());
			}
		}
	}
	public Quest getQuest()
	{
		return q;
	}
	public boolean isComplete(String oid)
	{
		Objective o = q.getObjective(oid);
		if(o == null)
			throw new IllegalArgumentException("Quest \"" + q.getName() + "\" has no such objective: " + oid);
		return !objprog.containsKey(o.getID()) && !unassigned.contains(oid);
	}
	public boolean isComplete()
	{
		return objprog.size() == 0 && unassigned.size() == 0;
	}
	public boolean isAssigned(String oid)
	{
		return objprog.containsKey(oid);
	}
	public void setStage(String id)
	{
		Stage s = q.getStage(id);
		if(s == null)
			throw new IllegalArgumentException("No such stage: " + id);
		if(settingstage)
		{
			lateststage = id;
			return;
		}
		settingstage = true;
		for(QuestAction a:s.getActions())
		{
			a.apply(player);
		}
		settingstage = false;
		if(lateststage != null)
		{
			final String lstage = lateststage;
			lateststage = null;
			setStage(lstage);
		}
	}
	public String getStage()
	{
		return stage;
	}
}
