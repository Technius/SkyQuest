package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainPanel extends JPanel implements ActionListener
{
	private SkyQuestUtility util;
	JLabel status;
	JButton save;
	private JButton load;
	public MainPanel(SkyQuestUtility util)
	{
		this.util = util;
		status = new JLabel("Load the SkyQuest plugin folder to begin!");
		save = new JButton("Save");
		save.setEnabled(false);
		load = new JButton("Load");
		setLayout(new BorderLayout());
		JPanel sp = new JPanel();
		sp.setLayout(new BoxLayout(sp, BoxLayout.X_AXIS));
		sp.add(Box.createHorizontalGlue());
		sp.add(status);
		sp.add(Box.createHorizontalGlue());
		add("Center", sp);
		JPanel buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));
		buttons.add(load);
		buttons.add(save);
		add("East", buttons);
		load.addActionListener(this);
		save.addActionListener(this);
	}
	public void actionPerformed(ActionEvent arg0)
	{
		if(arg0.getSource() == load)util.openFolderDialog();
		else if(arg0.getSource() == save)util.saveFolderDialog();
	}
}
