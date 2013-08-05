package net.skycraftmc.SkyQuest;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class QuestProgress
{
	protected Quest q;
	ArrayList<String> unassigned = new ArrayList<String>();
	HashMap<String, String> objprog = new HashMap<String, String>();
	CompletionState state;
	QuestProgress(Quest quest)
	{
		q = quest;
	}
	public Quest getQuest()
	{
		return q;
	}
	public CompletionState getState()
	{
		return state;
	}
	public String getProgress(String oid)
	{
		Objective o = q.getObjective(oid);
		if(o == null)
			throw new IllegalArgumentException("Quest \"" + q.getName() + "\" has no such objective: " + oid);
		return objprog.get(oid);
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
		return state != CompletionState.IN_PROGRESS;
	}
}
