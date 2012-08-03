package net.skycraftmc.SkyQuest.maker;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class QuestPanel extends JPanel
{
	private SQMQList qlist;
	private SQMFrame frame;
	private SQMOList olist;
	public QuestPanel(SQMFrame frame)
	{
		this.frame = frame;
		setLayout(new BorderLayout());
		qlist = new SQMQList(this);
		qlist.setPreferredSize(new Dimension(frame.getWidth()/4, frame.getHeight()));
		olist = new SQMOList(this);
		olist.setPreferredSize(new Dimension(frame.getWidth()/4, frame.getHeight()));
		JPanel lists = new JPanel();
		BoxLayout blayout = new BoxLayout(lists, BoxLayout.X_AXIS);
		lists.setLayout(blayout);
		JPanel a = new JPanel();
		a.setLayout(new BorderLayout());
		a.add("Center", qlist);
		a.add("North", new JLabel("Quests"));
		JPanel b = new JPanel();
		b.setLayout(new BorderLayout());
		b.add("Center", olist);
		b.add("North", new JLabel("Objectives"));
		lists.add(a);
		lists.add(b);
		add("West", lists);
	}
	public SQMQList getQuestList()
	{
		return qlist;
	}
	SQMFrame getFrame()
	{
		return frame;
	}
}
