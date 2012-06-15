package net.skycraftmc.SkyQuest.maker;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class SQMFrame extends Frame implements WindowListener, ActionListener
{
	private java.awt.List plist;
	private TextArea pinfo;
	private Button load;
	private TextField datapath;
	private Button browse;
	private JLabel loadstatus = new JLabel("Click browse to find the SkyQuest folder");
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
		Panel home = new Panel();
		Panel pdata = new Panel();
		home.setLayout(new BorderLayout());
		pdata.setLayout(new BorderLayout());
		//home
		load = new Button("Load data");
		browse = new Button("Browse");
		datapath = new TextField("Click browse or enter path to SkyQuest folder");
		home.add("North", new JLabel("Welcome to SkyQuest Quest Utility!  Click a tab to start!"));
		Panel home1 = new Panel();
		home1.setLayout(new BorderLayout());
		home1.add("North", datapath);
		home1.add("Center", browse);
		home1.add("South", load);
		home.add("East", home1);
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
		MenuBar menub = new MenuBar();
		Menu menu = new Menu("File");
		MenuItem exit = new MenuItem("Exit");
		exit.addActionListener(this);
		browse.addActionListener(this);
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
		else if(arg0.getSource() == load)
		{
			File f = new File(datapath.getText());
			if(!f.exists())loadstatus.setText("File does not exist!");
			else if(!f.canRead())loadstatus.setText("This file cannot be read!");
			else if(!f.isDirectory())loadstatus.setText("This file must be a folder!");
			else if(!f.getName().equalsIgnoreCase("SkyQUest"))loadstatus.setText("This folder must be named \"SkyQuest\"");
		}
		else if(arg0.getSource() == browse)
		{
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int v = fc.showOpenDialog(this);
			if(v != JFileChooser.APPROVE_OPTION)return;
			File f = fc.getSelectedFile();
			datapath.setText(f.getAbsolutePath());
		}
	}
}
