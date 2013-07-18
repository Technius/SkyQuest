package net.skycraftmc.SkyQuest.utilitygui.component;

import javax.swing.JComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import net.skycraftmc.SkyQuest.QuestManager;

public class QuestExistsListener implements DocumentListener
{
	private JComponent[] c;
	public QuestExistsListener(JComponent... components)
	{
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
		boolean a;
		try
		{
			a = QuestManager.getInstance().getQuest(e.getDocument().getText(0, e.getDocument().getLength())) != null;
			for(JComponent c:this.c)c.setEnabled(a);
		} catch (BadLocationException e1)
		{
			e1.printStackTrace();
		}
	}
}
