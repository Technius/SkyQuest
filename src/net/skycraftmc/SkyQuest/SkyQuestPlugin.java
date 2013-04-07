package net.skycraftmc.SkyQuest;

import org.bukkit.plugin.java.JavaPlugin;

public class SkyQuestPlugin extends JavaPlugin
{
	private QuestManager qm;
	private QuestLogManager qlm;
	private static SkyQuestPlugin inst;
	public void onEnable()
	{
		inst = this;
		SkyQuest.onServer = true;
		qm = new QuestManager();
		qlm = new QuestLogManager(this);
		getServer().getPluginManager().registerEvents(new SkyQuestListener(this), this);
		getServer().getPluginManager().registerEvents(qlm, this);
		
		//Testing
		Stage s1 = new Stage("stage1");
		s1.addAction(new QuestAction(ActionType.ASSIGN_OBJECTIVE, "test obj1"));
		Quest q = new Quest("test", "Purge the unclean", s1);
		q.getDescription().add("They defile our holy grounds!  Purge them!");
		Objective o1 = new Objective("obj1", "Kill 5 zombies", ObjectiveType.KILL, "5 ZOMBIE");
		o1.addReward(new QuestAction(ActionType.ASSIGN_OBJECTIVE, "test obj2"));
		o1.addReward(new QuestAction(ActionType.ASSIGN_OBJECTIVE, "test obje1"));
		q.addObjective(o1);
		Objective oe1 = new Objective("obje1", "Kill 5 more zombies", ObjectiveType.KILL, "5 ZOMBIE");
		oe1.setOptional(true);
		q.addObjective(oe1);
		Objective o2 = new Objective("obj2", "Kill 5 skeletons", ObjectiveType.KILL, "5 SKELETON");
		o2.setItemIconId(352);
		o2.addReward(new QuestAction(ActionType.ASSIGN_OBJECTIVE, "test obj3"));
		q.addObjective(o2);
		Objective o3 = new Objective("obj3", "Kill 3 zombie pigmen", ObjectiveType.KILL, "3 PIG_ZOMBIE");
		o3.addReward(new QuestAction(ActionType.ASSIGN_OBJECTIVE, "test obj4"));
		q.addObjective(o3);
		Objective o4 = new Objective("obj4", "Go somewhere random", ObjectiveType.TRAVEL, "20 circle 264 222 world");
		q.addObjective(o4);
		qm.addQuest(q);
	}
	public QuestManager getQuestManager()
	{
		return qm;
	}
	public QuestLogManager getQuestLogManager()
	{
		return qlm;
	}
	public static SkyQuestPlugin getPlugin()
	{
		return inst;
	}
}
