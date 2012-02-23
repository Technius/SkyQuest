package net.skycraftmc.SkyQuest.quest;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class MoveObjective extends Objective
{
	private int radius;
	private Location loc;
	private Player player;
	private boolean completed;
	public MoveObjective(Player player, String objective, String label,List<String> rewards, String text, int radius, Location loc) 
	{
		super(ObjectiveType.TRAVEL, objective, label, rewards, text);
		this.radius = radius;
		this.player = player;
	}
	public void setRadius(int radius)
	{
		this.radius = radius;
	}
	public int getRadius()
	{
		return radius;
	}
	public Location getLocation()
	{
		return loc;
	}
	public void setLocation(Location location)
	{
		loc = location;
	}
	public String getTarget() 
	{
		return "Move to " + loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + " at world " + loc.getWorld();
	}
	public String getProgressAsString() 
	{
		if(!getComplete())return "You are not quite there yet";
		else return "You are there";
	}
	public Player getPlayer()
	{
		return player;
	}
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	public void setComplete(boolean complete) 
	{
		completed = complete;
	}
	public boolean getComplete() 
	{
		return completed;
	}
	public String getParsedObjective() 
	{
		return objective;
	}
	public boolean isComplete() 
	{
		Location p1 = loc.add(radius, radius, radius);
		Location p2 = loc.subtract(radius, radius , radius);
		double minx, miny, minz, maxx, maxy, maxz;
		minx = Math.min(p1.getX(), p2.getX());
		miny = Math.min(p1.getY(), p2.getY());
		minz = Math.min(p1.getZ(), p2.getZ());
		maxx = Math.max(p1.getX(), p2.getX());
		maxy = Math.max(p1.getY(), p2.getY());
		maxz = Math.max(p1.getZ(), p2.getZ());
		Location p = player.getLocation();
		if(p.getX() < minx || p.getX() > maxx)return false;
		if(p.getY() < miny || p.getY() > maxy)return false;
		if(p.getZ() < minz || p.getZ() > maxz)return false;
		return true;
	}

}
