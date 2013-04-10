package net.skycraftmc.SkyQuest.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagLong extends TagBase 
{
	private long data;
	public TagLong(String name) {
		super(name);
	}
	public TagLong(String name, long data) {
		super(name);
		this.data = data;
	}

	@Override
	public void load(DataInputStream in) throws IOException {
		data = in.readLong();
	}

	@Override
	public TagType getType() {
		return TagType.LONG;
	}
	public void write(DataOutputStream out)throws IOException
	{
		out.writeLong(data);
	}
}