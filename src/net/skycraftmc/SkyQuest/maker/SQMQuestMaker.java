package net.skycraftmc.SkyQuest.maker;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import net.skycraftmc.SkyQuest.quest.Objective;
import net.skycraftmc.SkyQuest.quest.ObjectiveType;

@SuppressWarnings("serial")
public class SQMQuestMaker extends Panel implements ActionListener
{
	private SQMFrame frame;
	private Label status = new Label("Fill in all the information then press create!");
	private Button create = new Button("Create Quest!");
	private TextField namef = new TextField("Quest Name");
	private ArrayList<Objective>objs = new ArrayList<Objective>();
	private ArrayList<String>next = new ArrayList<String>();
	private Button createob = new Button("Add Objective");
	private TextField onamef = new TextField();
	private TextField otarget = new TextField("Target");
	private TextArea description = new TextArea();
	private Choice optional = new Choice();
	private Choice def = new Choice();
	private Choice type = new Choice();
	public SQMQuestMaker(SQMFrame frame)
	{
		this.frame = frame;
		optional.add("True");
		optional.add("False");
		def.add("True");
		def.add("False");
		for(ObjectiveType t: ObjectiveType.values())type.add(t.toString());
		setLayout(new BorderLayout());
		Panel o = new Panel();
		Panel otf = new Panel();
		Panel i = new Panel();
		Panel nf = new Panel();
		Panel c = new Panel();
		Panel c1 = new Panel();
		Panel t = new Panel();
		i.setLayout(new BorderLayout());
		o.setLayout(new BorderLayout());
		otf.setLayout(new BorderLayout());
		nf.setLayout(new BorderLayout());
		t.setLayout(new BorderLayout());
		c1.setLayout(new BorderLayout());
		i.add("North", namef);
		i.add("Center", create);
		//objectives
		nf.add("North", new Label("Objective Name"));
		nf.add("Center", onamef);
		otf.add("Center", description);
		t.add("North", new Label("Target"));
		t.add("Center", otarget);
		t.add("South", new Label("Description"));
		otf.add("North", t);
		c.add(new Label("Optional?"));
		c.add(optional);
		c.add(new Label("Start with this?"));
		c.add(def);
		c1.add("North", c);
		c1.add("Center", new Label("Quest Type"));
		c1.add("East", type);
		o.add("North", nf);
		o.add("Center", otf);
		o.add("South", createob);
		o.add("East", c1);
		//
		add("North", i);
		add("West", o);
		add("South", status);
		create.addActionListener(this);
		createob.addActionListener(this);
	}
	public void actionPerformed(ActionEvent arg0) 
	{
		if(arg0.getSource() == create)
		{
			createQuest();
		}
		else if(arg0.getSource() == createob)
		{
			createObjective();
		}
	}
	public void createObjective()
	{
		if(frame.getFile() == null)
		{
			status.setText("You must load data in the home tab first!");
			return;
		}
		if(onamef.getText().isEmpty())
		{
			status.setText("You must enter a name for the objective!");
			return;
		}
		String[] desc = description.getText().split(SQMFrame.newline);
		
	}
	public void createQuest()
	{
		if(frame.getFile() == null)
		{
			status.setText("You must load data in the home tab first!");
			return;
		}
		if(namef.getText().isEmpty())
		{
			status.setText("You must input a name!");
			return;
		}
		if(frame.hasQuest(namef.getText()))
		{
			status.setText("A quest by that name already exists!");
			return;
		}
		ArrayList<Objective>o = new ArrayList<Objective>();
		ArrayList<String>n = new ArrayList<String>();
		for(Objective o1:objs)o.add(o1);
		for(String s:next)n.add(s);
		HashMap<String, Object> quest = new HashMap<String, Object>();
		quest.put("objectives", o);
		quest.put("next", n);
		quest.put("name", namef.getText());
		frame.getQuests().add(quest);
		frame.getQuestList().refresh();
		objs.clear();
	}
	
}
