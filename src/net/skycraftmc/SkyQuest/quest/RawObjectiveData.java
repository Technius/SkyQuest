package net.skycraftmc.SkyQuest.quest;

import java.util.ArrayList;

public class RawObjectiveData 
{
	protected ArrayList<String>rewards;
	protected ArrayList<Integer>next;
	protected boolean optional = false;
	protected boolean begin = false;
	protected ObjectiveType type;
	protected ArrayList<String>description;
	protected String name;
	protected String target;
	protected int progress = 0;
	protected boolean complete;
	public RawObjectiveData(String name, String target, ArrayList<String>description, ArrayList<String> rewards, ArrayList<Integer> next, ObjectiveType type, boolean optional, boolean begin)
	{
		this.name = name;
		this.target = target;
		this.description = description;
		this.rewards = rewards;
		this.type = type;
		this.next = next;
		this.begin = begin;
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
	public String getProgress()
	{
		if(type == ObjectiveType.KILL)return "" + progress;
		return null;
	}
	public String[] getDescription()
	{
		return description.toArray(new String[description.size()]);
	}
}
