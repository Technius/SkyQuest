package net.skycraftmc.SkyQuest.utilitygui;

import javax.swing.JPanel;
import javax.swing.JTextField;

import net.skycraftmc.SkyQuest.Objective;

@SuppressWarnings("serial")
public class ObjectivePanel extends JPanel
{
	private JTextField name;
	public ObjectivePanel()
	{
		
	}
	public void loadData(Objective o)
	{
		name.setText(o.getName());
		
	}
}
