package net.skycraftmc.SkyQuest.quest;

public abstract class Quest 
{
	public abstract boolean isComplete(int objective);
	public abstract org.bukkit.entity.Player getPlayer();
	public abstract int addObjective(Objective o);
	public abstract int getObjectives();
}
