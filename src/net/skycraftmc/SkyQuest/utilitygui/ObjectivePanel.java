package net.skycraftmc.SkyQuest.utilitygui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import net.skycraftmc.SkyQuest.Objective;
import net.skycraftmc.SkyQuest.QuestAction;
import net.skycraftmc.SkyQuest.Stage;

@SuppressWarnings("serial")
public class ObjectivePanel extends JPanel implements ActionListener
{
	private SkyQuestUtility util;
	private JTextField name;
	private Objective sel;
	JButton save;
	JButton cancel;
	private JCheckBox optional;
	StagePanel rewards;
	Stage rwcp;
	ObjectiveEditPanel oep;
	public ObjectivePanel(SkyQuestUtility util)
	{
		this.util = util;
		util.quest.op = this;
		name = new JTextField();
		optional = new JCheckBox("Optional", false);
		save = new JButton("Save changes");
		cancel = new JButton("Cancel");
		rewards = new StagePanel(util);
		oep = new ObjectiveEditPanel(util, this);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel np = new JPanel();
		np.setLayout(new BorderLayout());
		name.setEnabled(false);
		np.add("Center", name);
		np.add("West", new JLabel("Name"));
		add(np);
		add(optional);
		add(new JLabel("Target"));
		add(oep);
		add(new JLabel("Rewards"));
		add(rewards);
		JPanel actionp = new JPanel();
		actionp.setLayout(new BoxLayout(actionp, BoxLayout.X_AXIS));
		actionp.add(save);
		actionp.add(cancel);
		add(Box.createVerticalGlue());
		add(actionp);
		save.addActionListener(this);
		save.setEnabled(false);
		cancel.addActionListener(this);
		cancel.setEnabled(false);
		rewards.create.setEnabled(false);
	}
	public void loadData(Objective o)
	{
		if(o == null)
		{
			clear();
			return;
		}
		sel = o;
		name.setEnabled(true);
		name.setText(o.getName());
		save.setEnabled(true);
		cancel.setEnabled(true);
		optional.setSelected(o.isOptional());
		rwcp = new Stage("");
		for(QuestAction qa: o.getRewardsAsStage().getActions())
			rwcp.addAction(new QuestAction(qa.getType(), qa.getAction()));
		rewards.loadData(rwcp);
		rewards.create.setEnabled(true);
		oep.load(o, o.getType());
	}
	public void clear()
	{
		sel = null;
		name.setEnabled(false);
		name.setText("");
		save.setEnabled(false);
		cancel.setEnabled(false);
		optional.setSelected(false);
		rewards.loadData(new Stage(""));
		rewards.create.setEnabled(false);
		oep.clear();
		SwingUtilities.updateComponentTreeUI(this);
	}
	public void saveData(Objective o)
	{
		o.setName(name.getText());
		o.setOptional(optional.isSelected());
		o.setTarget(oep.oe.createData());
		Stage rw = o.getRewardsAsStage();
		for(int i = rw.size() - 1; i > -1; i --)rw.removeAction(i);
		for(QuestAction qa:rwcp.getActions())o.addReward(qa);
		loadData(o);
		util.markFileChanged();
	}
	public void saveData()
	{
		if(sel != null)saveData(sel);
	}
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource() == save)saveData();
		else if(arg0.getSource() == cancel && sel != null)loadData(sel);
	}
}
