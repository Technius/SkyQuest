package net.skycraftmc.SkyQuest.utilitygui;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.skycraftmc.SkyQuest.Quest;
import net.skycraftmc.SkyQuest.Stage;

@SuppressWarnings("serial")
public class StageList extends JList<Stage> implements ListSelectionListener
{
	DefaultListModel<Stage> model;
	QuestPanel qp;
	public StageList(QuestPanel qp)
	{
		this.qp = qp;
		model = new DefaultListModel<Stage>();
		setModel(model);
		addListSelectionListener(this);
	}
	public void refreshList(Quest q)
	{
		model.clear();
		for(Stage s:q.getStages())
		{
			model.addElement(s);
		}
	}
	public void valueChanged(ListSelectionEvent e) 
	{
		Stage s = getSelectedValue();
		if(s != null)
		{
			qp.sp.loadData(s);
			if(getSelectedValue() == qp.list.getSelectedValue().getFirstStage())qp.sbdelete.setEnabled(false);
			else qp.sbdelete.setEnabled(true);
		}
	}
}

