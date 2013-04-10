package net.skycraftmc.SkyQuest.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class TagBase 
{
	protected String name;
	public TagBase(String name)
	{
		if(name == null)this.name = "";
		else this.name = name;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		if(name == null)this.name = "";
		else this.name = name;
	}
	public static TagBase loadTag(DataInputStream in) throws IOException
	{
		TagType t = TagType.byID(in.read());
		if(t == TagType.END)return new TagEnd();
		TagBase base = createTag(t, in.readUTF());
		base.load(in);
		return base;
	}
	public void saveTag(DataOutputStream out)throws IOException
	{
		int id = getType().id;
		out.write(id);
		if(id == TagType.END.id)return;
		out.writeUTF(name);
		write(out);
	}
	public abstract void load(DataInputStream in) throws IOException;
	public abstract void write(DataOutputStream out) throws IOException;
	protected static TagBase createTag(TagType type, String name)
	{
		switch(type)
		{
		case END: return new TagEnd();
		case BYTE: return new TagByte(name);
		case SHORT: return new TagShort(name);
		case INT: return new TagInt(name);
		case LONG: return new TagLong(name);
		case FLOAT: return new TagFloat(name);
		case DOUBLE: return new TagDouble(name);
		case STRING: return new TagString(name);
		case LIST: return new TagList(name);
		case COMPOUND: return new TagCompound(name);
		}
		return null;
	}
	protected enum TagType
	{
		END(0),
		BYTE(1),
		SHORT(2),
		INT(3),
		LONG(4),
		FLOAT(5),
		DOUBLE(6),
		BYTE_ARRAY(7),
		STRING(8),
		LIST(9),
		COMPOUND(10),
		INT_ARRAY(11);
		private int id;
		private TagType(int id)
		{
			this.id = id;
		}
		public int getID()
		{
			return id;
		}
		public static TagType byID(int id)
		{
			TagType[] values = TagType.values();
			if(id < 0 || id >= values.length)return null;
			return values[id];
		}
	}
	public abstract TagType getType();
}
