package net.skycraftmc.SkyQuest.quest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.entity.Player;

import net.skycraftmc.SkyQuest.SkyQuestMain;

public class QuestManager 
{
	private SkyQuestMain p;
	public static HashMap<Player, List<Quest>> quests = new HashMap<Player, List<Quest>>();
	public static List<Quest> allquests = new ArrayList<Quest>();
	public QuestManager(SkyQuestMain main)
	{
		p = main;
	}
	public void addQuest(Player player, Quest quest)
	{
		quests.get(player).add(Quest.clone(quest));
	}
	public List<Quest> getQuests(Player player)
	{
		return quests.get(player);
	}
	public Quest getQuest(String quest)
	{
		return null;
	}
}
