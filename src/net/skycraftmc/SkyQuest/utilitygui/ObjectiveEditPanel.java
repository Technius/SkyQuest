package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.skycraftmc.SkyQuest.Objective;
import net.skycraftmc.SkyQuest.objective.ObjectiveType;

@SuppressWarnings("serial")
public class ObjectiveEditPanel extends JPanel implements ActionListener
{
	Objective loaded;
	private JButton save;
	private JButton cancel;
	private Object parent;
	private SkyQuestUtility util;
	ObjectiveType loadedtype;
	private JPanel epanel;
	ObjectiveEditor oe;
	public ObjectiveEditPanel(SkyQuestUtility util, Object parent)
	{
		this.util = util;
		this.parent = parent;
		setLayout(new BorderLayout());
		if(parent == util.quest.op)
		{
			save = util.quest.op.save;
			cancel = util.quest.op.cancel;
		}
		else
		{
			JPanel btns = new JPanel();
			save = new JButton("Save");
			cancel = new JButton("Cancel");
			btns.add(save);
			btns.add(cancel);
			save.addActionListener(this);
			cancel.addActionListener(this);
			add(btns, "South");
		}
		epanel = new JPanel();
		epanel.setLayout(new BorderLayout());
		add("Center", epanel);
	}
	public void load(Objective o, ObjectiveType type)
	{
		loaded = o;
		if(epanel.getComponentCount() > 0)epanel.remove(0);
		oe = type.createEditorPanel();
		oe.save = save;
		save.setEnabled(false);
		oe.cancel = cancel;
		oe.init();
		if(o == null)
		{
			save.setText("Create");
			loadedtype = type;
		}
		else
		{
			save.setText("Save");
			oe.loadFrom(o);
			loadedtype = o.getType();
		}
		epanel.add("Center", oe);
		invalidate();
		SwingUtilities.updateComponentTreeUI(this);
	}
	
	public void actionPerformed(ActionEvent arg0)
	{
		if(parent != util.quest.cod.oed)return;
		if(arg0.getSource() == cancel)
		{
			util.quest.cod.oed.setVisible(false);
		}
		else if(arg0.getSource() == save)
		{
			Objective o = util.quest.cod.oed.create();
			util.quest.list.getSelectedValue().addObjective(o);
			util.markFileChanged();
			util.quest.list.update(util.quest.list.getSelectedValue());
			util.quest.cod.oed.setVisible(false);
			util.quest.olist.setSelectedValue(o, true);
			clear();
		}
	}
	
	public void clear()
	{
		loaded = null;
		loadedtype = null;
		if(oe != null)
		{
			remove(oe);
			oe = null;
		}
	}
}
