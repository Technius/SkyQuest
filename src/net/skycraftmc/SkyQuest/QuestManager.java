package net.skycraftmc.SkyQuest;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestManager 
{
	private ArrayList<Quest>quests = new ArrayList<Quest>();
	private ArrayList<PlayerQuestLog>qlogs = new ArrayList<PlayerQuestLog>();
	private HashMap<String, ObjectiveType>regisObjType = new HashMap<String, ObjectiveType>();
	public Quest getQuest(String id)
	{
		for(Quest q:quests)
		{
			if(q.getID().equals(id))return q;
		}
		return null;
	}
	public void addQuest(Quest quest)
	{
		if(quest == null)
			throw new IllegalArgumentException("quest is null");
		if(getQuest(quest.getID()) != null)return;
		quests.add(quest);
	}
	public void registerObjectiveType(ObjectiveType type)
	{
		if(type == null)
			throw new IllegalArgumentException("type is null");
		if(regisObjType.containsKey(type.getClass().getName()))
			throw new java.lang.UnsupportedOperationException(type.getClass().getName() + " is already" +
				" registered");
		regisObjType.put(type.getClass().getName(), type);
	}
	public PlayerQuestLog getQuestLog(String player)
	{
		for(PlayerQuestLog q:qlogs)
		{
			if(q.getPlayer().equals(player))return q;
		}
		return null;
	}
}
