package net.skycraftmc.SkyQuest.action;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.skycraftmc.SkyQuest.Objective;
import net.skycraftmc.SkyQuest.PlayerQuestLog;
import net.skycraftmc.SkyQuest.Quest;
import net.skycraftmc.SkyQuest.QuestAction;
import net.skycraftmc.SkyQuest.QuestData;
import net.skycraftmc.SkyQuest.QuestManager;
import net.skycraftmc.SkyQuest.SkyQuestPlugin;
import net.skycraftmc.SkyQuest.utilitygui.ActionEditor;

public class SetStageAction extends ActionType
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
		qd.setStage(q.getID());
		return true;
	}

	public boolean isValid(String action) 
	{
		return action.split(" ", 2).length == 2;
	}
	
	public String getName() 
	{
		return "Set Stage";
	}

	public boolean requiresPlayer() 
	{
		return true;
	}
	
	public String getDescription()
	{
		return "Sets the stage of a quest.";
	}
	@Override
	public ActionEditor createEditorPanel()
	{
		return new SetStageEditorPanel();
	}
	@SuppressWarnings("serial")
	private class SetStageEditorPanel extends ActionEditor
	{
		private JTextField qidtf;
		private JTextField sidtf;
		private JLabel label;
		@Override
		public void init()
		{
			qidtf = new JTextField();
			sidtf = new JTextField();
			label = new JLabel();
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel p1 = new JPanel();
			JPanel p2 = new JPanel();
			p1.setLayout(new BorderLayout());
			p2.setLayout(new BorderLayout());
			p1.add("North", new JLabel("Quest ID"));
			p1.add("Center", qidtf);
			p2.add("North", new JLabel("Stage ID"));
			p2.add("Center", sidtf);
			add(p1);
			add(p2);
			add(label);
			label.setForeground(Color.RED);
			FieldListener l = new FieldListener(qidtf, sidtf, getFinishButton(), label);
			qidtf.getDocument().addDocumentListener(l);
			sidtf.getDocument().addDocumentListener(l);
		}
		public String createData() 
		{
			return qidtf.getText() + " " + sidtf.getText();
		}
		public void loadFrom(QuestAction action)
		{
			String[] t = action.getAction().split(" ", 2);
			qidtf.setText(t[0]);
			sidtf.setText(t[1]);
		}
	}
	private class FieldListener implements DocumentListener
	{
		private JTextField qidtf;
		private JTextField sidtf;
		private JComponent p;
		private JLabel l;
		private FieldListener(JTextField f, JTextField f2, JComponent p, JLabel l)
		{
			qidtf = f;
			sidtf = f2;
			this.p = p;
			this.l = l;
		}
		public void changedUpdate(DocumentEvent arg0)
		{
			update(arg0);
		}
		public void insertUpdate(DocumentEvent arg0)
		{
			update(arg0);
		}
		public void removeUpdate(DocumentEvent arg0)
		{
			update(arg0);
		}
		public void update(DocumentEvent e)
		{
			Quest q = QuestManager.getInstance().getQuest(qidtf.getText());
			if(q == null)
			{
				l.setText("This quest doesn't exist.");
				p.setEnabled(false);
				return;
			}
			String s = sidtf.getText();
			boolean a = q.getStage(s) != null;
			p.setEnabled(a);
			if(a)l.setText("");
			else l.setText("This stage doesn't exist.");
		}
	}
}
