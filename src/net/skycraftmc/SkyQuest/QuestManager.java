package net.skycraftmc.SkyQuest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.skycraftmc.SkyQuest.action.ActionType;
import net.skycraftmc.SkyQuest.objective.ObjectiveType;

public class QuestManager 
{
	private static QuestManager inst = null;
	public QuestManager()
	{
		if(inst != null)throw new UnsupportedOperationException("Quest manager already initialized");
		registerObjectiveType(ObjectiveType.KILL);
		registerObjectiveType(ObjectiveType.TRAVEL);
		registerActionType(ActionType.ASSIGN_OBJECTIVE);
		registerActionType(ActionType.SET_STAGE);
		registerActionType(ActionType.MESSAGE);
		registerActionType(ActionType.COMMAND);
		registerActionType(ActionType.CONSOLE_COMMAND);
		inst = this;
	}
	public static QuestManager getInstance()
	{
		return inst;
	}
	private ArrayList<Quest>quests = new ArrayList<Quest>();
	private ArrayList<PlayerQuestLog>qlogs = new ArrayList<PlayerQuestLog>();
	private HashMap<String, ObjectiveType>regisObjType = new HashMap<String, ObjectiveType>();
	private HashMap<String, ActionType>regisActType = new HashMap<String, ActionType>();
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
	public void registerActionType(ActionType type)
	{
		if(type == null)
			throw new IllegalArgumentException("type is null");
		if(regisActType.containsKey(type.getClass().getName()))
			throw new java.lang.UnsupportedOperationException(type.getClass().getName() + " is already" +
				" registered");
		regisActType.put(type.getClass().getName(), type);
	}
	public ObjectiveType getRegisteredObjectiveType(Class<? extends ObjectiveType>clazz)
	{
		return getRegisteredObjectiveType(clazz.getName());
	}
	public ObjectiveType getRegisteredObjectiveType(String name)
	{
		for(Map.Entry<String, ObjectiveType>k:regisObjType.entrySet())
		{
			if(k.getKey().equals(name))return k.getValue();
		}
		return null;
	}
	public ActionType getRegisteredActionType(Class<? extends ActionType>clazz)
	{
		return getRegisteredActionType(clazz.getName());
	}
	public ActionType getRegisteredActionType(String name)
	{
		for(Map.Entry<String, ActionType>k:regisActType.entrySet())
		{
			if(k.getKey().equals(name))return k.getValue();
		}
		return null;
	}
	public PlayerQuestLog getQuestLog(String player)
	{
		for(PlayerQuestLog q:qlogs)
		{
			if(q.getPlayer().equals(player))return q;
		}
		return null;
	}
	public void addQuestLog(PlayerQuestLog log)
	{
		if(log == null)
			throw new IllegalArgumentException("log is null");
		if(getQuestLog(log.getPlayer()) != null)return;
		qlogs.add(log);
	}
}
