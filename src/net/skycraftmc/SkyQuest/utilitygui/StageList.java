package net.skycraftmc.SkyQuest.utilitygui;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import net.skycraftmc.SkyQuest.Quest;
import net.skycraftmc.SkyQuest.Stage;

@SuppressWarnings("serial")
public class StageList extends JList<Stage>
{
	DefaultListModel<Stage> model;
	public StageList()
	{
		model = new DefaultListModel<Stage>();
		setModel(model);
	}
	public void refreshList(Quest q)
	{
		model.clear();
		for(Stage s:q.getStages())
		{
			model.addElement(s);
		}
	}
}

