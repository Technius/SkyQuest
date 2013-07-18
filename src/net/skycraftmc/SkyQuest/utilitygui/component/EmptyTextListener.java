package net.skycraftmc.SkyQuest.utilitygui.component;

import javax.swing.JComponent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class EmptyTextListener implements DocumentListener
{
	private JComponent[] c;
	public EmptyTextListener(JComponent... components)
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
		try
		{
			boolean a = e.getDocument().getText(0, e.getDocument().getLength()).trim().length() > 0;
			for(JComponent c:this.c)c.setEnabled(a);
		} 
		catch (BadLocationException e1)
		{
			e1.printStackTrace();
		}
	}
}
