package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.skycraftmc.SkyQuest.Quest;
import net.skycraftmc.SkyQuest.Stage;

@SuppressWarnings("serial")
public class CreateStageDialog extends JDialog implements DocumentListener, ActionListener
{
	QuestPanel qp;
	SkyQuestUtility util;
	JTextField name;
	JButton ok;
	JButton cancel;
	public CreateStageDialog(QuestPanel qp)
	{
		super(qp.util, "SkyQuest Utility - Create Stage", true);
		this.qp = qp;
		this.util = qp.util;
		setLayout(new BorderLayout());
		name = new JTextField();
		ok = new JButton("Create");
		cancel = new JButton("Cancel");
		add("Center", name);
		add("West", new JLabel("Name"));
		JPanel b = new JPanel();
		b.add(ok);
		b.add(cancel);
		add("South", b);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		ok.addActionListener(this);
		cancel.addActionListener(this);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(d.width/2 - 150, d.height/2 - 60, 300, 120);
		name.getDocument().addDocumentListener(this);
	}
	public void display()
	{
		setVisible(true);
		refresh();
	}
	private void refresh()
	{
		String s = name.getText();
		if(s.isEmpty())ok.setEnabled(false);
		else if(qp.list.getSelectedValue().getStage(s) != null)ok.setEnabled(false);
		else ok.setEnabled(true);
	}
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == ok)
		{
			Stage s = new Stage(name.getText());
			Quest q = qp.list.getSelectedValue();
			q.addStage(s);
			qp.slist.refreshList(q);
			util.markFileChanged();
			qp.slist.setSelectedValue(s, true);
			setVisible(false);
		}
		else if(e.getSource() == cancel)
		{
			setVisible(false);
		}
	}
	public void changedUpdate(DocumentEvent arg0) 
	{
		refresh();
	}
	public void insertUpdate(DocumentEvent arg0)
	{
		refresh();
	}
	public void removeUpdate(DocumentEvent arg0) 
	{
		refresh();
	}
}
