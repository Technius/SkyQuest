package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.skycraftmc.SkyQuest.Objective;
import net.skycraftmc.SkyQuest.QuestAction;
import net.skycraftmc.SkyQuest.objective.ObjectiveType;

/**
 * An {@link ActionEditor} that provides very basic
 * functionality.  It can only edit the {@link QuestAction}
 * data directly.
 * @see ActionEditor
 */
@SuppressWarnings("serial")
public class BasicObjectiveEditor extends ObjectiveEditor
{
	private ObjectiveType type;
	private JTextField f;
	public BasicObjectiveEditor(ObjectiveType type)
	{
		this.type = type;
	}
	
	public void init()
	{
		setLayout(new BorderLayout());
		JTextArea label = new JTextArea("The data of this objective type can only be modified manually" +
			" as the objective type does not have a custom editor.");
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
		f.getDocument().addDocumentListener(new ObjectiveValidListener(type, getFinishButton()));
	}

	public void loadFrom(Objective objective)
	{
		f.setText(objective.getTarget());
	}

	public String createData()
	{
		return f.getText();
	}
}
