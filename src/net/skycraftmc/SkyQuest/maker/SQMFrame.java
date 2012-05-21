package net.skycraftmc.SkyQuest.maker;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class SQMFrame extends Frame implements WindowListener, ActionListener
{
	private java.awt.List plist;
	private TextArea pinfo;
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
		/**
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(this,"Unspecified Error.  Click OK to close.", "SkyQuest Quest Utility", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return;
		}**/
		JTabbedPane tab = new JTabbedPane();
		Panel home = new Panel();
		Panel pdata = new Panel();
		home.setLayout(new BorderLayout());
		pdata.setLayout(new BorderLayout());
		//home
		home.add("North", new JLabel("Welcome to SkyQuest Quest Utility!  Click a tab to start!"));
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
}
