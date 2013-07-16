package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.skycraftmc.SkyQuest.Quest;
import net.skycraftmc.SkyQuest.QuestManager;
import net.skycraftmc.SkyQuest.objective.ObjectiveType;

@SuppressWarnings("serial")
public class CreateObjectiveDialog extends JDialog implements ActionListener, ItemListener, DocumentListener
{
	private SkyQuestUtility util;
	private QuestPanel qp;
	JTextArea desc;
	JComboBox<ObjectiveType>list;
	JButton ok;
	JButton cancel;
	ObjectiveEditDialog oed;
	JTextField id;
	JTextField name;
	public CreateObjectiveDialog(QuestPanel qp)
	{
		super(qp.util, "Select objective type", true);
		this.util = qp.util;
		this.qp = qp;
		list = new JComboBox<ObjectiveType>(QuestManager.getInstance().getRegisteredObjectiveTypes());
		desc = new JTextArea();
		desc.setEditable(false);
		desc.setLineWrap(true);
		desc.setWrapStyleWord(true);
		ok = new JButton("Ok");
		cancel = new JButton("Cancel");
		list.setSelectedIndex(0);
		setLayout(new BorderLayout());
		JPanel lp = new JPanel();
		lp.setLayout(new BorderLayout());
		lp.add("Center", list);
		lp.add("South", desc);
		add("Center", lp);
		JPanel p = new JPanel();
		p.add(ok);
		p.add(cancel);
		add("South", p);
		id = new JTextField();
		name = new JTextField();
		JPanel flds = new JPanel();
		flds.setLayout(new BoxLayout(flds, BoxLayout.Y_AXIS));
		JPanel idp = new JPanel();
		idp.setLayout(new BorderLayout());
		idp.add("Center", id);
		idp.add("West", new JLabel("Objective ID"));
		JPanel namep = new JPanel();
		namep.setLayout(new BorderLayout());
		namep.add("Center", name);
		namep.add("West", new JLabel("Name"));
		flds.add(idp);
		flds.add(namep);
		add("North", flds);
		ok.addActionListener(this);
		cancel.addActionListener(this);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int)d.getWidth()/4, (int)d.getHeight()/4, (int)d.getWidth()/2, (int)d.getHeight()/2);
		list.addItemListener(this);
		if(list.getSelectedIndex() != -1)
			desc.setText(((ObjectiveType)list.getSelectedItem()).getDescription());
		oed = new ObjectiveEditDialog(util, this);
		id.getDocument().addDocumentListener(this);
		name.getDocument().addDocumentListener(this);
	}
	public void itemStateChanged(ItemEvent e)
	{
		if(list.getSelectedIndex() == -1)return;
		desc.setText(((ObjectiveType)list.getSelectedItem()).getDescription());
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() == cancel)setVisible(false);
		else if(e.getSource() == ok)
		{
			setVisible(false);
			oed.loadAndShow(null, (ObjectiveType) list.getSelectedItem());
		}
	}
	public void changedUpdate(DocumentEvent arg0)
	{
		update();
	}
	public void insertUpdate(DocumentEvent arg0)
	{
		update();
	}
	public void removeUpdate(DocumentEvent arg0)
	{
		update();
	}
	public void update()
	{
		if(name.getText().isEmpty())ok.setEnabled(false);
		else if(id.getText().isEmpty())ok.setEnabled(false);
		else if(((Quest)qp.list.getSelectedValue()).getObjective(id.getText()) != null)ok.setEnabled(false);
		else ok.setEnabled(true);
	}
}
