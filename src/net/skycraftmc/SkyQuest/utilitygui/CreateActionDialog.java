package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;

import net.skycraftmc.SkyQuest.QuestManager;
import net.skycraftmc.SkyQuest.action.ActionType;

@SuppressWarnings("serial")
public class CreateActionDialog extends JDialog implements ActionListener
{
	JComboBox<ActionType>list;
	JButton ok;
	JButton cancel;
	ActionDialog ad;
	public CreateActionDialog(SkyQuestUtility util, ActionDialog ad)
	{
		super(util, "Select action type", true);
		this.ad = ad;
		list = new JComboBox<ActionType>(QuestManager.getInstance().getRegisteredActionTypes());
		ok = new JButton("Ok");
		cancel = new JButton("Cancel");
		list.setSelectedIndex(0);
		setLayout(new BorderLayout());
		add("Center", list);
		JPanel p = new JPanel();
		p.add(ok);
		p.add(cancel);
		add("South", p);
		ok.addActionListener(this);
		cancel.addActionListener(this);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int)d.getWidth()/4, (int)d.getHeight()/4, (int)d.getWidth()/2, (int)d.getHeight()/2);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == cancel)setVisible(false);
		else if(e.getSource() == ok)
		{
			setVisible(false);
			ad.loadAndShow(null, (ActionType)list.getSelectedItem());
		}
	}
}
