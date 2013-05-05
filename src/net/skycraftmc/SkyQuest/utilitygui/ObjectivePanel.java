package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import net.skycraftmc.SkyQuest.Objective;

@SuppressWarnings("serial")
public class ObjectivePanel extends JPanel implements ActionListener
{
	private JTextField name;
	private Objective sel;
	private JButton save;
	private JButton cancel;
	public ObjectivePanel()
	{
		name = new JTextField();
		save = new JButton("Save changes");
		cancel = new JButton("Cancel");
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel np = new JPanel();
		np.setLayout(new BorderLayout());
		name.setEnabled(false);
		np.add("Center", name);
		np.add("West", new JLabel("Name"));
		add(np);
		JPanel actionp = new JPanel();
		actionp.setLayout(new BoxLayout(actionp, BoxLayout.X_AXIS));
		actionp.add(save);
		actionp.add(cancel);
		add(Box.createVerticalGlue());
		add(actionp);
		save.addActionListener(this);
		save.setEnabled(false);
		cancel.addActionListener(this);
		cancel.setEnabled(false);
		setBorder(new LineBorder(Color.black));
	}
	public void loadData(Objective o)
	{
		sel = o;
		name.setEnabled(true);
		name.setText(o.getName());
		save.setEnabled(true);
		cancel.setEnabled(true);
	}
	public void saveData(Objective o)
	{
		o.setName(name.getText());
	}
	public void saveData()
	{
		if(sel != null)saveData(sel);
	}
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource() == save)saveData();
		else if(arg0.getSource() == cancel && sel != null)loadData(sel);
	}
}
