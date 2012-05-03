package net.skycraftmc.SkyQuest.quest;

public enum ObjectiveType 
{
	KILL, TRAVEL;
	private ObjectiveType()
	{
		
	}
	public String toString()
	{
		String a = name();
		return a.charAt(0) + a.substring(1).toLowerCase();
	}
	public static ObjectiveType getType(String s)
	{
		ObjectiveType o = null;
		try{o= valueOf(s.toUpperCase().replaceAll(" ", ""));}
		catch(IllegalArgumentException iae){return null;}
		return o;
	}
}
