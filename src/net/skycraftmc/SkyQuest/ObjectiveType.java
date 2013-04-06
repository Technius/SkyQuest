package net.skycraftmc.SkyQuest;

public abstract class ObjectiveType 
{
	public abstract boolean isComplete(String progress);
	public abstract void setProgress(String progress);
	public abstract boolean isValid(String progress);
	public abstract String getName();
}
