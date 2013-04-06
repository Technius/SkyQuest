package net.skycraftmc.SkyQuest;

public abstract class ActionType 
{
	public abstract boolean apply(String player, String action);
	/**
	 * Applies this action.
	 * This has the same effect as calling action(null, action).
	 * @param action A string containing parameters for this action.
	 */
	public void apply(String action)
	{
		apply(null, action);
	}
	public abstract boolean isValid(String action);
	public abstract String getName();
}
