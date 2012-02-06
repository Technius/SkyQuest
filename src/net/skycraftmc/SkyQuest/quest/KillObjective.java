package net.skycraftmc.SkyQuest.quest;

import java.util.List;

public class KillObjective extends Objective
{
	int progress;
	public KillObjective(String objective, String label, List<String> rewards, String text) 
	{
		super(ObjectiveType.KILL, objective, label, rewards, text);
	}
	public ObjectiveType getType() 
	{
		return ObjectiveType.KILL;
	}
	public boolean isComplete()
	{
		return false;
	}
}
