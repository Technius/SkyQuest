package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class MenuBar extends JMenuBar implements ActionListener
{
	private SkyQuestUtility util;
	private JMenuItem exit;
	JMenuItem load;
	JMenuItem save;
	public MenuBar(SkyQuestUtility util)
	{
		this.util = util;
		//File menu
		JMenu file = new JMenu("File");
		exit = new JMenuItem("Exit");
		load = new JMenuItem("Load");
		save = new JMenuItem("Save");
		save.setEnabled(false);
		file.add(load);
		file.add(save);
		file.addSeparator();
		file.add(exit);
		add(file);
		//Listeners
		exit.addActionListener(this);
		load.addActionListener(this);
		save.addActionListener(this);
	}
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource() == exit)util.exit();
		else if(arg0.getSource() == load)util.openFolderDialog();
		else if(arg0.getSource() == save)util.saveFolderDialog();
	}
}
