package net.skycraftmc.SkyQuest.maker;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class HomePanel extends JPanel implements ActionListener
{
	private JButton load;
	JTextField datapath;
	private JButton browse;
	JLabel loadstatus = new JLabel("Click browse to find the SkyQuest folder");
	private SQMFrame frame;
	public HomePanel(SQMFrame frame)
	{
		this.frame = frame;
		setLayout(new BorderLayout());
		load = new JButton("Load data");
		browse = new JButton("Browse");
		datapath = new JTextField("Click browse or enter path to SkyQuest folder");
		add("North", new JLabel("Welcome to SkyQuest Quest Utility!  Click a tab to start!"));
		Panel home1 = new Panel();
		Panel lp = new Panel();
		home1.setLayout(new BorderLayout());
		lp.add("East", browse);
		lp.add("West", load);
		home1.add("North", datapath);
		home1.add("Center", lp);
		home1.add("South", loadstatus);
		add("East", home1);
		browse.addActionListener(this);
		load.addActionListener(this);
	}
	public void actionPerformed(ActionEvent arg0)
	{
		if(arg0.getSource() == browse)
		{
			JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setApproveButtonText("Choose");
			int v = fc.showOpenDialog(this);
			if(v != JFileChooser.APPROVE_OPTION)return;
			File f = fc.getSelectedFile();
			datapath.setText(f.getAbsolutePath());
		}
		else if(arg0.getSource() == load)frame.loadData();
	}
}
