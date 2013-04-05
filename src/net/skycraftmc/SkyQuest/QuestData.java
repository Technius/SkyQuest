package net.skycraftmc.SkyQuest;

import java.util.HashMap;

public class QuestData
{
	private Quest q;
	private String stage;
	private HashMap<String, String> objprog = new HashMap<String, String>();
	public QuestData(Quest quest)
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
	public String getProgress(String qid)
	{
		Objective o = q.getObjective(qid);
		if(o == null)
			throw new IllegalArgumentException("Quest \"" + q.getName() + "\" has no such objective: " + qid);
		return objprog.get(qid);
	}
	public Quest getQuest()
	{
		return q;
	}
}
