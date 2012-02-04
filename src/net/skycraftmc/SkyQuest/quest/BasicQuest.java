package net.skycraftmc.SkyQuest.quest;

import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;

public class BasicQuest extends Quest
{
	private Player player;
	public BasicQuest(Player player, Objective... objectives)
	{
		this.player = player;
		ob = Arrays.asList(objectives);
	}
	private List<Objective> ob;
	int co = 1;
	public boolean isComplete(int objective) 
	{
		return false;
	}
	public Player getPlayer() 
	{
		return player;
	}
	public void addObjective(Objective o) 
	{
		ob.add(o);
	}
	public void completeObjective(int index) 
	{
		co = index + 1;
	}
	public List<Objective> getObjectives() 
	{
		return ob;
	}
	public Objective getCurrentObjective()
	{
		return ob.get(co);
	}
}
