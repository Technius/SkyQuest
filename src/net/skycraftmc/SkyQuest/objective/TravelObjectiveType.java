package net.skycraftmc.SkyQuest.objective;


public class TravelObjectiveType extends ObjectiveType
{

	public boolean isComplete(String target, String progress) 
	{
		if(!isValid(progress))
			throw new IllegalArgumentException("progress is not valid");
		if(!isValid(target))
			throw new IllegalArgumentException("target is not valid");
		String[] tt = target.split(" ", 5);
		String[] tp = progress.split(" ", 5);
		if(!tt[4].equals(tp[4]))return false;
		double radius = Double.parseDouble(tt[0]);
		double tx = Double.parseDouble(tt[2]);
		double tz = Double.parseDouble(tt[3]);
		double px = Double.parseDouble(tp[2]);
		double pz = Double.parseDouble(tp[3]);
		if(tt[1].equalsIgnoreCase("circle"))
		{
			return Math.sqrt(Math.pow(tx - px, 2) + Math.pow(tz - pz, 2)) <= radius;
		}
		else if(tt[1].equalsIgnoreCase("square"))
		{
			return px <= tx + radius && px >= tx - radius && pz <= tz + radius && pz >= tz - radius;
		}
		else return false;
	}

	public boolean isValid(String progress) 
	{
		//radius type x z world
		String[] t = progress.split(" ", 5);
		if(t.length != 5)return false;
		if(!t[1].equalsIgnoreCase("circle") && !t[1].equalsIgnoreCase("square"))return false;
		try
		{
			Double.parseDouble(t[0]);
			Double.parseDouble(t[2]);
			Double.parseDouble(t[3]);
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
}
