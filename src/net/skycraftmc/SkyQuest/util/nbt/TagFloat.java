package net.skycraftmc.SkyQuest.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagFloat extends TagBase 
{
	private float data;
	public TagFloat(String name) {
		super(name);
	}
	public TagFloat(String name, float data) {
		super(name);
		this.data = data;
	}

	@Override
	public void load(DataInputStream in) throws IOException {
		data = in.readFloat();
	}

	@Override
	public TagType getType() {
		return TagType.FLOAT;
	}
	public void write(DataOutputStream out)throws IOException
	{
		out.writeFloat(data);
	}
}
