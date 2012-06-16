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
	private SQMFrame frame;
	private DefaultListModel model;
	public SQMQList(SQMFrame sqm)
	{
		frame = sqm;
		model = new DefaultListModel();
		setModel(model);
		addListSelectionListener(this);
	}
	public DefaultListModel getModel()
	{
		return model;
	}
	public void valueChanged(ListSelectionEvent arg0) 
	{
		int sel = getSelectedIndex();
		if(sel == -1)return;
		String q = (String) getModel().getElementAt(sel);
		HashMap<String, Object> quest = frame.getQuest(q);
		if(quest == null)return;
		String name = (String)quest.get("name");
		String nl = SQMFrame.newline;
		String tab = SQMFrame.tab;
		@SuppressWarnings("unchecked")
		ArrayList<Objective> obj = (ArrayList<Objective>) quest.get("objectives");
		TextArea qinfo = frame.getQuestArea();
		qinfo.setText("Quest data: " + name + nl);
		qinfo.append("Objectives:" + nl);
		for(Objective o:obj)
		{
			qinfo.append(tab + o.getName() + ":" + nl);
			qinfo.append(tab + tab + "Objective: " + o.getTarget() + nl);
			String d = null;
			for(String s: o.getDescription())
			{
				if(d == null)d = s;
				else d = d + nl + tab + tab + tab + s;
			}
			String r = null;
			for(String s: o.getRewards())
			{
				if(r == null)r = s;
				else r = r + nl + tab + tab + tab + s;
			}
			qinfo.append(tab + tab + "Description: " + d + nl);
			if(r != null)qinfo.append(tab + tab + "Rewards: " + r + nl);
		}
	}
}
