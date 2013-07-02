package net.skycraftmc.SkyQuest.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import net.minecraft.server.v1_6_R1.NBTBase;
import net.skycraftmc.SkyQuest.util.nbt.TagBase;

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
}
