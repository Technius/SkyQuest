package net.skycraftmc.SkyQuest.utilitygui;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.skycraftmc.SkyQuest.Objective;
import net.skycraftmc.SkyQuest.Quest;
import net.skycraftmc.SkyQuest.QuestManager;

@SuppressWarnings("serial")
public class QuestList extends JList<Quest> implements ListSelectionListener
{
	private SkyQuestUtility util;
	private QuestManager qm;
	private QuestPanel qp;
	DefaultListModel<Quest>model;
	public QuestList(QuestPanel qp)
	{
		util = qp.util;
		qm = util.qm;
		this.qp = qp;
		model = new DefaultListModel<Quest>();
		setModel(model);
		addListSelectionListener(this);
	}
	public void refreshList()
	{
		model.clear();
		for(Quest q:qm.getQuests())
		{
			model.addElement(q);
		}
	}
	public void valueChanged(ListSelectionEvent arg0)
	{
		if(arg0.getValueIsAdjusting())return;
		int index = arg0.getFirstIndex();
		if(index < 0)return;
		if(arg0.getSource() == this)
		{
			Quest q = getSelectedValue();
			if(q != null)
			{
				update(q);
				if(qp.olist.getSelectedIndex() == -1)qp.op.clear();
			}
		}
		else if(arg0.getSource() == qp.olist)
		{
			Objective o = qp.olist.getSelectedValue();
			if(o != null)qp.op.loadData(o);
		}
	}
	public void update(Quest q)
	{
		qp.sp.model.clear();
		qp.olist.refreshList(q);
		qp.slist.refreshList(q);
	}
}
