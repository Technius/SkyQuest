package net.skycraftmc.SkyQuest;

public enum CompletionState
{
	/**
	 * A quest state that represents a quest that has
	 * not been completed or failed yet.
	 * ID: 0
	 */
	IN_PROGRESS,
	/**
	 * A quest state that represents a quest that has
	 * been completed.
	 * ID: 1
	 */
	COMPLETE,
	/**
	 * A quest state that represents a quest that has been failed.
	 * ID: 2
	 */
	FAILED;
	/**
	 * @return The CompletionState with this ID, or null if not found.
	 */
	public static CompletionState getById(int id)
	{
		if(id < 0 || id >= values().length)return null;
		return values()[id];
	}
	/**
	 * @return The CompletionState with this name, or null if not found.
	 */
	public static CompletionState getByName(String name)
	{
		try
		{
			return valueOf(name.toUpperCase().replace(' ', '_'));
		}
		catch(IllegalArgumentException iae)
		{
			return null;
		}
	}
	/**
	 * @return The ID of this completion state.
	 */
	public int getId()
	{
		return this.ordinal();
	}
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		String[] t = name().split("_");
		for(String s:t)
		{
			if(sb.length() > 0)sb.append(" ");
			sb.append(Character.toUpperCase(s.charAt(0)));
			if(s.length() > 1)sb.append(s.substring(1, s.length()).toLowerCase());
		}
		return sb.toString();
	}
}
