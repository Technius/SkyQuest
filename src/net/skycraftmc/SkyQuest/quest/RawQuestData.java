package net.skycraftmc.SkyQuest.quest;

import java.util.ArrayList;

public class RawQuestData 
{
	protected ArrayList<String>next = new ArrayList<String>();
	protected ArrayList<RawObjectiveData>o = new ArrayList<RawObjectiveData>();
	protected String name;
	public RawQuestData(String name)
	{
		this.name = name;
	}
	public RawObjectiveData[] getObjectives()
	{
		return o.toArray(new RawObjectiveData[o.size()]);
	}
	public String[] getNextQuests()
	{
		return next.toArray(new String[next.size()]);
	}
	public String getName()
	{
		return name;
	}
	public void addRawObjective(RawObjectiveData data)
	{
		if(getRawObjective(data.getName()) != null)return;
		o.add(data);
	}
	public void removeRawObjective(RawObjectiveData data)
	{
		o.remove(data);
	}
	public RawObjectiveData getRawObjective(String name)
	{
		for(RawObjectiveData data: o)
		{
			if(data.getName().equals(name))return data;
		}
		return null;
	}
}
