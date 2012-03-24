package net.skycraftmc.SkyQuest.quest;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Quest 
{
	String title;
	ArrayList<Objective>objectives;
	ArrayList<Objective>completed = new ArrayList<Objective>();
	boolean complete = false;
	private String dm;
	public Quest(Player player, ArrayList<Objective> objectives, String title, String display)
	{
		this.player = player;
		this.objectives = objectives;
		this.title = title;
		dm = display;
	}
	private Player player;
	public String getName()
	{
		return title;
	}
	public String getDisplayName()
	{
		return dm;
	}
	public static Quest clone(Quest quest)
	{
		ArrayList<Objective> obj = quest.getObjectives();
		ArrayList<Objective> r = new ArrayList<Objective>();
		for(Objective o:obj)
		{
			if(o instanceof KillObjective)r.add(KillObjective.clone((KillObjective)o));
		}
		return new Quest(quest.getPlayer(), quest.getObjectives(), quest.getTitle(), quest.getDisplayName());
	}
	public boolean isComplete(int objective)
	{
		if(completed.contains(objectives.get(objective)))return true;
		return false;
	}
	public void setComplete(boolean complete)
	{
		this.complete = complete;
	}
	public boolean isComplete()
	{
		return complete;
	}
	public String getTitle()
	{
		return title;
	}
	public Player getPlayer()
	{
		return player;
	}
	public void addObjective(Objective o)
	{
		objectives.add(o);
	}
	public java.util.ArrayList<Objective> getObjectives()
	{
		return objectives;
	}
	public void completeObjective(int index)
	{
		completed.add(objectives.get(index));
	}
	public void completeObjective(Objective o)
	{
		for(Objective x:objectives)
		{
			if(o == x && !completed.contains(o))completed.add(o);
		}
	}
	public Objective getCurrentObjective()
	{
		return objectives.get(completed.size());
	}
}
