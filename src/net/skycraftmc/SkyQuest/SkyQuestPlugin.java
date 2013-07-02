package net.skycraftmc.SkyQuest;

import java.io.File;
import java.io.IOException;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class SkyQuestPlugin extends JavaPlugin
{
	private QuestManager qm;
	private FileManager fm;
	private QuestLogManager qlm;
	private static SkyQuestPlugin inst;
	private ItemStack questlog;
	final String bookTitle = ChatColor.GOLD + "Quest Log";
	final String bookAuthor = ChatColor.AQUA + "SkyQuest";
	public void onEnable()
	{
		inst = this;
		SkyQuest.onServer = true;
		qm = new QuestManager();
		fm = new FileManager();
		qlm = new QuestLogManager(this);
		getServer().getPluginManager().registerEvents(new SkyQuestListener(this), this);
		getServer().getPluginManager().registerEvents(qlm, this);
		for(Player p:getServer().getOnlinePlayers())
		{
			String name = p.getName();
			if(qm.getQuestLog(name) == null)
			{
				PlayerQuestLog log = new PlayerQuestLog(name);
				qm.addQuestLog(log);
			}
		}
		getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable(){
			public void run()
			{
				fm.loadData(getDataFolder(), qm);
				int ql = qm.getQuests().length;
				getLogger().info("Loaded " + ql + " quest" + (ql != 1 ? "s" : ""));
				int pl = qm.getQuestLogs().length;
				getLogger().info("Loaded " + pl + " player" + (pl != 1 ? "s" : ""));
			}
		});
		getCommand("quest").setExecutor(new SkyQuestCmd(this));
		questlog = new ItemStack(Material.WRITTEN_BOOK);
		BookMeta im = (BookMeta) questlog.getItemMeta();
		im.setTitle(bookTitle);
		im.setAuthor(bookAuthor);
		questlog.setItemMeta(im);
				
	}
	public void onDisable()
	{
		try
		{
			fm.savePlayerData(new File(getDataFolder(), "Players"));
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
	public QuestManager getQuestManager()
	{
		return qm;
	}
	public QuestLogManager getQuestLogManager()
	{
		return qlm;
	}
	public static SkyQuestPlugin getPlugin()
	{
		return inst;
	}
	public ItemStack createQuestLogItem()
	{
		return questlog.clone();
	}
}
