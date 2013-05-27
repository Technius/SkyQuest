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
	JButton delete;
	JButton create;
	SkyQuestUtility util;
	ActionDialog ad;
	Stage loaded;
	public StagePanel(SkyQuestUtility util)
	{
		this.util = util;
		ad = new ActionDialog(util, this);
		model = new DefaultListModel<QuestAction>();
		list = new JList<QuestAction>(model);
		edit = new JButton("Edit");
		delete = new JButton("Delete");
		create = new JButton("Create");
		edit.setEnabled(false);
		delete.setEnabled(false);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		setLayout(new BorderLayout());
		JScrollPane scroll = new JScrollPane(list);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add("Center", scroll);
		JPanel buttons = new JPanel();
		buttons.add(edit);
		buttons.add(create);
		buttons.add(delete);
		add("South", buttons);
		edit.addActionListener(this);
		delete.addActionListener(this);
		list.addListSelectionListener(this);
	}
	public void loadData(Stage s)
	{
		loaded = s;
		model.clear();
		for(QuestAction a:s.getActions())model.addElement(a);
	}
	public void actionPerformed(ActionEvent arg0) 
	{
		int index = list.getSelectedIndex();
		if(index != -1)
		{
			if(arg0.getSource() == edit)
			{
				ad.loadAndShow(list.getSelectedValue(), list.getSelectedValue().getType());
			}
			else if(arg0.getSource() == delete)
			{
				if(loaded != null)
				{
					loaded.removeAction(index);
					loadData(loaded);
				}
			}
		}
	}
	public void valueChanged(ListSelectionEvent arg0)
	{
		edit.setEnabled(true);
		delete.setEnabled(true);
	}
}
