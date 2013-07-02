package net.skycraftmc.SkyQuest.action;

import net.skycraftmc.SkyQuest.CompletionState;
import net.skycraftmc.SkyQuest.Objective;
import net.skycraftmc.SkyQuest.PlayerQuestLog;
import net.skycraftmc.SkyQuest.Quest;
import net.skycraftmc.SkyQuest.QuestData;
import net.skycraftmc.SkyQuest.SkyQuestPlugin;

public class MarkQuestCompletionAction extends ActionType
{
	public boolean apply(String player, String action)
	{
		if(!isValid(action))
			throw new IllegalArgumentException("action is not valid");
		String[] t = action.split(" ", 3);
		Quest q = SkyQuestPlugin.getPlugin().getQuestManager().getQuest(t[0]);
		if(q == null)return false;
		PlayerQuestLog log = SkyQuestPlugin.getPlugin().getQuestManager().getQuestLog(player);
		if(!log.isAssigned(q))return false;
		QuestData qd = log.getProgress(q);
		CompletionState s = CompletionState.getByName(t[1]);
		if(s != CompletionState.IN_PROGRESS)qd.markComplete(s);
		else return false;
		return true;
	}

	public boolean isValid(String action)
	{
		String[] t = action.split(" ", 2);
		if(t.length != 2)return false;
		CompletionState s = CompletionState.getByName(t[1]);
		return s != null && s != CompletionState.IN_PROGRESS;
	}

	public String getName() 
	{
		return "Mark Quest Completion";
	}

	public boolean requiresPlayer()
	{
		return false;
	}
	
	public String getDescription()
	{
		return "Marks a quest as complete or failed.";
	}

}
