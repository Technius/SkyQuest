package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.skycraftmc.SkyQuest.Quest;

@SuppressWarnings("serial")
public class QuestPropertyDialog extends JDialog implements ActionListener
{
	SkyQuestUtility util;
	JCheckBox first;
	JCheckBox hidden;
	JButton save;
	JButton cancel;
	JTextField questname;
	JTextArea desc;
	public QuestPropertyDialog(QuestPanel qp)
	{
		super(qp.util, "SkyQuest - Quest Property", true);
		setLayout(new BorderLayout());
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		util = qp.util;
		first = new JCheckBox("Assign on First Join");
		hidden = new JCheckBox("Hidden");
		save = new JButton("Save");
		cancel = new JButton("Cancel");
		desc = new JTextArea();
		questname = new JTextField();
		questname.addKeyListener(new KeyListener(){
			public void keyTyped(KeyEvent e) {
				update();
			}
			public void keyPressed(KeyEvent e) {
				update();
			}
			public void keyReleased(KeyEvent e) {
				update();
			}
			private void update()
			{
				save.setEnabled(!questname.getText().isEmpty());
			}
			
		});
		JPanel qn = new JPanel();
		qn.setLayout(new BorderLayout());
		qn.add("West", new JLabel("Quest Name"));
		qn.add("Center", questname);
		p.add(qn);
		JPanel dp = new JPanel();
		dp.setLayout(new BorderLayout());
		dp.add("West", new JLabel("Description"));
		dp.add("Center", desc);
		p.add(dp);
		p.add(first);
		p.add(hidden);
		JPanel buttons = new JPanel();
		buttons.add(save);
		buttons.add(cancel);
		add("Center", p);
		add("South", buttons);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int)d.getWidth()/4, (int)d.getHeight()/4, (int)d.getWidth()/2, (int)d.getHeight()/2);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		save.addActionListener(this);
		cancel.addActionListener(this);
	}
	private Quest loaded = null;
	public void loadAndShow(Quest q)
	{
		loaded = q;
		first.setSelected(q.isFirstAssigned());
		hidden.setSelected(!q.isVisible());
		questname.setText(q.getName());
		StringBuffer sb = new StringBuffer();
		for(String s:q.getDescription())
		{
			if(sb.length() > 0)sb.append(System.getProperty("line.separator"));
			sb.append(s);
		}
		desc.setText(sb.toString());
		setVisible(true);
	}
	public void saveEdits()
	{
		if(loaded == null)return;
		loaded.setFirstAssigned(first.isSelected());
		loaded.setVisible(!hidden.isSelected());
		loaded.setName(questname.getText());
		ArrayList<String>desc = new ArrayList<String>();
		for(String s:this.desc.getText().split(System.getProperty("line.separator")))desc.add(s);
		loaded.setDescription(desc);
		util.markFileChanged();
	}
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == cancel)
		{
			setVisible(false);
		}
		else if(e.getSource() == save)
		{
			saveEdits();
			setVisible(false);
		}
	}
}
