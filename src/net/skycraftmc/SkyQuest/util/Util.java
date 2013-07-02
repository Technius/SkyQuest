package net.skycraftmc.SkyQuest.util;

import net.skycraftmc.SkyQuest.Objective;
import net.skycraftmc.SkyQuest.Quest;
import net.skycraftmc.SkyQuest.QuestAction;
import net.skycraftmc.SkyQuest.Stage;
import net.skycraftmc.SkyQuest.action.ActionType;
import net.skycraftmc.SkyQuest.objective.ObjectiveType;

public class Util
{
	public static Quest createExampleQuest()
	{
		Stage s1 = new Stage("stage1");
		s1.addAction(new QuestAction(ActionType.ASSIGN_OBJECTIVE, "test obj1"));
		Quest q = new Quest("test", "Purge the unclean", s1);
		q.getDescription().add("They defile our holy grounds!  Purge them!");
		Objective o1 = new Objective("obj1", "Kill 5 zombies", ObjectiveType.KILL, "5 ZOMBIE");
		o1.addReward(new QuestAction(ActionType.ASSIGN_OBJECTIVE, "test obj2"));
		o1.addReward(new QuestAction(ActionType.ASSIGN_OBJECTIVE, "test obje1"));
		o1.addReward(new QuestAction(ActionType.MESSAGE, "Good job!  Now clear out the filthy skeletons."));
		q.addObjective(o1);
		Objective oe1 = new Objective("obje1", "Kill 5 more zombies", ObjectiveType.KILL, "5 ZOMBIE");
		oe1.setOptional(true);
		oe1.addReward(new QuestAction(ActionType.MESSAGE, "Thanks for getting rid of those zombies!"));
		q.addObjective(oe1);
		Objective o2 = new Objective("obj2", "Kill 5 skeletons", ObjectiveType.KILL, "5 SKELETON");
		o2.setItemIconId(352);
		o2.addReward(new QuestAction(ActionType.ASSIGN_OBJECTIVE, "test obj3"));
		o2.addReward(new QuestAction(ActionType.MESSAGE, "Obliterate the pigmen... they are the vilest."));
		q.addObjective(o2);
		Objective o3 = new Objective("obj3", "Kill 3 zombie pigmen", ObjectiveType.KILL, "3 PIG_ZOMBIE");
		o3.addReward(new QuestAction(ActionType.ASSIGN_OBJECTIVE, "test obj4"));
		o3.addReward(new QuestAction(ActionType.MESSAGE, "Now, get out of here before someone finds out."));
		q.addObjective(o3);
		Objective o4 = new Objective("obj4", "Go somewhere random", ObjectiveType.TRAVEL, "20 circle 264 222 world");
		o4.addReward(new QuestAction(ActionType.MESSAGE, "Great work!  Our holy grounds are pure again!"));
		o4.addReward(new QuestAction(ActionType.MARK_QUEST_COMPLETION, "test COMPLETE"));
		q.addObjective(o4);
		return q;
	}
}
