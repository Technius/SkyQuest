package net.skycraftmc.SkyQuest.quest;

import java.util.ArrayList;

public class Objective 
{
	private ArrayList<String>rewards = new ArrayList<String>();
	private boolean optional = false;
	private ObjectiveType type;
	private ArrayList<String>description = new ArrayList<String>();
	private String name;
	private String target;
	private int progress;
	private boolean complete;
	public Objective(String name, String target, ArrayList<String>description, ArrayList<String> rewards, ObjectiveType type, boolean optional)
	{
		this.name = name;
		this.target = target;
		this.description = description;
		this.rewards = rewards;
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
	public String getProgress()
	{
		if(type == ObjectiveType.KILL)return "" + progress;
		return null;
	}
	public String[] getDescription()
	{
		return description.toArray(new String[description.size()]);
	}
	public Objective clone()
	{
		return new Objective(name, target, description, rewards, type, optional);
	}
	public void setProgress(String progress)
	{
		if(type == ObjectiveType.KILL)
		{
			try{this.progress = Integer.parseInt(progress);}
			catch(Exception e){throw new IllegalArgumentException("Type=KILL, so progress must be an integer");}
		}
	}
	public boolean isComplete()
	{
		return complete;
	}
	public boolean checkComplete()
	{
		if(type == ObjectiveType.KILL)
		{
			String[] t = target.split("[ ]", 2);
			int i = Integer.parseInt(t[1]);
			if(progress >= i)complete = true;
		}
		return isComplete();
	}
	public void setComplete(boolean complete)
	{
		this.complete = complete;
	}
}
