package net.skycraftmc.SkyQuest.maker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import net.skycraftmc.SkyQuest.util.SkyQuestDataLoader;

@SuppressWarnings("serial")
public class SQMFrame extends JFrame implements WindowListener, ActionListener
{
	public static final String tab = "    ";
	public static final String newline = System.getProperty("line.separator");
	private java.awt.List plist;
	private TextArea pinfo;
	private ArrayList<HashMap<String, Object>> questdata = null;
	private SkyQuestDataLoader loader = null;
	private HomePanel home;
	private QuestPanel quests;
	File file;
	public SQMFrame()
	{
		super("SkyQuest Quest Utility");
	}
	public void init()
	{
		addWindowListener(this);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setSize((int)(d.getWidth() - d.getWidth()/3), (int)(d.getHeight() - d.getHeight()/3));
		setBounds((int)d.getWidth()/2 - getWidth()/2, (int)d.getHeight()/2 - getHeight()/2, getWidth(), getHeight());
		setLayout(new BorderLayout());
		//Following code causes random glitches... -.-
		
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			javax.swing.JOptionPane.showMessageDialog(this,"Unspecified Error.  Click OK to close.", "SkyQuest Quest Utility", javax.swing.JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}
		JTabbedPane tab = new JTabbedPane();
		home = new HomePanel(this);
		quests = new QuestPanel(this);
		Panel pdata = new Panel();
		pdata.setLayout(new BorderLayout());
		//player data
		plist = new java.awt.List();
		Panel pdatalist = new Panel();
		pdatalist.setLayout(new BorderLayout());
		pdatalist.add("North", new JLabel("Players"));
		pdatalist.add("Center", plist);
		pdata.add("West", pdatalist);
		Panel pdatainfo = new Panel();
		pinfo = new TextArea("Please load data in the home tab.");
		pinfo.setEditable(false);
		pdatainfo.add("Center", pinfo);
		pdata.add("Center", pdatainfo);
		//menu
		tab.addTab("Home", home);
		tab.addTab("Player Data", pdata);
		tab.addTab("Quest Manager", quests);
		MenuBar menub = new MenuBar();
		Menu menu = new Menu("File");
		MenuItem exit = new MenuItem("Exit");
		exit.addActionListener(this);
		menu.add(exit);
		menub.add(menu);
		setMenuBar(menub);
		add("Center", tab);
		setVisible(true);
	}
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		System.exit(0);
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() instanceof MenuItem)
		{
			String a = arg0.getActionCommand();
			if(a.equalsIgnoreCase("Exit"))System.exit(0);
		}
	}
	public ArrayList<HashMap<String, Object>> getQuests()
	{
		return questdata;
	}
	public HashMap<String, Object>getQuest(String name)
	{
		for(HashMap<String, Object> q:questdata)
		{
			if(!q.containsKey("name"))continue;
			if(!q.containsKey("objectives"))continue;
			if(!q.containsKey("next"))continue;
			if(((String)q.get("name")).equalsIgnoreCase(name))return q;
		}
		return null;
	}
	public File getFile()
	{
		return file;
	}
	public boolean hasQuest(String quest)
	{
		for(HashMap<String, Object> q:questdata)
		{
			if(!q.containsKey("name"))continue;
			if(((String)q.get("name")).equalsIgnoreCase(quest))return true;
		}
		return false;
	}
	public void loadData()
	{
		file = new File(home.datapath.getText());
		if(!file.exists())home.loadstatus.setText("File does not exist!");
		else if(!file.canRead())home.loadstatus.setText("This file cannot be read!");
		else if(!file.isDirectory())home.loadstatus.setText("This file must be a folder!");
		else if(!file.getName().equalsIgnoreCase("SkyQuest"))home.loadstatus.setText("This folder must be named \"SkyQuest\"");
		else
		{
			home.loadstatus.setText("Loading data...");
			loader = new SkyQuestDataLoader(file);
			questdata = loader.loadQuests();
			quests.getQuestList().refresh();
			home.loadstatus.setText("Data loaded successfully!");
		}
	}
	public QuestPanel getQuestPanel()
	{
		return quests;
	}
}
