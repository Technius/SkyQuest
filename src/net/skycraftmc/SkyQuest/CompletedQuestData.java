package net.skycraftmc.SkyQuest;

import java.util.ArrayList;
import java.util.HashMap;

public class CompletedQuestData extends QuestProgress
{
	public CompletedQuestData(Quest q, ArrayList<String> unassigned, HashMap<String, String>progress, CompletionState state)
	{
		super(q);
		this.unassigned = unassigned;
		this.state = state;
		objprog = progress;
	}
}
