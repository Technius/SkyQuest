package net.skycraftmc.SkyQuest;

public abstract class ActionType 
{
	public static final AssignObjectiveAction ASSIGN_OBJECTIVE = new AssignObjectiveAction();
	public static final SetStageAction SET_STAGE = new SetStageAction();
	public static final MessageAction MESSAGE = new MessageAction();
	public static final CommandAction COMMAND = new CommandAction();
	public static final ConsoleCommandAction CONSOLE_COMMAND = new ConsoleCommandAction();
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
	public abstract boolean requiresPlayer();
}
