package net.skycraftmc.SkyQuest;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OpenQuestLog
{
	private Player player;
	private Quest selected = null;
	private InventoryView view;
	private Inventory inv;
	private Quest[] curassigned;
	private PlayerQuestLog log;
	public OpenQuestLog(Player player, PlayerQuestLog log, Inventory inv)
	{
		this.log = log;
		this.player = player;
		this.inv = inv;
		curassigned = log.getAssigned();
		update();
		view = player.openInventory(inv);
	}
	public Player getPlayer()
	{
		return player;
	}
	public Quest getSelected()
	{
		return selected;
	}
	public void setSelected(Quest value)
	{
		selected = value;
	}
	public InventoryView getInventoryView()
	{
		return view;
	}
	public Inventory getInventory()
	{
		return inv;
	}
	public void update()
	{
		inv.clear();
		if(selected == null)
		{
			Quest[] as = curassigned;
			for(int i = 0; i < as.length && i < 27; i ++)
			{
				Quest q = as[i];
				Material m = Material.getMaterial(q.getItemIconId());
				if(m == null || m == Material.AIR)m = Material.WRITTEN_BOOK;
				ItemStack qstack = new ItemStack(m);
				ItemMeta im = player.getServer().getItemFactory().getItemMeta(m);
				im.setDisplayName(ChatColor.GREEN + q.getName());
				ArrayList<String>lore = new ArrayList<String>();
				lore.add(ChatColor.YELLOW + "Click for more information!");
				im.setLore(lore);
				qstack.setItemMeta(im);
				inv.setItem(i, qstack);
			}
		}
		curassigned = log.getAssigned();
	}
}
