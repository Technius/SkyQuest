package net.skycraftmc.SkyQuest;

import java.util.ArrayList;
import java.util.HashMap;

public class QuestManager 
{
	private ArrayList<Quest>quests = new ArrayList<Quest>();
	private HashMap<String, ObjectiveType>regisObjType = new HashMap<String, ObjectiveType>();
	public Quest getQuest(String id)
	{
		for(Quest q:quests)
		{
			if(q.getID().equals(id))return q;
		}
		return null;
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
}