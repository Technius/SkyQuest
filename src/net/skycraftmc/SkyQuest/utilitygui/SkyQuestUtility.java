package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import net.skycraftmc.SkyQuest.FileManager;
import net.skycraftmc.SkyQuest.QuestManager;

@SuppressWarnings("serial")
public class SkyQuestUtility extends JFrame implements WindowListener
{
	private static final SkyQuestUtility util;
	QuestManager qm;
	FileManager fm;
	MainPanel main;
	MenuBar menu;
	File open;
	QuestPanel quest;
	JFileChooser fc;
	static
	{
		util = new SkyQuestUtility();
	}
	public static void main(String[] args)
	{
		util.init();
	}
	public SkyQuestUtility()
	{
		super("SkyQuest Utility");
	}
	private void init()
	{
		qm = new QuestManager();
		fm = new FileManager();
		//Set LookAndFeel to Nimbus
		try {
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Load icon image
		try
		{
			Image icon = ImageIO.read(getClass().getClassLoader().getResource("res/icon.png"));
			if(icon != null)setIconImage(icon);
		}
		catch(IOException ioe)
		{
			System.out.println("Failed to load icon");
			ioe.printStackTrace();
		}
		//Take up half of the screen, centered
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int)d.getWidth()/6, (int)d.getHeight()/6, (int)d.getWidth()*2/3, (int)d.getHeight()*2/3);
		//Set up stuff
		setTitle("SkyQuest Utility");
		setLayout(new BorderLayout());
		//Add components
		JTabbedPane tabs = new JTabbedPane();
		main = new MainPanel(this);
		tabs.addTab("Home", main);
		quest = new QuestPanel(this);
		tabs.addTab("Quests", quest);
		add("Center", tabs);
		menu = new MenuBar(this);
		setJMenuBar(menu);
		//Listeners
		addWindowListener(this);
		//Workaround for getting System JFileChooser in Nimbus
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.updateUI();
			UIManager.setLookAndFeel(NimbusLookAndFeel.class.getName());
			refreshUI(fc, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Show
		setVisible(true);
	}
	private void refreshUI(JComponent c, boolean ip)
	{
		if(ip)c.updateUI();
		for(int i = 0; i < c.getComponentCount(); i ++)
		{
			Component c2 = c.getComponent(i);
			if(c2 instanceof JComponent)refreshUI((JComponent)c2, true);
		}
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
	public void openFolderDialog()
	{
		int sel = fc.showOpenDialog(this);
		if(sel == JFileChooser.CANCEL_OPTION)return;
		else if(sel == JFileChooser.APPROVE_OPTION)
		{
			File f = fc.getSelectedFile();
			if(!new File(f, "Quests").exists() || !new File(f, "Players").exists())
			{
				JOptionPane.showMessageDialog(this, "This folder is not a valid SkyQuest plugin folder",
					"SkyQuest Utility", JOptionPane.WARNING_MESSAGE);
				return;
			}
			fm.loadData(f, qm);
			open = f;
			menu.save.setEnabled(true);
			main.status.setText("Folder loaded: " + f.getAbsolutePath());
			main.save.setEnabled(true);
			quest.list.refreshList();
		}
	}
	public void saveFolderDialog()
	{
		if(!open.exists())
		{
			int sel = fc.showSaveDialog(this);
			if(sel == JFileChooser.CANCEL_OPTION)return;
			else if(sel == JFileChooser.APPROVE_OPTION)
			{
				open = fc.getSelectedFile();
			}
		}
		open.delete();
		open.mkdir();
		try 
		{
			fm.saveData(open, qm);
			JOptionPane.showMessageDialog(this, "Data saved to " + open.getAbsolutePath() + "!",
				"SkyQuest Utility", JOptionPane.INFORMATION_MESSAGE);
		}
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(this, "An error has occurred: " + e.getMessage(), 
				"SkyQuest Utility", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
