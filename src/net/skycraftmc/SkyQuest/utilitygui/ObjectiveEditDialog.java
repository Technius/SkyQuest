package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JDialog;

import net.skycraftmc.SkyQuest.Objective;
import net.skycraftmc.SkyQuest.objective.ObjectiveType;

@SuppressWarnings("serial")
public class ObjectiveEditDialog extends JDialog
{
	private ObjectiveEditPanel oep;
	private CreateObjectiveDialog cod;
	public ObjectiveEditDialog(SkyQuestUtility util, CreateObjectiveDialog cod)
	{
		super(util, true);
		this.cod = cod;
		oep = new ObjectiveEditPanel(util, this);
		add(oep);
	}
	public void loadAndShow(Objective o, ObjectiveType type)
	{
		oep.load(o, type);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((int)d.getWidth()/4, (int)d.getHeight()/4, (int)d.getWidth()/2, (int)d.getHeight()/2);
		setVisible(true);
		requestFocus();
	}
	public Objective create()
	{
		if(oep.loaded == null)return new Objective(cod.id.getText(), cod.name.getText(), oep.loadedtype, oep.oe.createData());
		return null;
	}
	public void clear()
	{
		oep.clear();
	}
}
