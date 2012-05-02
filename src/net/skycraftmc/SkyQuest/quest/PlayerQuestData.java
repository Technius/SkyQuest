package net.skycraftmc.SkyQuest.quest;

import java.util.ArrayList;

import org.bukkit.entity.Player;

public class PlayerQuestData 
{
	private Player player;
	private ArrayList<Quest>quests = new ArrayList<Quest>();
	public PlayerQuestData(Player player)
	{
		this.player = player;
	}
	public Quest[] getQuests()
	{
		return quests.toArray(new Quest[quests.size()]);
	}
	public Player getPlayer()
	{
		return player;
	}
	public void grantQuest(Quest quest)
	{
		addQuest(quest);
		player.sendMessage("Started: " + quest.getName());
		Objective[] obj = quest.getObjectives();
		if(obj.length > 0);//open up the book GUI when 1.3 is released
	}
	public void addQuest(Quest quest)
	{
		for(Quest q:quests)
		{
			if(q.getName().equalsIgnoreCase(quest.getName()))return;
		}
		quests.add(quest);
	}
	public boolean hasQuest(Quest quest)
	{
		return hasQuest(quest.getName());
	}
	public boolean hasQuest(String quest)
	{
		for(Quest q:quests)
		{
			if(q.getName().equalsIgnoreCase(quest))return true;
		}
		return false;
	}
}
