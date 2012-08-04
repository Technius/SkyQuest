package net.skycraftmc.SkyQuest.quest;

import java.util.ArrayList;

public class Quest extends RawQuestData
{
	private ArrayList<Objective>o = new ArrayList<Objective>();
	public Quest(String name, ArrayList<Objective> objectives, ArrayList<String> next)
	{
		super(name);
		this.name = name;
		this.next = next;
		o = objectives;
	}
	public Quest clone()
	{
		ArrayList<Objective>o = new ArrayList<Objective>();
		for(Objective oa:this.o)o.add(oa.clone());
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
	public String[] getNextQuests()
	{
		return next.toArray(new String[next.size()]);
	}
	public Objective[] getUnlocked()
	{
		ArrayList<Objective>a = new ArrayList<Objective>();
		for(Objective o: this.o)
		{
			if(o.isDefault())a.add(o);
			for(int i:o.getNext())
			{
				if(i - 1 >= this.o.size())continue;
				Objective oa = a.get(i);
				if(!a.contains(oa))a.add(oa);
			}
		}
		return a.toArray(new Objective[a.size()]);
	}
}
