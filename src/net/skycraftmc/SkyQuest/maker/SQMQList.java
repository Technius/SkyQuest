package net.skycraftmc.SkyQuest.maker;

import java.awt.TextArea;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.skycraftmc.SkyQuest.quest.Objective;

@SuppressWarnings("serial")
public class SQMQList extends JList implements ListSelectionListener
{
	private QuestPanel panel;
	private DefaultListModel model;
	public SQMQList(QuestPanel panel)
	{
		this.panel = panel;
		model = new DefaultListModel();
		setModel(model);
		addListSelectionListener(this);
	}
	public DefaultListModel getModel()
	{
		return model;
	}
	public void refresh()
	{
		model.clear();
		removeAll();
		for(HashMap<String, Object> o: panel.getFrame().getQuests())
		{
			String name = (String) o.get("name");
			if(name != null)model.addElement(name);
		}
	}
	public void valueChanged(ListSelectionEvent arg0) 
	{
		int sel = getSelectedIndex();
		if(sel == -1)return;
		String q = (String) getModel().getElementAt(sel);
		HashMap<String, Object> quest = panel.getFrame().getQuest(q);
		if(quest == null)return;
		@SuppressWarnings("unchecked")
		ArrayList<Objective> obj = (ArrayList<Objective>) quest.get("objectives");
	}
}
