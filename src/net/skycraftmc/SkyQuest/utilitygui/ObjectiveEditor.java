package net.skycraftmc.SkyQuest.utilitygui;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.skycraftmc.SkyQuest.Objective;
import net.skycraftmc.SkyQuest.objective.ObjectiveType;

/**
 * A panel used for editing {@link Qbjective}s in
 * SkyQuest's utility gui tool.
 */
@SuppressWarnings("serial")
public abstract class ObjectiveEditor extends JPanel
{
	ObjectiveType type;
	JButton save;
	JButton cancel;
	protected final JButton getFinishButton()
	{
		return save;
	}
	protected final JButton getCancelButton()
	{
		return cancel;
	}
	public abstract String createData();
	public abstract void loadFrom(Objective o);
	public void init()
	{
		
	}
}