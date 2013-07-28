package net.skycraftmc.SkyQuest.objective;

import java.awt.BorderLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.skycraftmc.SkyQuest.Objective;
import net.skycraftmc.SkyQuest.SkyQuest;
import net.skycraftmc.SkyQuest.utilitygui.ObjectiveEditor;

import org.bukkit.entity.EntityType;

public class KillObjectiveType extends ObjectiveType
{

	public boolean isComplete(String target, String progress) 
	{
		if(!isValid(target))
			throw new IllegalArgumentException("target is not valid");
		if(!isValid(progress))
			throw new IllegalArgumentException("progress is not valid");
		String[] tokensp = progress.split(" ", 2);
		String[] tokenst = target.split(" ", 2);
		if(!tokenst[1].equalsIgnoreCase(tokensp[1]))return false;
		return tokenst[0].equals(tokensp[0]);
	}
	
	public int getKills(String progress)
	{
		if(!isValid(progress))
			throw new IllegalArgumentException("progress is not valid");
		return Integer.parseInt(progress.split(" ")[0]);
	}
	public boolean isSimilarType(String target, String progress)
	{
		if(!isValid(target))
			throw new IllegalArgumentException("target is not valid");
		if(!isValid(progress))
			throw new IllegalArgumentException("progress is not valid");
		String[] tokensp = progress.split(" ", 2);
		String[] tokenst = target.split(" ", 2);
		return tokenst[1].equals(tokensp[1]);
	}
	public String getEntityType(String data)
	{
		if(!isValid(data))
			throw new IllegalArgumentException("data is not valid");
		return data.split(" ", 2)[1];
	}
	public boolean isValid(String progress)
	{
		String[] tokens = progress.split(" ", 2);
		if(tokens.length != 2)return false;
		try
		{
			if(Integer.parseInt(tokens[0]) < 0)return false;
		}catch(NumberFormatException nfe)
		{
			return false;
		}
		if(SkyQuest.isOnServer())
		{
			try
			{
				EntityType et = EntityType.valueOf(tokens[1].toUpperCase());
				return et.isAlive();
			}
			catch(IllegalArgumentException iae)
			{
				return false;
			}
		}
		return true;
	}

	public String getName() 
	{
		return "Kill";
	}
	
	public String createProgress(String data)
	{
		return "0 " + data;
	}
	
	public String getData(String progress)
	{
		if(!isValid(progress))
			throw new IllegalArgumentException("progress is not valid");
		return progress.split(" ", 2)[1];
	}
	
	@Override
	public String getProgressString(String target, String progress) 
	{
		int p = getKills(progress);
		int t = getKills(target);
		return "(" + p + "/" + t + ")";
	}
	
	@Override
	public int getItemIcon()
	{
		return 267;
	}
	
	@Override
	public String getDescription()
	{
		return "Requires the player to kill a certain amount of a specified entity";
	}
	
	@Override
	public ObjectiveEditor createEditorPanel()
	{
		return new KillObjectiveEditor();
	}
	
	@SuppressWarnings("serial")
	private class KillObjectiveEditor extends ObjectiveEditor implements DocumentListener
	{
		private JTextField amt;
		private JTextField type;
		public void init()
		{
			amt = new JTextField();
			type = new JTextField();
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JPanel p = new JPanel();
			p.setLayout(new BorderLayout());
			p.add("Center", amt);
			p.add("West", new JLabel("Amount"));
			add(p);
			JPanel p2 = new JPanel();
			p2.setLayout(new BorderLayout());
			p2.add("Center", type);
			p2.add("West", new JLabel("Type"));
			add(p2);
			amt.getDocument().addDocumentListener(this);
			type.getDocument().addDocumentListener(this);
		}
		public String createData()
		{
			return amt.getText() + " " + type.getText();
		}
		public void loadFrom(Objective o)
		{
			String[] t = o.getTarget().split(" ", 2);
			amt.setText(t[0]);
			type.setText(t[1]);
		}
		public void insertUpdate(DocumentEvent e)
		{
			update(e);
		}
		public void removeUpdate(DocumentEvent e)
		{
			update(e);
		}
		public void changedUpdate(DocumentEvent e)
		{
			update(e);
		}
		public void update(DocumentEvent e)
		{
			try
			{
				Integer.parseInt(amt.getText());
				getFinishButton().setEnabled(true);
				String s = type.getText();
				if(s.trim().length() == 0)getFinishButton().setEnabled(false);
				else if(s.contains(" "))getFinishButton().setEnabled(false);
				else getFinishButton().setEnabled(true);
			}
			catch(NumberFormatException nfe)
			{
				getFinishButton().setEnabled(false);
			}
		}
	}
}
