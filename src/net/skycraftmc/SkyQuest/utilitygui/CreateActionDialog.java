package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import net.skycraftmc.SkyQuest.QuestManager;
import net.skycraftmc.SkyQuest.action.ActionType;

@SuppressWarnings("serial")
public class CreateActionDialog extends JDialog implements ActionListener, ItemListener
{
	JComboBox<ActionType>list;
	JButton ok;
	JButton cancel;
	ActionDialog ad;
	JTextArea desc;
	public CreateActionDialog(SkyQuestUtility util, ActionDialog ad)
	{
		super(util, "Select action type", true);
		this.ad = ad;
		list = new JComboBox<ActionType>(QuestManager.getInstance().getRegisteredActionTypes());
		desc = new JTextArea();
		desc.setEditable(false);
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);
		ok = new JButton("Ok");
		cancel = new JButton("Cancel");
		list.setSelectedIndex(0);
		setLayout(new BorderLayout());
		JPanel lp = new JPanel();
		lp.setLayout(new BorderLayout());
		lp.add("Center", list);
		lp.add("South", desc);
		add("Center", lp);
		JPanel p = new JPanel();
		p.add(ok);
		p.add(cancel);
		add("South", p);
		ok.addActionListener(this);
		cancel.addActionListener(this);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int)d.getWidth()/4, (int)d.getHeight()/4, (int)d.getWidth()/2, (int)d.getHeight()/2);
		list.addItemListener(this);
		if(list.getSelectedIndex() != -1)
			desc.setText(((ActionType)list.getSelectedItem()).getDescription());
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
	
	public void itemStateChanged(ItemEvent e)
	{
		if(list.getSelectedIndex() == -1)return;
		desc.setText(((ActionType)list.getSelectedItem()).getDescription());
	}
}
