package net.skycraftmc.SkyQuest.action;

import java.awt.BorderLayout;

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
	@SuppressWarnings("serial")
	private class AssignObjectiveEditorPanel extends ActionEditor
	{
		private JTextField qidtf;
		private JTextField oidtf;
		@Override
		public void init()
		{
			qidtf = new JTextField();
			oidtf = new JTextField();
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel p1 = new JPanel();
			JPanel p2 = new JPanel();
			p1.setLayout(new BorderLayout());
			p2.setLayout(new BorderLayout());
			p1.add("North", new JLabel("Quest ID"));
			p1.add("Center", qidtf);
			p2.add("North", new JLabel("Objective ID"));
			p2.add("Center", oidtf);
			add(p1);
			add(p2);
			FieldListener l = new FieldListener(qidtf, oidtf, getFinishButton());
			qidtf.getDocument().addDocumentListener(l);
			oidtf.getDocument().addDocumentListener(l);
		}
		public String createData()
		{
			return qidtf.getText() + " " + oidtf.getText();
		}
		public void loadFrom(QuestAction action)
		{
			String[] t = action.getAction().split(" ", 2);
			qidtf.setText(t[0]);
			oidtf.setText(t[1]);
		}
	}
	
	private class FieldListener implements DocumentListener
	{
		private JTextField qidtf;
		private JTextField oidtf;
		private JComponent p;
		private FieldListener(JTextField f, JTextField f2, JComponent p)
		{
			qidtf = f;
			oidtf = f2;
			this.p = p;
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
				p.setEnabled(false);
				return;
			}
			String s = oidtf.getText();
			p.setEnabled(q.getObjective(s) != null);
		}
	}

	public String getDescription()
	{
		return "Assigns an objective to the player.";
	}
}
