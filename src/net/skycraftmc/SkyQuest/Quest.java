package net.skycraftmc.SkyQuest;

import java.util.ArrayList;

public class Quest 
{
	private String name;
	private String id;
	private ArrayList<Objective>obj = new ArrayList<Objective>();
	private ArrayList<Stage>stages = new ArrayList<Stage>();
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
	public Stage getStage(String id)
	{
		for(Stage s:stages)
		{
			if(s.getID().equals(id))return s;
		}
		return null;
	}
	public void addStage(Stage stage)
	{
		if(stage == null)
			throw new IllegalArgumentException("stage is null");
		if(getObjective(stage.getID()) != null)return;
		stages.add(stage);
	}
	public void removeStage(String id)
	{
		Stage s = getStage(id);
		if(s != null)stages.remove(s);
	}
	public Objective[] getObjectives()
	{
		return obj.toArray(new Objective[obj.size()]);
	}
}
