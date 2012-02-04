package net.skycraftmc.SkyQuest.quest;

public abstract class Quest 
{
	public abstract boolean isComplete(int objective);
	public abstract org.bukkit.entity.Player getPlayer();
	public abstract void addObjective(Objective o);
	public abstract java.util.List<Objective> getObjectives();
	public abstract void completeObjective(int index);
	public abstract Objective getCurrentObjective();
}
