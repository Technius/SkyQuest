package net.skycraftmc.SkyQuest;

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
}
