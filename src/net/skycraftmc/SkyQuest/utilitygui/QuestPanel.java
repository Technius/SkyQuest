package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.skycraftmc.SkyQuest.Quest;
import net.skycraftmc.SkyQuest.Stage;

@SuppressWarnings("serial")
public class QuestPanel extends JPanel implements ActionListener
{
	SkyQuestUtility util;
	QuestList list;
	ObjectiveList olist;
	StageList slist;
	StagePanel sp;
	ObjectivePanel op;
	JTabbedPane tabs;
	JButton obdelete;
	JButton obcreate;
	JButton prop;
	JButton sbcreate;
	JButton sbdelete;
	JButton create;
	JButton delete;
	QuestPropertyDialog qpd;
	CreateStageDialog csd;
	CreateQuestDialog cqd;
	CreateObjectiveDialog cod;
	public QuestPanel(SkyQuestUtility util)
	{
		this.util = util;
		util.quest = this;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		JPanel qp = new JPanel();
		list = new QuestList(this);
		this.sp = new StagePanel(util);
		op = new ObjectivePanel(util);
		olist = new ObjectiveList();
		slist = new StageList(this);
		this.qpd = new QuestPropertyDialog(this);
		csd = new CreateStageDialog(this);
		cqd = new CreateQuestDialog(this);
		cod = new CreateObjectiveDialog(this);
		qp.setLayout(new BorderLayout());
		qp.add("North", new JLabel("Quests"));
		JScrollPane qs = new JScrollPane(list);
		qs.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		qs.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		qp.add("Center", qs);
		JPanel qpb = new JPanel();
		prop = new JButton("Properties");
		create = new JButton("Create");
		delete = new JButton("Delete");
		qpb.add(create);
		qpb.add(delete);
		qpb.add(prop);
		qp.add("South", qpb);
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
		JPanel sb = new JPanel();
		sbcreate = new JButton("Create");
		sbdelete = new JButton("Delete");
		sb.add(sbcreate);
		sb.add(sbdelete);
		sp.add("South", sb);
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
		JPanel opp = new JPanel();
		opp.setLayout(new BorderLayout());
		opp.add("Center", this.op);
		JPanel ob = new JPanel();
		obdelete = new JButton("Delete");
		obcreate = new JButton("Create");
		ob.add(obcreate);
		ob.add(obdelete);
		op.add("South", ob);
		JScrollPane ops = new JScrollPane(opp);
		ops.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		ops.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		panels.add(ops, "Objectives");
		panels.add(this.sp, "Stages");
		add(panels);
		olist.addListSelectionListener(list);
		obdelete.addActionListener(this);
		obcreate.addActionListener(this);
		prop.addActionListener(this);
		sbdelete.addActionListener(this);
		sbcreate.addActionListener(this);
		create.addActionListener(this);
		delete.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == obdelete)
		{
			int index = olist.getSelectedIndex();
			if(index != -1)
			{
				list.getSelectedValue().removeObjective(olist.getSelectedValue().getID());
				olist.refreshList(list.getSelectedValue());
				int s = olist.model.size();
				if(s > index)olist.setSelectedIndex(index);
				else if(s > 0)olist.setSelectedIndex(s - 1);
				else 
				{
					obdelete.setEnabled(false);
					op.clear();
				}
				if(obdelete.isEnabled())op.loadData(olist.getSelectedValue());
				util.markFileChanged();
			}
		}
		else if(e.getSource() == obcreate && list.getSelectedIndex() != -1)
		{
			cod.setVisible(true);
			cod.requestFocus();
		}
		else if(e.getSource() == prop)
		{
			if(list.getSelectedIndex() != -1)
			{
				qpd.loadAndShow(list.getSelectedValue());
			}
		}
		else if(e.getSource() == sbdelete)
		{
			if(slist.getSelectedIndex() != -1)
			{
				Quest q = list.getSelectedValue();
				Stage s = slist.getSelectedValue();
				if(s != q.getFirstStage())
				{
					q.removeStage(s.getID());
					slist.refreshList(q);
					util.markFileChanged();
				}
			}
		}
		else if(e.getSource() == sbcreate)
		{
			csd.display();
		}
		else if(e.getSource() == create)
		{
			cqd.display();
		}
		else if(e.getSource() == delete)
		{
			Quest q = list.getSelectedValue();
			int index = list.getSelectedIndex();
			if(q != null)
			{
				util.qm.removeQuest(q);
				list.refreshList();
				util.markFileChanged();
				int s = list.model.size();
				if(s > index)list.setSelectedIndex(index);
				else if(s > 0)list.setSelectedIndex(s - 1);
				else 
				{
					delete.setEnabled(false);
					op.clear();
					olist.model.clear();
					slist.model.clear();
					sp.model.clear();
				}
				if(delete.isEnabled())op.loadData(olist.getSelectedValue());
			}
		}
	}
}
