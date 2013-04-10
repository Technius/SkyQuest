package net.skycraftmc.SkyQuest.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagShort extends TagBase
{
	public short data;
	public TagShort(String name) {
		super(name);
	}
	public TagShort(String name, short data)
	{
		super(name);
		this.data = data;
	}

	@Override
	public void load(DataInputStream in) throws IOException {
		data = in.readShort();
	}

	@Override
	public TagType getType() {
		return TagType.SHORT;
	}
	public void write(DataOutputStream out)throws IOException
	{
		out.writeShort(data);
	}
}
