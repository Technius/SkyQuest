package net.skycraftmc.SkyQuest.quest;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Quest 
{
	private Player player;
	private ArrayList<Objective>o = new ArrayList<Objective>();
	public Quest(Player player, String name)
	{
		this.player = player;
		this.name = name;
	}
	public Quest(Player player, String name, ArrayList<Objective> objectives)
	{
		this.player = player;
		this.name = name;
		o = objectives;
	}
	private String name;
	public String getName()
	{
		return name;
	}
	public Player getPlayer()
	{
		return player;
	}
	public Quest clone()
	{
		return new Quest(player, name, o);
	}
	public Objective[] getObjectives()
	{
		return o.toArray(new Objective[o.size()]);
	}
}
