package net.skycraftmc.SkyQuest;

public abstract class ObjectiveType 
{
	public static ObjectiveType KILL = new KillObjectiveType();
	public abstract boolean isComplete(String target, String progress);
	public abstract boolean isValid(String progress);
	public abstract String getName();
}
