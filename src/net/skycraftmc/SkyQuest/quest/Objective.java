package net.skycraftmc.SkyQuest.quest;

import java.util.ArrayList;

public abstract class Objective 
{
	private ArrayList<String>rewards = new ArrayList<String>();
	private boolean optional;
	private ObjectiveType type;
	private ArrayList<String>description = new ArrayList<String>();
	private String name;
	private String target;
	public void setType(ObjectiveType type)
	{
		this.type = type;
	}
	public boolean isOptional()
	{
		return optional;
	}
	public String[] getRewards()
	{
		return rewards.toArray(new String[rewards.size()]);
	}
	public void setRewards(ArrayList<String> rewards)
	{
		this.rewards = rewards;
	}
	public ObjectiveType getType()
	{
		return type;
	}
	public String getName()
	{
		return name;
	}
	public String getTarget()
	{
		return target;
	}
	public String[] getDescription()
	{
		return description.toArray(new String[description.size()]);
	}
}
