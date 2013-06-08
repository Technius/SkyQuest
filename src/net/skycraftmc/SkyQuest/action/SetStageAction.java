package net.skycraftmc.SkyQuest.action;

import net.skycraftmc.SkyQuest.Objective;
import net.skycraftmc.SkyQuest.PlayerQuestLog;
import net.skycraftmc.SkyQuest.Quest;
import net.skycraftmc.SkyQuest.QuestData;
import net.skycraftmc.SkyQuest.SkyQuestPlugin;

public class SetStageAction extends ActionType
{

	public boolean apply(String player, String action) 
	{
		if(!isValid(action))
			throw new IllegalArgumentException("action is not valid");
		String[] t = action.split(" ", 2);
		Quest q = SkyQuestPlugin.getPlugin().getQuestManager().getQuest(t[0]);
		if(q == null)return false;
		Objective o = q.getObjective(t[1]);
		if(o == null)return false;
		PlayerQuestLog log = SkyQuestPlugin.getPlugin().getQuestManager().getQuestLog(player);
		if(!log.isAssigned(q))return false;
		QuestData qd = log.getProgress(q);
		qd.setStage(q.getID());
		return true;
	}

	public boolean isValid(String action) 
	{
		return action.split(" ", 2).length == 2;
	}
	
	public String getName() 
	{
		return "Set Stage";
	}

	public boolean requiresPlayer() 
	{
		return true;
	}
	
	public String getDescription()
	{
		return "Sets the stage of a quest.";
	}
}
