package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;

import net.skycraftmc.SkyQuest.QuestAction;
import net.skycraftmc.SkyQuest.action.ActionType;

/**
 * An {@link ActionEditor} that provides very basic
 * functionality.  It can only edit the {@link QuestAction}
 * data directly.
 * @see ActionEditor
 */
@SuppressWarnings("serial")
public class BasicActionEditor extends ActionEditor
{
	private ActionType type;
	private JTextField f;
	private JLabel l;
	public BasicActionEditor(ActionType type)
	{
		this.type = type;
	}
	
	public void init()
	{
		setLayout(new BorderLayout());
		JTextArea label = new JTextArea("The data of this action type can only be modified manually" +
			" as the action type does not have a custom editor.");
		label.setEnabled(false);
		label.setLineWrap(true);
		label.setWrapStyleWord(true);
		add("North", label);
		JLabel data = new JLabel("Data");
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add("West", data);
		f = new JTextField();
		p.add("Center", f);
		add("Center", p);
		l = new JLabel();
		l.setForeground(Color.RED);
		add("South", l);
		f.getDocument().addDocumentListener(new ActionValidListener(type, getFinishButton()){
			protected boolean update(DocumentEvent e)
			{
				boolean a = super.update(e);
				if(a)l.setText("");
				else l.setText("This data is invalid.");
				return a;
			}
		});
	}

	public void loadFrom(QuestAction action)
	{
		f.setText(action.getAction());
	}

	public String createData()
	{
		return f.getText();
	}
}
