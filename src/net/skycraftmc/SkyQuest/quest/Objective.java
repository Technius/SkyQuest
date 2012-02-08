package net.skycraftmc.SkyQuest.quest;

import java.util.List;

public abstract class Objective 
{
	private ObjectiveType type;
	protected String objective;
	private String label;
	private List<String> rewards;
	private String text;
	public Objective(ObjectiveType type, String objective, String label, List<String> rewards, String text)
	{
		this.type = type;
		this.objective = objective;
		this.label = label;
		this.rewards = rewards;
		this.text = text;
	}
	public ObjectiveType getType()
	{
		return type;
	}
	public abstract boolean isComplete();
	public String getObjective()
	{
		return objective;
	}
	public String getLabel()
	{
		return label;
	}
	public String toString()
	{
		return (label + ":" + type.toString() + "," + objective);
	}
	public String getText()
	{
		return text;
	}
	public List<String> getRewards()
	{
		return rewards;
	}
	public enum ObjectiveType
	{
		KILL(1), BLOCK_BREAK(2), BLOCK_BUILD(3), TRAVEL(4);
		private int id;
		private ObjectiveType(int id)
		{
			this.id = id;
		}
		public String toString()
		{
			String sl = name().toLowerCase().replaceAll("_", " ");
			String[] st = sl.split("[ ]");
			String m = Character.toUpperCase(st[0].charAt(0)) + st[0].substring(1);
			for(String x: st)
			{
				if(x.equalsIgnoreCase(st[0]))continue;
				m = m + " " + Character.toUpperCase(x.charAt(0)) + x.substring(1);
			}
			return m;
		}
		
		public int getId(ObjectiveType objective)
		{
			return id;
		}
		public ObjectiveType getById(int id)
		{
			for(ObjectiveType objective : ObjectiveType.values())
			{
				if(objective.getId(objective) == id) return objective;
			}
			return null;
		}
		
	}
}
