package net.skycraftmc.SkyQuest.quest;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import net.skycraftmc.SkyQuest.SkyQuestMain;

public class QuestManager 
{
	private SkyQuestMain plugin;
	private ArrayList<Quest>quests = new ArrayList<Quest>();
	private ArrayList<PlayerQuestData>pdata = new ArrayList<PlayerQuestData>();
	public QuestManager(SkyQuestMain plugin)
	{
		this.plugin = plugin;
	}
	public void addData(Player player)
	{
		for(PlayerQuestData a:pdata)
		{
			if(a.getPlayer() == player)return;
		}
		pdata.add(new PlayerQuestData(player));
	}
	public PlayerQuestData getData(Player player)
	{
		for(PlayerQuestData a:pdata)
		{
			if(a.getPlayer() == player)return a;
		}
		return null;
	}
	public void removeData(Player player)
	{
		for(PlayerQuestData a:pdata)
		{
			if(a.getPlayer() == player)pdata.remove(a);
		}
	}
	public boolean containsData(Player player)
	{
		for(PlayerQuestData a:pdata)
		{
			if(a.getPlayer() == player)return true;
		}
		return false;
	}
	public void addQuest(Quest quest)
	{
		if(!quests.contains(quest))quests.add(quest);
	}
	public void removeQuest(Quest quest)
	{
		quests.remove(quest);
	}
	public void clearQuests()
	{
		quests.clear();
	}
	public Quest getQuest(String name)
	{
		for(Quest q:quests)
		{
			if(q.getName().equalsIgnoreCase(name))return q;
		}
		return null;
	}
}
