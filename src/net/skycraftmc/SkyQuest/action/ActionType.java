package net.skycraftmc.SkyQuest.action;

import net.skycraftmc.SkyQuest.utilitygui.ActionEditor;
import net.skycraftmc.SkyQuest.utilitygui.BasicActionEditor;


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
	/**
	 * Returns whether or not the provided action data is valid for this ActionType.
	 * @param action The data provided for this ActionType.
	 * @return Whether or not the data is valid.
	 */
	public abstract boolean isValid(String action);
	/**
	 * @return The name of this ActionType
	 */
	public abstract String getName();
	/**
	 * @return If this action requires a player
	 */
	public abstract boolean requiresPlayer();
	/**
	 * Creates a new {@link ActionEditor} that is used for
	 * editing and creating actions of this type.  By default,
	 * this method returns an {@link ActionEditor} of type
	 * {@link BasicActionEditor}
	 * @return A new {@link ActionEditor}
	 */
	public ActionEditor createEditorPanel()
	{
		return new BasicActionEditor(this);
	}
	public String toString()
	{
		return getName();
	}
	/**
	 * @return Text describing this ActionType
	 */
	public String getDescription()
	{
		return "No description.";
	}
}
