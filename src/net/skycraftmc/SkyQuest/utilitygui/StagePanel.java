package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.skycraftmc.SkyQuest.QuestAction;
import net.skycraftmc.SkyQuest.Stage;

@SuppressWarnings("serial")
public class StagePanel extends JPanel implements ActionListener, ListSelectionListener
{
	DefaultListModel<QuestAction>model;
	JList<QuestAction>list;
	JButton edit;
	SkyQuestUtility util;
	ActionDialog ad;
	public StagePanel(SkyQuestUtility util)
	{
		this.util = util;
		ad = new ActionDialog(util, this);
		model = new DefaultListModel<QuestAction>();
		list = new JList<QuestAction>(model);
		edit = new JButton("Edit");
		edit.setEnabled(false);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(list);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add("Center", scroll);
		add("South", edit);
		edit.addActionListener(this);
		list.addListSelectionListener(this);
	}
	public void loadData(Stage s)
	{
		model.clear();
		for(QuestAction a:s.getActions())model.addElement(a);
	}
	public void actionPerformed(ActionEvent arg0) 
	{
		int index = list.getSelectedIndex();
		if(index != -1)
		{
			ad.loadAndShow(list.getSelectedValue(), list.getSelectedValue().getType());
		}
	}
	public void valueChanged(ListSelectionEvent arg0)
	{
		edit.setEnabled(true);
	}
}
