package net.skycraftmc.SkyQuest.utilitygui;

import javax.swing.JComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import net.skycraftmc.SkyQuest.objective.ObjectiveType;

public class ObjectiveValidListener implements DocumentListener
{
	private ObjectiveType type;
	private JComponent[] c;
	public ObjectiveValidListener(ObjectiveType type, JComponent... components)
	{
		this.type = type;
		c = components;
	}
	public void changedUpdate(DocumentEvent e) {
		update(e);
	}
	public void insertUpdate(DocumentEvent e) {
		update(e);
	}

	public void removeUpdate(DocumentEvent e) {
		update(e);
	}
	private void update(DocumentEvent e)
	{
		Document d = e.getDocument();
		try {
			boolean a = type.isValid(d.getText(0, d.getLength()));
			for(JComponent c:this.c)c.setEnabled(a);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
}
