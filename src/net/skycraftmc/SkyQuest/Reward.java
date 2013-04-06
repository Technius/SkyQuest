package net.skycraftmc.SkyQuest;

public class Reward 
{
	private RewardType type;
	private String reward;
	public Reward(RewardType type, String reward)
	{
		if(!type.isValid(reward))
			throw new IllegalArgumentException("reward is not valid for type " + type.getName());
		this.type = type;
		this.reward = reward;
	}
	public RewardType getType()
	{
		return type;
	}
	public String getAction()
	{
		return reward;
	}
}
