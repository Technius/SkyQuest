package net.skycraftmc.SkyQuest;

public abstract class RewardType
{
	public abstract boolean reward(String player, String reward);
	public abstract boolean isValid(String reward);
}
