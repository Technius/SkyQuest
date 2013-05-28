package net.skycraftmc.SkyQuest.action;

import java.awt.event.KeyAdapter;

import javax.swing.JTextField;

import net.skycraftmc.SkyQuest.Objective;
import net.skycraftmc.SkyQuest.PlayerQuestLog;
import net.skycraftmc.SkyQuest.Quest;
import net.skycraftmc.SkyQuest.QuestAction;
import net.skycraftmc.SkyQuest.QuestData;
import net.skycraftmc.SkyQuest.SkyQuestPlugin;
import net.skycraftmc.SkyQuest.utilitygui.ActionEditor;

public class AssignObjectiveAction extends ActionType
{

	public boolean apply(String player, String action)
	{
		if(!isValid(action))
			throw new IllegalArgumentException("action is not valid");
		String[] t = action.split(" ", 2);
		Quest q = SkyQuestPlugin.getPlugin().getQuestManager().getQuest(t[0]);
		if(q == null)return false;
		Objective o = q.getObjective(t[1]);
		if(o == null)return false;
		PlayerQuestLog log = SkyQuestPlugin.getPlugin().getQuestManager().getQuestLog(player);
		if(!log.isAssigned(q))return false;
		QuestData qd = log.getProgress(q);
		if(qd.isComplete(o.getID()))return false;
		if(qd.isAssigned(o.getID()))return false;
		qd.assign(o.getID());
		return true;
	}

	public boolean isValid(String action) 
	{
		return action.split(" ").length == 2;
	}

	public String getName()
	{
		return "Assign Objective";
	}

	public boolean requiresPlayer() 
	{
		return true;
	}
	@Override
	public ActionEditor createEditorPanel()
	{
		return new AssignObjectiveEditorPanel();
	}
	private class AssignObjectiveEditorPanel extends ActionEditor
	{
		private JTextField qidtf;
		private JTextField oidtf;
		private AssignObjectiveEditorPanel()
		{
			qidtf = new JTextField();
			oidtf = new JTextField();
		}
		public String createData() 
		{
			return null;
		}
		public void loadFrom(QuestAction action)
		{
			
		}
	}
	private class EmptyTextListener extends KeyAdapter
	{
		
	}
}
