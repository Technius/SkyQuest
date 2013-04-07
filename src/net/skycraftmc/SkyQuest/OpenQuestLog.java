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
	private int page = 1;
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
				Quest q = as[i + (page - 1)*27];
				Material m = Material.getMaterial(q.getItemIconId());
				if(m == null || m == Material.AIR)m = Material.BOOK_AND_QUILL;
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
		else
		{
			QuestData qd = log.getProgress(selected);
			Objective[] oa = qd.getQuest().getObjectives();
			for(int i = 0; i < oa.length && i < 27; i ++)
			{
				Objective o = oa[i];
				Material m = Material.getMaterial(o.getItemIconId());
				if(m == null || m == Material.AIR)m = Material.getMaterial(o.getType().getItemIcon());
				if(m == null || m == Material.AIR)m = Material.WRITTEN_BOOK;
				ItemStack ostack = new ItemStack(m);
				ItemMeta im = player.getServer().getItemFactory().getItemMeta(m);
				String ps = o.getType().getProgressString(o.getTarget(), qd.getProgress(o.getID()));
				im.setDisplayName(ChatColor.GREEN + o.getName() + " " + ps);
				ostack.setItemMeta(im);
				inv.setItem(i, ostack);
			}
			ItemStack back = new ItemStack(Material.ARROW);
			ItemMeta bim = player.getServer().getItemFactory().getItemMeta(Material.ARROW);
			bim.setDisplayName(ChatColor.YELLOW + "Back to quest log");
			back.setItemMeta(bim);
			inv.setItem(27, back);
		}
		curassigned = log.getAssigned();
	}
	public int getPage()
	{
		return page;
	}
	public void setPage(int page)
	{
		this.page = page;
	}
	public Quest[] getCurrentQuestList()
	{
		return curassigned;
	}
}
