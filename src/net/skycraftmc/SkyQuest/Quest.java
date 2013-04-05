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
}
