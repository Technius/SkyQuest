package net.skycraftmc.SkyQuest.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TagList extends TagBase 
{
	private TagType type = null;
	private ArrayList<TagBase>tags = new ArrayList<TagBase>();
	public TagList(String name) {
		super(name);
	}
	
	public void load(DataInputStream in) throws IOException 
	{
		tags.clear();
		type = TagType.byID(in.read());
		int size = in.readInt();
		for(int i = 0; i < size; i ++)
		{
			TagBase tag = TagBase.createTag(type, null);
			tag.load(in);
			tags.add(tag);
		}
	}

	@Override
	public TagType getType() {
		return TagType.LIST;
	}
	public int size()
	{
		return tags.size();
	}
	public void add(TagBase b)
	{
		if(type == null)type = b.getType();
		else if(b.getType() != type)return;
		tags.add(b);
	}
	public TagBase get(int index)
	{
		return tags.get(index);
	}
	public TagBase[] get()
	{
		return tags.toArray(new TagBase[tags.size()]);
	}
	public void write(DataOutputStream out)throws IOException
	{
		out.write(type.getID());
		out.writeInt(tags.size());
		TagBase[] tags = get();
		for(int i = 0; i < tags.length; i ++)tags[i].write(out);
	}
}
