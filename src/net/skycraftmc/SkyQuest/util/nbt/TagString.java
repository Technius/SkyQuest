package net.skycraftmc.SkyQuest.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagString extends TagBase 
{
	public String data;
	public TagString(String name) {
		super(name);
	}
	public TagString(String name, String data)
	{
		super(name);
		this.data = data;
	}

	@Override
	public void load(DataInputStream in) throws IOException {
		data = in.readUTF();
	}

	@Override
	public TagType getType() {
		return TagType.STRING;
	}
	public void write(DataOutputStream out)throws IOException
	{
		out.writeUTF(data);
	}
}
