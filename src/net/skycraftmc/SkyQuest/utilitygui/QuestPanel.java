package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

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
	StageList slist;
	StagePanel sp;
	ObjectivePanel op;
	JTabbedPane tabs;
	public QuestPanel(SkyQuestUtility util)
	{
		this.util = util;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JPanel qp = new JPanel();
		list = new QuestList(this);
		this.sp = new StagePanel(util);
		op = new ObjectivePanel();
		olist = new ObjectiveList();
		slist = new StageList();
		qp.setLayout(new BorderLayout());
		qp.add("North", new JLabel("Quests"));
		JScrollPane qs = new JScrollPane(list);
		qs.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		qs.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		qp.add("Center", qs);
		tabs = new JTabbedPane();
		JPanel op = new JPanel();
		op.setLayout(new BorderLayout());
		JScrollPane os = new JScrollPane(olist);
		os.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		os.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		op.add("Center", os);
		tabs.add(op, "Objectives");
		JPanel sp = new JPanel();
		sp.setLayout(new BorderLayout());
		JScrollPane ss = new JScrollPane(slist);
		ss.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		ss.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.add("Center", ss);
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
		panels.add(this.sp, "Stages");
		add(panels);
		olist.addListSelectionListener(list);
		slist.addListSelectionListener(list);
	}
}
