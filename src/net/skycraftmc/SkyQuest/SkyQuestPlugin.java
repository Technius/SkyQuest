package net.skycraftmc.SkyQuest;

public class SkyQuestPlugin 
{
	private QuestManager qm;
	public void onEnable()
	{
		SkyQuest.onServer = true;
		qm = new QuestManager();
		qm.registerObjectiveType(ObjectiveType.KILL);
		
		//Testing
		Quest q = new Quest("test", "TestQuest");
		Stage s = new Stage("stage1");
		q.addStage(s);
		q.addObjective(new Objective("obj1", "TestObjective", ObjectiveType.KILL, "1 ZOMBIE"));
		qm.addQuest(q);
	}
	public QuestManager getQuestManager()
	{
		return qm;
	}
}
