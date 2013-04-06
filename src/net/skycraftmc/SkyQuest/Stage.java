package net.skycraftmc.SkyQuest;

import java.util.ArrayList;

public class Stage 
{
	private String id;
	private ArrayList<QuestAction>actions = new ArrayList<QuestAction>();
	public Stage(String id)
	{
		this.id = id;
	}
	public String getID()
	{
		return id;
	}
	/**
	 * Adds an action to this quest stage.
	 * @param action The {@link QuestAction} to be added.
	 * @see #addAction(QuestAction, int)
	 */
	public void addAction(QuestAction action)
	{
		if(action == null)
			throw new IllegalArgumentException("action is null");
		actions.add(action);
	}
	/**
	 * Inserts an action at the specified index.
	 * @param action The {@link QuestAction} to be added.
	 * @param index The index of the action
	 */
	public void addAction(QuestAction action, int index)
	{
		if(index >= actions.size() || index < 0)
			throw new ArrayIndexOutOfBoundsException("index is out of bounds");
		actions.add(index, action);
	}
	public void removeAction(int index)
	{
		if(index >= actions.size() || index < 0)
			throw new ArrayIndexOutOfBoundsException("index is out of bounds");
		actions.remove(index);
	}
	public QuestAction[] getActions()
	{
		return actions.toArray(new QuestAction[actions.size()]);
	}
}
