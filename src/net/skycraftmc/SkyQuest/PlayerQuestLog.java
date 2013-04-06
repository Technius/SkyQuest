package net.skycraftmc.SkyQuest;

import java.util.ArrayList;

public class PlayerQuestLog 
{
	private String player;
	private ArrayList<QuestData>assigned = new ArrayList<QuestData>();
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
		assigned.add(new QuestData(player, quest));
	}
	public boolean isAssigned(Quest quest)
	{
		for(QuestData qd:assigned)
		{
			if(qd.getQuest() == quest)return true;
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
			if(qd.getQuest() == quest)return qd;
		}
		return null;
	}
}
