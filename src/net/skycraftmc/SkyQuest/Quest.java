package net.skycraftmc.SkyQuest;

import java.util.ArrayList;

public class Quest 
{
	private String name;
	private String id;
	private ArrayList<Objective>obj = new ArrayList<Objective>();
	public Quest(String name, String id)
	{
		this.name = name;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getID()
	{
		return id;
	}
	public Objective getObjective(String id)
	{
		for(Objective o:obj)
		{
			if(o.getID().equals(id))return o;
		}
		return null;
	}
	public void addObjective(Objective obj)
	{
		if(obj == null)
			throw new IllegalArgumentException("obj is null");
		if(getObjective(obj.getID()) != null)return;
		this.obj.add(obj);
	}
	public void removeObjective(String id)
	{
		Objective o = getObjective(id);
		if(o != null)obj.remove(o);
	}
}
