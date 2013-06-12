package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.skycraftmc.SkyQuest.Quest;
import net.skycraftmc.SkyQuest.Stage;

@SuppressWarnings("serial")
public class CreateStageDialog extends JDialog implements KeyListener, ActionListener
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
		JPanel b = new JPanel();
		b.add(ok);
		b.add(cancel);
		add("South", b);
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		ok.addActionListener(this);
		cancel.addActionListener(this);
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
		
	}
	public void keyPressed(KeyEvent arg0) {}
	public void keyReleased(KeyEvent arg0) {}
	public void keyTyped(KeyEvent arg0)
	{
		refresh();
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
			setVisible(false);
		}
		else if(e.getSource() == cancel)
		{
			setVisible(false);
		}
	}
}
