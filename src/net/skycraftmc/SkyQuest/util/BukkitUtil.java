package net.skycraftmc.SkyQuest.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.lang.reflect.Method;

import net.minecraft.server.v1_6_R2.NBTBase;
import net.skycraftmc.SkyQuest.util.nbt.TagBase;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import org.bukkit.Server;

public class BukkitUtil
{
	public static NBTBase tagToNMS(TagBase tag) throws IOException
	{
		//Created piped streams
		PipedOutputStream pos = new PipedOutputStream();
		PipedInputStream pis = new PipedInputStream();
		pos.connect(pis);
		DataOutputStream dos = new DataOutputStream(pos);
		DataInputStream dis = new DataInputStream(pis);
		//Write tag data into output stream
		tag.saveTag(dos);
		//Load NBT tag
		NBTBase b = NBTBase.a(dis);
		//Close streams
		dos.close();
		dis.close();
		pos.close();
		pis.close();
		return b;
	}
	public static TagBase tagFromNMS(NBTBase nbt) throws IOException
	{
		//Created piped streams
		PipedOutputStream pos = new PipedOutputStream();
		PipedInputStream pis = new PipedInputStream();
		pos.connect(pis);
		DataOutputStream dos = new DataOutputStream(pos);
		DataInputStream dis = new DataInputStream(pis);
		//Write tag data into output stream
		NBTBase.a(nbt, dos);
		//Load NBT tag
		TagBase b = TagBase.loadTag(dis);
		//Close streams
		dos.close();
		dis.close();
		pos.close();
		pis.close();
		return b;
	}
	private static Method doCmdMethod;
	static
	{
		try
		{
			Class<?> c = Class.forName("org.bukkit.Server");
			Class<?> c2 = Class.forName("org.bukkit.command.CommandSender");
			doCmdMethod = c.getDeclaredMethod("dispatchCommand", c2, String.class);
			doCmdMethod.setAccessible(true);
		}
		catch(Exception e)
		{
			
		}
	}
	public static boolean doCommand(Object sender, String cmd)
	{
		try
		{
			return (boolean) doCmdMethod.invoke(Bukkit.getServer(), sender, cmd);
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
