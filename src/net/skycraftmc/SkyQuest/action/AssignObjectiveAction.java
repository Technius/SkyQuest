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
			EmptyTextListener etl = new EmptyTextListener(getFinishButton(), getCancelButton());
			qidtf.getDocument().addDocumentListener(etl);
			oidtf.getDocument().addDocumentListener(etl);
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
	private class EmptyTextListener implements DocumentListener
	{
		private JComponent[] c;
		public EmptyTextListener(JComponent... components)
		{
			c = components;
		}
		public void changedUpdate(DocumentEvent e) {
			update(e);
		}
		public void insertUpdate(DocumentEvent e) {
			update(e);
		}

		public void removeUpdate(DocumentEvent e) {
			update(e);
		}
		private void update(DocumentEvent e)
		{
			boolean a = e.getDocument().getLength() > 0;
			for(JComponent c:this.c)c.setEnabled(a);
		}
	}

	public String getDescription()
	{
		return "Assigns an objective to the player.";
	}
}
