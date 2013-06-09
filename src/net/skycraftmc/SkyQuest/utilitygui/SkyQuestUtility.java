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
import net.skycraftmc.SkyQuest.util.StringConfig;

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
	StringConfig conf;
	File savedir;
	File conffile;
	File lastfile;
	JTabbedPane tabs;
	boolean changed = false;
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
		savedir = getSaveDir();
		conffile = new File(savedir, "data.txt");
		qm = new QuestManager();
		fm = new FileManager();
		conf = new StringConfig();
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
		tabs = new JTabbedPane();
		main = new MainPanel(this);
		tabs.addTab("Home", main);
		quest = new QuestPanel(this);
		tabs.addTab("Quests", quest);
		tabs.setEnabledAt(1, false);
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
		//Load settings
		conf.setFile(conffile);
		try
		{
			loadSettings();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}

		quest.tabs.setPreferredSize(new Dimension(getWidth()/3, quest.tabs.getSize().height));
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
		if(changed)
		{
			int i = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit without saving?", 
				"SkyQuest Utility - Confirm", JOptionPane.YES_NO_OPTION);
			if(i == JOptionPane.NO_OPTION)return;
		}
		try
		{
			saveSettings();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
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
			QuestManager.getInstance().clearData();
			fm.loadData(f, qm);
			tabs.setEnabledAt(1, true);
			open = f;
			menu.save.setEnabled(true);
			main.status.setText("Folder loaded: " + f.getAbsolutePath());
			main.save.setEnabled(true);
			lastfile = fc.getSelectedFile();
			quest.list.refreshList();
			setTitle("SkyQuest Utility - " + f.getPath());
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
		changed = false;
		setTitle("SkyQuest Utility - " + open.getPath());
	}
	public void saveSettings() throws IOException
	{
		if(!savedir.exists())savedir.mkdir();
		conf.start();
		if(lastfile != null)
			conf.writeKey("lastfile", lastfile.getAbsolutePath());
		conf.close();
	}
	public void loadSettings() throws IOException
	{
		if(conffile.exists())
		{
			conf.load();
			if(conf.hasKey("lastfile"))
			{
				lastfile = new File(conf.getString("lastfile", ""));
				fc.setSelectedFile(lastfile);
			}
		}
	}
	private File getSaveDir()
	{
		String osname = System.getProperty("os.name");
		File ufolder = new File(System.getProperty("user.home"));
		File dfolder;
		String f = File.separator + ".SkyQuest";
		if(osname.equals("Windows 7") || osname.equalsIgnoreCase("Windows Vista"))
			dfolder = new File(ufolder, "AppData" + File.separator + "Roaming" + f);
		else if(osname.contains("Windows"))
			dfolder = new File(ufolder, "Application Data" + f);
		else dfolder = new File(ufolder, f);
		return dfolder;
	}
	public void markFileChanged()
	{
		if(!changed)
		{
			changed = true;
			setTitle(getTitle() + "*");
		}
	}
}
