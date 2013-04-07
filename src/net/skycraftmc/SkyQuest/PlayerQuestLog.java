package net.skycraftmc.SkyQuest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerQuestLog 
{
	private String player;
	private ArrayList<QuestData>assigned = new ArrayList<QuestData>();
	private HashMap<CompletedQuestData, Integer>completed = new HashMap<CompletedQuestData, Integer>();
	public PlayerQuestLog(String player)
	{
		this.player = player;
	}
	public String getPlayer()
	{
		return player;
	}
	public void assign(Quest quest)
	{
		if(isAssigned(quest))return;
		QuestData qd = new QuestData(this, quest);
		assigned.add(qd);
		if(SkyQuest.isOnServer())
		{
			Player p = Bukkit.getServer().getPlayerExact(player);
			if(p != null)p.sendMessage("Started: "  + quest.getName());
		}
		qd.setStage(quest.getFirstStage().getID());
	}
	public void complete(Quest quest)
	{
		if(!isAssigned(quest))return;
		QuestData qd = getProgress(quest);
		assigned.remove(qd);
		CompletedQuestData cqd = new CompletedQuestData(quest, qd.unassigned, qd.objprog);
		if(!hasCompleted(quest))completed.put(cqd, 1);
		else completed.put(cqd, completed.get(getCompletedData(quest)) + 1);
	}
	public boolean isAssigned(Quest quest)
	{
		for(QuestData qd:assigned)
		{
			if(qd.getQuest().getID().equals(quest.getID()))return true;
		}
		return false;
	}
	public Quest[] getAssigned()
	{
		Quest[] quests = new Quest[assigned.size()];
		int i = 0;
		for(QuestData q:assigned)
		{
			quests[i] = q.getQuest();
			i++;
		}
		return quests;
	}
	public QuestData getProgress(Quest quest)
	{
		for(QuestData qd:assigned)
		{
			if(qd.getQuest().getID().equals(quest.getID()))return qd;
		}
		return null;
	}
	public boolean hasCompleted(Quest quest)
	{
		return getCompletedData(quest) != null;
	}
	public int getTimesCompleted(Quest quest)
	{
		if(!completed.containsKey(quest.getID()))return 0;
		else return completed.get(quest.getID());
	}
	public CompletedQuestData getCompletedData(Quest quest)
	{
		for(CompletedQuestData c:completed.keySet())
		{
			if(c.getQuest() == quest)return c;
		}
		return null;
	}
	public CompletedQuestData[] getCompleted()
	{
		Set<CompletedQuestData>s = completed.keySet();
		return s.toArray(new CompletedQuestData[s.size()]);
	}
}
