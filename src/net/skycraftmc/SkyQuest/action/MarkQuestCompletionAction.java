package net.skycraftmc.SkyQuest.action;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.skycraftmc.SkyQuest.CompletionState;
import net.skycraftmc.SkyQuest.PlayerQuestLog;
import net.skycraftmc.SkyQuest.Quest;
import net.skycraftmc.SkyQuest.QuestAction;
import net.skycraftmc.SkyQuest.QuestData;
import net.skycraftmc.SkyQuest.SkyQuestPlugin;
import net.skycraftmc.SkyQuest.utilitygui.ActionEditor;
import net.skycraftmc.SkyQuest.utilitygui.component.QuestExistsListener;

public class MarkQuestCompletionAction extends ActionType
{
	public boolean apply(String player, String action)
	{
		if(!isValid(action))
			throw new IllegalArgumentException("action is not valid");
		String[] t = action.split(" ", 3);
		Quest q = SkyQuestPlugin.getPlugin().getQuestManager().getQuest(t[0]);
		if(q == null)return false;
		PlayerQuestLog log = SkyQuestPlugin.getPlugin().getQuestManager().getQuestLog(player);
		if(!log.isAssigned(q))return false;
		QuestData qd = log.getProgress(q);
		CompletionState s = CompletionState.getByName(t[1]);
		if(s != CompletionState.IN_PROGRESS)qd.markComplete(s);
		else return false;
		return true;
	}

	public boolean isValid(String action)
	{
		String[] t = action.split(" ", 2);
		if(t.length != 2)return false;
		CompletionState s = CompletionState.getByName(t[1]);
		return s != null && s != CompletionState.IN_PROGRESS;
	}

	public String getName() 
	{
		return "Mark Quest Completion";
	}

	public boolean requiresPlayer()
	{
		return false;
	}
	
	public String getDescription()
	{
		return "Marks a quest as complete or failed.";
	}
	
	@Override
	public ActionEditor createEditorPanel()
	{
		return new MarkQuestCompletionEditorPanel();
	}
	@SuppressWarnings("serial")
	private class MarkQuestCompletionEditorPanel extends ActionEditor
	{
		private JTextField tf;
		private JComboBox<CompletionState> cslist;
		@Override
		public void init()
		{
			tf = new JTextField();
			CompletionState[] allstates = CompletionState.values();
			CompletionState[] states = new CompletionState[allstates.length - 1];
			for(int i = 1; i < allstates.length; i ++)states[i - 1] = allstates[i];
			cslist = new JComboBox<CompletionState>(states);
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel p = new JPanel();
			p.setLayout(new BorderLayout());
			p.add("Center", tf);
			p.add("West", new JLabel("Quest ID"));
			JPanel p2 = new JPanel();
			p2.setLayout(new BorderLayout());
			p2.add("Center", cslist);
			p2.add("West", new JLabel("Completion State"));
			add(p);
			add(p2);
			tf.getDocument().addDocumentListener(new QuestExistsListener(getFinishButton()));
		}
		public String createData() 
		{
			return tf.getText() + " " + ((CompletionState)cslist.getSelectedItem()).name();
		}
		public void loadFrom(QuestAction action)
		{
			String[] t = action.getAction().split(" ", 2);
			tf.setText(t[0]);
			CompletionState cs = CompletionState.getByName(t[1]);
			if(cs != null)cslist.setSelectedItem(cs);
		}
	}
}
