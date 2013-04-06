package net.skycraftmc.SkyQuest;

public abstract class ObjectiveType 
{
	public static final KillObjectiveType KILL = new KillObjectiveType();
	public abstract boolean isComplete(String target, String progress);
	public abstract boolean isValid(String progress);
	public abstract String getName();
	public abstract String createProgress(String data);
	public abstract String getData(String progress);
}
