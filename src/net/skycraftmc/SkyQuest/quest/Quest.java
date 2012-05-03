package net.skycraftmc.SkyQuest.quest;

import java.util.ArrayList;

public class Quest 
{
	private ArrayList<String>next = new ArrayList<String>();
	private ArrayList<Objective>o = new ArrayList<Objective>();
	public Quest(String name, ArrayList<Objective> objectives, ArrayList<String> next)
	{
		this.name = name;
		this.next = next;
		o = objectives;
	}
	private String name;
	public String getName()
	{
		return name;
	}
	public Quest clone()
	{
		return new Quest(name, o, next);
	}
	public Objective[] getObjectives()
	{
		return o.toArray(new Objective[o.size()]);
	}
	public boolean checkComplete()
	{
		for(Objective o:this.o)
		{
			if(!o.isComplete())return false;
		}
		return true;
	}
}
