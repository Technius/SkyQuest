package net.skycraftmc.SkyQuest;

import org.bukkit.plugin.java.JavaPlugin;

public class SkyQuestPlugin extends JavaPlugin
{
	private QuestManager qm;
	private static SkyQuestPlugin inst;
	public void onEnable()
	{
		inst = this;
		SkyQuest.onServer = true;
		qm = new QuestManager();
		getServer().getPluginManager().registerEvents(new SkyQuestListener(this), this);
		
		//Testing
		Stage s1 = new Stage("stage1");
		s1.addAction(new QuestAction(ActionType.ASSIGN_OBJECTIVE, "test obj1"));
		Quest q = new Quest("test", "TestQuest", s1);
		q.addObjective(new Objective("obj1", "TestObjective", ObjectiveType.KILL, "1 ZOMBIE"));
		qm.addQuest(q);
	}
	public QuestManager getQuestManager()
	{
		return qm;
	}
	public static SkyQuestPlugin getPlugin()
	{
		return inst;
	}
}
