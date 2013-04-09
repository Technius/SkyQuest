package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

@SuppressWarnings("serial")
public class SkyQuestUtility extends JFrame implements WindowListener
{
	private static final SkyQuestUtility util;
	static
	{
		util = new SkyQuestUtility();
	}
	public static void main(String[] args)
	{
		util.init();
	}
	private void init()
	{
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Take up half of the screen, centered
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int)d.getWidth()/4, (int)d.getHeight()/4, (int)d.getWidth()/2, (int)d.getHeight()/2);
		//Set up stuff
		setTitle("SkyQuest Utility");
		setLayout(new BorderLayout());
		//Add components
		JTabbedPane tabs = new JTabbedPane();
		add("Center", tabs);
		setJMenuBar(new MenuBar(this));
		//Listeners
		addWindowListener(this);
		//Show
		setVisible(true);
	}
	public void exit()
	{
		System.exit(0);
	}
	public void windowActivated(WindowEvent arg0) {
	}
	public void windowClosed(WindowEvent arg0) {
	}
	public void windowClosing(WindowEvent arg0)
	{
		exit();
	}
	public void windowDeactivated(WindowEvent arg0) {
	}
	public void windowDeiconified(WindowEvent arg0) {
	}
	public void windowIconified(WindowEvent arg0) {
	}
	public void windowOpened(WindowEvent arg0) {
	}
}
