package net.skycraftmc.SkyQuest.action;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;

import net.skycraftmc.SkyQuest.PlayerQuestLog;
import net.skycraftmc.SkyQuest.Quest;
import net.skycraftmc.SkyQuest.QuestAction;
import net.skycraftmc.SkyQuest.SkyQuestPlugin;
import net.skycraftmc.SkyQuest.utilitygui.ActionEditor;
import net.skycraftmc.SkyQuest.utilitygui.component.QuestExistsListener;

public class AssignQuestAction extends ActionType
{

	public boolean apply(String player, String action)
	{
		if(!isValid(action))
			throw new IllegalArgumentException("action is not valid");
		Quest q = SkyQuestPlugin.getPlugin().getQuestManager().getQuest(action);
		if(q == null)return false;
		PlayerQuestLog log = SkyQuestPlugin.getPlugin().getQuestManager().getQuestLog(player);
		if(log.isAssigned(q))return false;
		log.assign(q);
		return true;
	}

	public boolean isValid(String action) 
	{
		return !action.contains(" ");
	}

	public String getName()
	{
		return "Assign Quest";
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
	@SuppressWarnings("serial")
	private class AssignObjectiveEditorPanel extends ActionEditor
	{
		private JTextField qidtf;
		private JLabel label;
		@Override
		public void init()
		{
			qidtf = new JTextField();
			label = new JLabel();
			setLayout(new BorderLayout());
			add("North", new JLabel("Quest ID"));
			add("Center", qidtf);
			add("South", label);
			label.setForeground(Color.RED);
			qidtf.getDocument().addDocumentListener(new QuestExistsListener(getFinishButton()){
				public boolean update(DocumentEvent e)
				{
					boolean a = super.update(e);
					if(a == false)label.setText("This quest doesn't exist.");
					else label.setText("");
					return a;
				}
			});
		}
		public String createData()
		{
			return qidtf.getText();
		}
		public void loadFrom(QuestAction action)
		{
			qidtf.setText(action.getAction());
		}
	}
	public String getDescription()
	{
		return "Assigns a quest to the player.";
	}
}
