package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class QuestPanel extends JPanel
{
	SkyQuestUtility util;
	QuestList list;
	ObjectiveList olist;
	//TODO Stage list
	//TODO Objective/stage tabs
	//TODO Stage panel
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
		JScrollPane qs = new JScrollPane(list);
		qs.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		qs.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		qp.add("Center", qs);
		JTabbedPane tabs = new JTabbedPane();
		JPanel op = new JPanel();
		op.setLayout(new BorderLayout());
		op.add("North", new JLabel("Objectives"));
		JScrollPane os = new JScrollPane(olist);
		os.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		os.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		op.add("Center", os);
		JPanel op2 = new JPanel();
		op2.setLayout(new BoxLayout(op2, BoxLayout.X_AXIS));
		op2.add(op);
		tabs.add(op2, "Objectives");
		JPanel sp = new JPanel();
		tabs.add(sp, "Stages");
		add(qp);
		add(tabs);
		final JPanel panels = new JPanel();
		final CardLayout cards = new CardLayout();
		panels.setLayout(cards);
		tabs.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				cards.next(panels);
			}
		});
		panels.add(this.op, "Objectives");
		add(panels);
		olist.addListSelectionListener(list);
	}
}
