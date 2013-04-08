package net.skycraftmc.SkyQuest;

import net.skycraftmc.SkyQuest.action.ActionType;

public class QuestAction
{
	private ActionType type;
	private String action;
	public QuestAction(ActionType type, String action)
	{
		if(!type.isValid(action))
			throw new IllegalArgumentException("action is not valid for type " + type.getName());
		this.type = type;
		this.action = action;
	}
	public ActionType getType()
	{
		return type;
	}
	public String getAction()
	{
		return action;
	}
	public void apply(String player)
	{
		if(player == null && type.requiresPlayer())
			throw new UnsupportedOperationException(type.getName() + " requires a player");
		type.apply(player, action);
	}
	public void apply()
	{
		if(type.requiresPlayer())
			throw new UnsupportedOperationException(type.getName() + " requires a player");
		type.apply(action);
	}
}
