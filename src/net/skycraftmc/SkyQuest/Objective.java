package net.skycraftmc.SkyQuest;

import java.util.ArrayList;

public class Objective 
{
	private String name;
	private String id;
	private ArrayList<Reward>rewards = new ArrayList<Reward>();
	public Objective(String name, String id)
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
}
