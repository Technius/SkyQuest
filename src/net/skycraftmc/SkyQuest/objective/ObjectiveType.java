package net.skycraftmc.SkyQuest.objective;

import net.skycraftmc.SkyQuest.utilitygui.BasicObjectiveEditor;
import net.skycraftmc.SkyQuest.utilitygui.ObjectiveEditor;


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
	/**
	 * Creates a new {@link ObjectiveEditor} that is used for
	 * editing and creating actions of this type.  By default,
	 * this method returns an {@link ObjectEditor} of type
	 * {@link BasicObjectiveEditor}
	 * @return A new {@link ObjectiveEditor}
	 */
	public ObjectiveEditor createEditorPanel()
	{
		return new BasicObjectiveEditor(this);
	}
	public String toString()
	{
		return getName();
	}
	/**
	 * @return Text describing this ObjectiveType
	 */
	public String getDescription()
	{
		return "No description.";
	}
}
