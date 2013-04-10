package net.skycraftmc.SkyQuest.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagDouble extends TagBase 
{
	private double data;
	public TagDouble(String name) {
		super(name);
	}
	public TagDouble(String name, double data) {
		super(name);
		this.data = data;
	}

	@Override
	public void load(DataInputStream in) throws IOException {
		data = in.readDouble();
	}

	@Override
	public TagType getType() {
		return TagType.DOUBLE;
	}
	public void write(DataOutputStream out)throws IOException
	{
		out.writeDouble(data);
	}
}