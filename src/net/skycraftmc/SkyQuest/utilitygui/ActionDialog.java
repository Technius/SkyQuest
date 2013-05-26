package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import net.skycraftmc.SkyQuest.QuestAction;
import net.skycraftmc.SkyQuest.QuestManager;
import net.skycraftmc.SkyQuest.action.ActionType;

@SuppressWarnings("serial")
public class ActionDialog extends JDialog implements ActionListener
{
	JButton save;
	JButton cancel;
	JList<ActionType>atypes;
	DefaultListModel<ActionType>amodel;
	SkyQuestUtility util;
	QuestAction loaded;
	ActionEditor ae;
	JComponent parent;
	public ActionDialog(SkyQuestUtility util, JComponent parent)
	{
		super(util, true);
		this.util = util;
		this.parent = parent;
		save = new JButton("Save");
		cancel = new JButton("Cancel");
		amodel = new DefaultListModel<ActionType>();
		atypes = new JList<ActionType>(amodel);
		for(ActionType a:QuestManager.getInstance().getRegisteredActionTypes())
			amodel.addElement(a);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setLayout(new BorderLayout());
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));
		buttons.add(save);
		buttons.add(cancel);
		JPanel ap = new JPanel();
		ap.setLayout(new BorderLayout());
		ap.add("Center", atypes);
		ap.add("North", new JLabel("Type"));
		add("South", buttons);
		cancel.addActionListener(this);
		save.addActionListener(this);
	}
	public void loadAndShow(QuestAction qa, ActionType type)
	{
		loaded = qa;
		ae = type.createEditorPanel();
		ae.save = save;
		save.setEnabled(false);
		ae.cancel = cancel;
		ae.init();
		if(qa == null)
		{
			save.setText("Create");
			atypes.setEnabled(true);
			setTitle("Create New Action");
		}
		else
		{
			save.setText("Save");
			ae.loadFrom(qa);
			atypes.setEnabled(false);
			setTitle("Edit Action - " + qa.getType().getName());
		}
		add("Center", ae);
		setBounds(util.getWidth()/4, util.getHeight()/4, util.getWidth()/2, util.getHeight()/2);
		setVisible(true);
	}
	public QuestAction create()
	{
		if(loaded == null)return new QuestAction(ae.type, ae.createData());
		return null;
	}
	public void edit()
	{
		if(loaded == null)return;
		loaded.setAction(ae.createData());
	}
	public void actionPerformed(ActionEvent arg0)
	{
		if(arg0.getSource() == cancel)
		{
			setVisible(false);
		}
		else if(arg0.getSource() == save)
		{
			if(parent == util.quest.sp)
			{
				if(loaded == null)util.quest.sp.model.addElement(create());
				else edit();
				util.quest.sp.loadData(util.quest.slist.getSelectedValue());
				setVisible(false);
				clear();
			}
		}
	}
	public void clear()
	{
		remove(ae);
		ae = null;
		loaded = null;
	}
}
