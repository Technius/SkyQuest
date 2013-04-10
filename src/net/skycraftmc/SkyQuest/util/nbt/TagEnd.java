package net.skycraftmc.SkyQuest.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class TagEnd extends TagBase
{

	public TagEnd() {
		super(null);
	}

	public TagType getType() {
		return TagType.END;
	}

	public void load(DataInputStream in) {
	}
	public void write(DataOutputStream out)throws IOException
	{
	}
}
