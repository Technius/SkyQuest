package net.skycraftmc.SkyQuest.objective;


public abstract class ObjectiveType 
{
	public static final KillObjectiveType KILL = new KillObjectiveType();
	public static final TravelObjectiveType TRAVEL = new TravelObjectiveType();
	public abstract boolean isComplete(String target, String progress);
	public abstract boolean isValid(String progress);
	public abstract String getName();
	public abstract String createProgress(String data);
	public abstract String getData(String progress);
	public abstract String getProgressString(String target, String progress);
	public int getItemIcon()
	{
		return 387;
	}
}
