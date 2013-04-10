package net.skycraftmc.SkyQuest.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagByte extends TagBase 
{
	public byte data;
	public TagByte(String name) {
		super(name);
	}
	public TagByte(String name, byte data)
	{
		super(name);
		this.data = data;
	}

	@Override
	public TagType getType() {
		return TagType.BYTE;
	}

	@Override
	public void load(DataInputStream in)throws IOException{
		data = in.readByte();
	}
	public void write(DataOutputStream out)throws IOException
	{
		out.writeByte(data);
	}
}
