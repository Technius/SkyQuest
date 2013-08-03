package net.skycraftmc.SkyQuest.objective;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.skycraftmc.SkyQuest.Objective;
import net.skycraftmc.SkyQuest.utilitygui.ObjectiveEditor;


public class TravelObjectiveType extends ObjectiveType
{

	public boolean isComplete(String target, String progress) 
	{
		if(!isValid(progress))
			throw new IllegalArgumentException("progress is not valid");
		if(!isValid(target))
			throw new IllegalArgumentException("target is not valid");
		String[] tt = target.split(" ", 6);
		String[] tp = progress.split(" ", 6);
		int twind = tt.length - 1;
		int pwind = tp.length - 1;
		if(!tt[twind].equals(tp[pwind]))return false;
		double radius = Double.parseDouble(tt[0]);
		double tx = Double.parseDouble(tt[2]);
		double tz = Double.parseDouble(tt[3]);
		double px = Double.parseDouble(tp[2]);
		double pz = Double.parseDouble(tp[3]);
		if(tt[1].equalsIgnoreCase("circle"))
		{
			if(twind == 4)
				return Math.sqrt(Math.pow(tx - px, 2) + Math.pow(tz - pz, 2)) <= radius;
			else
			{
				double ty = Double.parseDouble(tt[4]);
				double py = Double.parseDouble(tp[4]);
				return Math.sqrt(Math.pow(tx - px, 2) + Math.pow(tz - pz, 2) + Math.pow(ty - py, 2)) <= radius;
			}
		}
		else if(tt[1].equalsIgnoreCase("square"))
		{
			if(pwind == 4)
				return px <= tx + radius && px >= tx - radius && pz <= tz + radius && pz >= tz - radius;
			else
			{
				double ty = Double.parseDouble(tt[4]);
				double py = Double.parseDouble(tp[4]);
				return px <= tx + radius && px >= tx - radius && pz <= tz + radius && pz >= tz - radius
					&& py <= ty + radius && py >= tx - radius;
			}
		}
		else return false;
	}

	public boolean isValid(String progress) 
	{
		//radius type x z [y] world
		String[] t = progress.split(" ", 6);
		if(t.length != 5 && t.length != 6)return false;
		if(!t[1].equalsIgnoreCase("circle") && !t[1].equalsIgnoreCase("square"))return false;
		try
		{
			Double.parseDouble(t[0]);
			Double.parseDouble(t[2]);
			Double.parseDouble(t[3]);
			if(t.length == 6)Double.parseDouble(t[4]);
		}
		catch(NumberFormatException nfe)
		{
			return false;
		}
		return true;
	}
	
	public String getType(String data)
	{
		if(!isValid(data))
			throw new IllegalArgumentException("data is not valid");
		return data.split(" ", 5)[1];
	}
	public int getRadius(String data)
	{
		if(!isValid(data))
			throw new IllegalArgumentException("data is not valid");
		return Integer.parseInt(data.split(" ", 5)[0]);
	}
	public String getName()
	{
		return "Travel";
	}

	public String createProgress(String data)
	{
		return data + "placeholder";
	}

	public String getData(String progress) 
	{
		return progress;
	}

	public String getProgressString(String target, String progress)
	{
		return "";
	}
	
	@Override
	public int getItemIcon()
	{
		return 345;
	}
	
	@Override
	public String getDescription()
	{
		return "Requires the player to travel to a certain area";
	}
	
	@Override
	public ObjectiveEditor createEditorPanel()
	{
		return new TravelObjectiveEditor();
	}
	
	@SuppressWarnings("serial")
	private class TravelObjectiveEditor extends ObjectiveEditor implements DocumentListener, ChangeListener
	{
		private JTextField x;
		private JTextField z;
		private JTextField world;
		private JTextField rad;
		private JTextField y;
		private JCheckBox usey;
		private JComboBox<String> type;
		public void init()
		{
			rad = new JTextField();
			type = new JComboBox<String>(new String[]{"square", "circle"});
			x = new JTextField();
			z = new JTextField();
			y = new JTextField();
			y.setEnabled(false);
			usey = new JCheckBox("Y", false);
			world = new JTextField();
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel p = new JPanel();
			p.setLayout(new BorderLayout());
			p.add("Center", type);
			p.add("West", new JLabel("Boundary Type"));
			add(p);
			JPanel p2 = new JPanel();
			p2.setLayout(new BorderLayout());
			p2.add("Center", rad);
			p2.add("West", new JLabel("Radius"));
			add(p2);
			JPanel p3 = new JPanel();
			p3.setLayout(new BorderLayout());
			p3.add("Center", x);
			p3.add("West", new JLabel("X"));
			add(p3);
			JPanel p6 = new JPanel();
			p6.setLayout(new BorderLayout());
			p6.add("Center", y);
			p6.add("West", usey);
			add(p6);
			JPanel p4 = new JPanel();
			p4.setLayout(new BorderLayout());
			p4.add("Center", z);
			p4.add("West", new JLabel("Z"));
			add(p4);
			JPanel p5 = new JPanel();
			p5.setLayout(new BorderLayout());
			p5.add("Center", world);
			p5.add("West", new JLabel("World"));
			add(p5);
			x.getDocument().addDocumentListener(this);
			y.getDocument().addDocumentListener(this);
			z.getDocument().addDocumentListener(this);
			world.getDocument().addDocumentListener(this);
			rad.getDocument().addDocumentListener(this);
			usey.addChangeListener(this);
		}
		public String createData()
		{
			return rad.getText() + " " + type.getSelectedItem().toString() + " " + x.getText() +
				" " + z.getText() + (usey.isSelected() ? " " + y.getText() : "") + " " + world.getText();
		}
		public void loadFrom(Objective o)
		{
			String[] t = o.getTarget().split(" ", 6);
			rad.setText(t[0]);
			type.setSelectedItem(t[1]);
			x.setText(t[2]);
			z.setText(t[3]);
			if(t.length == 6)
			{
				y.setText(t[4]);
				usey.setSelected(true);
			}
			world.setText(t[t.length - 1]);
		}
		public void insertUpdate(DocumentEvent e)
		{
			update();
		}
		public void removeUpdate(DocumentEvent e)
		{
			update();
		}
		public void changedUpdate(DocumentEvent e)
		{
			update();
		}
		public void update()
		{
			try
			{
				Double.parseDouble(x.getText());
				Double.parseDouble(z.getText());
				Double.parseDouble(rad.getText());
				if(usey.isSelected())Double.parseDouble(y.getText());
				String s = world.getText();
				if(s.trim().length() == 0)getFinishButton().setEnabled(false);
				else if(s.contains(" "))getFinishButton().setEnabled(false);
				else getFinishButton().setEnabled(true);
			}
			catch(NumberFormatException nfe)
			{
				getFinishButton().setEnabled(false);
			}
		}
		public void stateChanged(ChangeEvent e)
		{
			boolean a = usey.isSelected();
			y.setEnabled(a);
			if(!a)y.setText("");
			update();
		}
	}
}
