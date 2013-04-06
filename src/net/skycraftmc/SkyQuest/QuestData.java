package net.skycraftmc.SkyQuest;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class QuestData
{
	private Quest q;
	private String stage;
	private HashMap<String, String> objprog = new HashMap<String, String>();
	private String player;
	public QuestData(String player, Quest quest)
	{
		q = quest;
	}
	public String getStage()
	{
		return stage;
	}
	public void setStage(String stage)
	{
		this.stage = stage;
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
			}
			//TODO Objective rewards
		}
		else objprog.put(oid, progress);
		if(isComplete())
		{
			if(SkyQuest.isOnServer())
			{
				Player p = Bukkit.getServer().getPlayerExact(player);
				if(p != null)p.sendMessage(ChatColor.GREEN + "Quest completed: " + q.getName());
				//TODO Quest rewards
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
		return objprog.containsKey(o.getID());
	}
	public boolean isComplete()
	{
		return objprog.size() == 0;
	}
}
