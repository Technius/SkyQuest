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
}
