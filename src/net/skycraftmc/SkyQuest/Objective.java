package net.skycraftmc.SkyQuest;

import java.util.ArrayList;

import net.skycraftmc.SkyQuest.objective.ObjectiveType;

public class Objective 
{
	private String name;
	private String id;
	private Stage rewards = new Stage("");
	private ObjectiveType type;
	private String target;
	private boolean optional = false;
	private boolean visible = true;
	private int itemico = -1;
	private ArrayList<String>description = new ArrayList<String>();
	public Objective(String id, String name, ObjectiveType type, String target)
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
	public boolean isVisible()
	{
		return visible;
	}
	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
	public QuestAction[] getRewards()
	{
		return rewards.getActions();
	}
	public void addReward(QuestAction reward)
	{
		if(reward == null)
			throw new IllegalArgumentException("reward is null");
		rewards.addAction(reward);
	}
	public void addReward(QuestAction reward, int index)
	{
		if(index >= rewards.size() || index < 0)
			throw new ArrayIndexOutOfBoundsException("index is out of bounds");
		rewards.addAction(reward, index);
	}
	public void removeReward(int index)
	{
		if(index >= rewards.size() || index < 0)
			throw new ArrayIndexOutOfBoundsException("index is out of bounds");
		rewards.removeAction(index);
	}
	public int getItemIconId()
	{
		return itemico;
	}
	public void setItemIconId(int itemid)
	{
		itemico = itemid;
	}
	public String toString()
	{
		return id;
	}
	public Stage getRewardsAsStage()
	{
		return rewards;
	}
	public String[] getDescription()
	{
		return description.toArray(new String[description.size()]);
	}
	public void setDescription(ArrayList<String> s)
	{
		description = s;
	}
	public void setDescription(String... s)
	{
		description.clear();
		for(String a:s)description.add(a);
	}
}
