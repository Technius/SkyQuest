package net.skycraftmc.SkyQuest;

import java.util.ArrayList;
import java.util.HashMap;

public class CompletedQuestData 
{
	private Quest q;
	private ArrayList<String> unassigned = new ArrayList<String>();
	private HashMap<String, String> objprog = new HashMap<String, String>();
	public CompletedQuestData(Quest q, ArrayList<String> unassigned, HashMap<String, String>progress)
	{
		this.q = q;
		this.unassigned = unassigned;
		objprog = progress;
	}
	public Quest getQuest()
	{
		return q;
	}
	public boolean isComplete()
	{
		if(objprog.size() == 0 && unassigned.size() == 0)return true;
		for(String s:unassigned)
		{
			Objective o = q.getObjective(s);
			if(o != null)
			{
				if(!o.isOptional())return false;
			}
		}
		for(String s:objprog.keySet())
		{
			Objective o = q.getObjective(s);
			if(o != null)
			{
				if(!o.isOptional())return false;
			}
		}
		return true;
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
}
