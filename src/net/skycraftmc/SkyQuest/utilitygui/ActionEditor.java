package net.skycraftmc.SkyQuest.utilitygui;

import javax.swing.JButton;
import javax.swing.JPanel;

import net.skycraftmc.SkyQuest.QuestAction;
import net.skycraftmc.SkyQuest.action.ActionType;

/**
 * A panel used for editing {@link QuestAction}s in
 * SkyQuest's utility gui tool.
 */
@SuppressWarnings("serial")
public abstract class ActionEditor extends JPanel
{
	ActionType type;
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
	public abstract void loadFrom(QuestAction action);
	public void init()
	{
		
	}
}
