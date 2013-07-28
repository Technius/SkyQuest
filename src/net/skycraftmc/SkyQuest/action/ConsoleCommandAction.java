package net.skycraftmc.SkyQuest.action;


import java.awt.BorderLayout;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JTextField;

import net.skycraftmc.SkyQuest.QuestAction;
import net.skycraftmc.SkyQuest.util.BukkitUtil;
import net.skycraftmc.SkyQuest.utilitygui.ActionEditor;
import net.skycraftmc.SkyQuest.utilitygui.component.EmptyTextListener;

import org.bukkit.Bukkit;

public class ConsoleCommandAction extends ActionType
{
	private Pattern pat = Pattern.compile("&player;");
	public boolean apply(String player, String action) 
	{
		if(!isValid(action))
			throw new IllegalArgumentException("action is not valid");
		Matcher m = pat.matcher(action);
		BukkitUtil.doCommand(Bukkit.getConsoleSender(), m.replaceAll(Matcher.quoteReplacement(player)));
		return true;
	}

	public boolean isValid(String action) 
	{
		if(action == null)return false;
		return !action.trim().isEmpty();
	}

	public String getName() 
	{
		return "Console Command";
	}

	public boolean requiresPlayer() 
	{
		return false;
	}
	
	public String getDescription()
	{
		return "Makes the console execute a command.";
	}
	@Override
	public ActionEditor createEditorPanel()
	{
		return new ConsoleCommandEditorPanel();
	}
	@SuppressWarnings("serial")
	private class ConsoleCommandEditorPanel extends ActionEditor
	{
		private JTextField tf;
		@Override
		public void init()
		{
			tf = new JTextField();
			setLayout(new BorderLayout());
			add("Center", tf);
			add("West", new JLabel("Command"));
			tf.getDocument().addDocumentListener(new EmptyTextListener(getFinishButton()));
		}
		public String createData() 
		{
			return tf.getText().trim();
		}
		public void loadFrom(QuestAction action)
		{
			tf.setText(action.getAction());
		}
	}
}
