package net.skycraftmc.SkyQuest.quest;

import java.util.ArrayList;

public class Objective extends RawObjectiveData
{
	public Objective(String name, String target, ArrayList<String> description,
			ArrayList<String> rewards, ArrayList<Integer> next,
			ObjectiveType type, boolean optional, boolean begin) {
		super(name, target, description, rewards, next, type, optional, begin);
	}
	public Objective clone()
	{
		return new Objective(name, target, description, rewards, next, type, optional, begin);
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
	public Integer[] getNext()
	{
		return next.toArray(new Integer[next.size()]);
	}
	public boolean isDefault()
	{
		return begin;
	}
}
