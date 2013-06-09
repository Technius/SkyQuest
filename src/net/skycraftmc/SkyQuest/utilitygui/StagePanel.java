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
	JButton moveup;
	JButton movedown;
	SkyQuestUtility util;
	ActionDialog ad;
	CreateActionDialog cad;
	Stage loaded;
	public StagePanel(SkyQuestUtility util)
	{
		this.util = util;
		ad = new ActionDialog(util, this);
		cad = new CreateActionDialog(util, ad);
		model = new DefaultListModel<QuestAction>();
		list = new JList<QuestAction>(model);
		edit = new JButton("Edit");
		delete = new JButton("Delete");
		create = new JButton("Create");
		moveup = new JButton("Move Up");
		movedown = new JButton("Move Down");
		edit.setEnabled(false);
		delete.setEnabled(false);
		moveup.setEnabled(false);
		movedown.setEnabled(false);
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
		buttons.add(moveup);
		buttons.add(movedown);
		add("South", buttons);
		edit.addActionListener(this);
		delete.addActionListener(this);
		create.addActionListener(this);
		moveup.addActionListener(this);
		movedown.addActionListener(this);
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
		if(arg0.getSource() == create && loaded != null)
		{
			cad.setVisible(true);
			return;
		}
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
					util.markFileChanged();
				}
			}
			else if(arg0.getSource() == moveup && loaded != null)
			{
				if(index != 0)
				{
					loaded.removeAction(index);
					loaded.addAction(list.getSelectedValue(), index - 1);
					loadData(loaded);
					list.setSelectedIndex(index - 1);
					util.markFileChanged();
				}
			}
			else if(arg0.getSource() == movedown && loaded != null)
			{
				if(index != model.size() - 1)
				{
					loaded.removeAction(index);
					int ni;
					if(index == loaded.getActions().length - 1)
					{
						loaded.addAction(list.getSelectedValue());
						ni = loaded.getActions().length - 1;
					}
					else
					{
						ni = index + 1;
						loaded.addAction(list.getSelectedValue(), ni);
					}
					loadData(loaded);
					list.setSelectedIndex(ni);
					util.markFileChanged();
				}
			}
		}
	}
	public void valueChanged(ListSelectionEvent arg0)
	{
		edit.setEnabled(true);
		delete.setEnabled(true);
		moveup.setEnabled(list.getSelectedIndex() != 0);
		movedown.setEnabled(list.getSelectedIndex() != model.size() - 1);
	}
}
