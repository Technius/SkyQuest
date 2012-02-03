package net.skycraftmc.SkyQuest.quest;

public class Objective 
{
	private ObjectiveType type;
	private int objective;
	private String label;
	public Objective(ObjectiveType type, int objective, String label)
	{
		this.type = type;
		this.objective = objective;
		this.label = label;
	}
	public ObjectiveType getType()
	{
		return type;
	}
	public String toString()
	{
		return (label + ":" + type.toString() + "," + objective);
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
