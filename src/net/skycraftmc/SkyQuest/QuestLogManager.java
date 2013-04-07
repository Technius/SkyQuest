package net.skycraftmc.SkyQuest;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QuestLogManager implements Listener
{
	private ArrayList<OpenQuestLog> openlogs = new ArrayList<OpenQuestLog>();
	private SkyQuestPlugin plugin;
	public QuestLogManager(SkyQuestPlugin plugin)
	{
		this.plugin = plugin;
	}
	public void openQuestLog(Player player)
	{
		if(getOpenQuestLog(player) != null)return;
		Inventory inv = plugin.getServer().createInventory(null, 36, "Quest Log");
		PlayerQuestLog log = plugin.getQuestManager().getQuestLog(player.getName());
		OpenQuestLog o = new OpenQuestLog(player, log, inv);
		o.update();
		openlogs.add(o);
	}
	public OpenQuestLog getOpenQuestLog(Player player)
	{
		for(OpenQuestLog o:openlogs)
		{
			if(o.getPlayer() == player)return o;
		}
		return null;
	}
	@EventHandler
	public void close(InventoryCloseEvent event)
	{
		Player player = (Player) event.getView().getPlayer();
		OpenQuestLog oql = getOpenQuestLog(player);
		if(oql != null)openlogs.remove(oql);
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void click(InventoryClickEvent event)
	{
		Player player = (Player) event.getView().getPlayer();
		OpenQuestLog oql = getOpenQuestLog(player);
		if(oql == null)return;
		event.setCancelled(true);
		if(event.getInventory() != oql.getInventory())return;
		if(event.getCurrentItem() == null)return;
		Quest sel = oql.getSelected();
		if(sel == null)
		{
			
		}
		else if(event.getSlot() == 27)
		{
			oql.setSelected(null);
			oql.update();
		}
		player.updateInventory();
	}
}
