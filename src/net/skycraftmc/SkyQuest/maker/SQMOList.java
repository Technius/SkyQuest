package net.skycraftmc.SkyQuest.maker;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

@SuppressWarnings("serial")
public class SQMOList extends JList<String> implements ListSelectionListener
{
	private QuestPanel panel;
	private DefaultListModel<String> model;
	public SQMOList(QuestPanel panel)
	{
		this.panel = panel;
		model = new DefaultListModel<String>();
		setModel(model);
		addListSelectionListener(this);
	}
	public void valueChanged(ListSelectionEvent event)
	{
		
	}
}
