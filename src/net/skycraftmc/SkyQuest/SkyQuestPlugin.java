package net.skycraftmc.SkyQuest;

public class SkyQuestPlugin 
{
	private QuestManager qm;
	public void onEnable()
	{
		qm = new QuestManager();
	}
	public QuestManager getQuestManager()
	{
		return qm;
	}
}
