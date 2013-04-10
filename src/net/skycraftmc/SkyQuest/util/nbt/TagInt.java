package net.skycraftmc.SkyQuest.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagInt extends TagBase 
{
	private int data;
	public TagInt(String name) {
		super(name);
	}
	public TagInt(String name, int data) {
		super(name);
		this.data = data;
	}

	@Override
	public void load(DataInputStream in) throws IOException {
		data = in.readInt();
	}

	@Override
	public TagType getType() {
		return TagType.INT;
	}
	public void write(DataOutputStream out)throws IOException
	{
		out.writeInt(data);
	}
}
