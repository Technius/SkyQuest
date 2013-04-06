package net.skycraftmc.SkyQuest;

import java.util.ArrayList;

public class Objective 
{
	private String name;
	private String id;
	private ArrayList<Reward>rewards = new ArrayList<Reward>();
	private ObjectiveType type;
	private String target;
	private boolean optional = false;
	public Objective(String name, String id, ObjectiveType type, String target)
	{
		if(!type.isValid(target))
			throw new IllegalArgumentException("target is not valid for type " + type.getName());
		this.name = name;
		this.type = type;
		this.id = id;
		this.target = target;
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
	public String getTarget()
	{
		return target;
	}
	public void setTarget(String target)
	{
		if(!type.isValid(target))
			throw new IllegalArgumentException("target is not valid for type " + type.getName());
		this.target = target;
	}
	public ObjectiveType getType()
	{
		return type;
	}
	public void setType(ObjectiveType type, String target)
	{
		if(!type.isValid(target))
			throw new IllegalArgumentException("target is not valid for type "+ type.getName());
		this.type = type;
		this.target = target;
	}
	public boolean isOptional()
	{
		return optional;
	}
	public void setOptional(boolean optional)
	{
		this.optional = optional;
	}
}
