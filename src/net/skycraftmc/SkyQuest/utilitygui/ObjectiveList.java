package net.skycraftmc.SkyQuest.utilitygui;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import net.skycraftmc.SkyQuest.Objective;
import net.skycraftmc.SkyQuest.Quest;

@SuppressWarnings("serial")
public class ObjectiveList extends JList<Objective>
{
	DefaultListModel<Objective> model;
	public ObjectiveList()
	{
		model = new DefaultListModel<Objective>();
		setModel(model);
	}
	public void refreshList(Quest q)
	{
		model.clear();
		for(Objective o:q.getObjectives())
		{
			model.addElement(o);
		}
	}
}
