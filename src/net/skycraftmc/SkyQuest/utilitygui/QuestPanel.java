package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class QuestPanel extends JPanel
{
	SkyQuestUtility util;
	QuestList list;
	ObjectiveList olist;
	ObjectivePanel op;
	public QuestPanel(SkyQuestUtility util)
	{
		this.util = util;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JPanel qp = new JPanel();
		list = new QuestList(this);
		op = new ObjectivePanel();
		olist = new ObjectiveList(util);
		qp.setLayout(new BorderLayout());
		qp.add("North", new JLabel("Quests"));
		qp.add("Center", list);
		JPanel op = new JPanel();
		op.setLayout(new BorderLayout());
		op.add("North", new JLabel("Objectives"));
		op.add("Center", olist);
		add(qp);
		add(op);
	}
}
