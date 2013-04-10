package net.skycraftmc.SkyQuest.util.nbt;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TagCompound extends TagBase
{
	private ArrayList<TagBase>tags = new ArrayList<TagBase>();
	public TagCompound(String name) 
	{
		super(name);
	}
	public TagCompound()
	{
		super(null);
	}
	public TagBase getTag(String key)
	{
		if(key == null)return null;
		for(int i = 0; i < tags.size(); i ++)
		{
			TagBase t = tags.get(i);
			if(key.equals(t.getName()))return t;
		}
		return null;
	}
	public TagList getList(String key)
	{
		TagBase t = getTag(key);
		if(t instanceof TagList)return (TagList)t;
		return null;
	}
	public void removeTag(String key)
	{
		if(key == null)return;
		for(int i = 0; i < tags.size(); i ++)
		{
			TagBase t = tags.get(i);
			if(key.equals(t.getName()))
			{
				tags.remove(t);
				break;
			}
		}
	}
	public void setTag(String name, TagBase tag)
	{
		if(name == null)return;
		removeTag(name);
		tag.setName(name);
		tags.add(tag);
	}
	public void clear()
	{
		tags.clear();
	}
	public int size()
	{
		return tags.size();
	}
	public TagBase[] getTags()
	{
		return tags.toArray(new TagBase[tags.size()]);
	}
	public void load(DataInputStream in)throws IOException 
	{
		clear();
		TagBase tag;
		while((tag = TagBase.loadTag(in)).getType() != TagType.END)setTag(tag.getName(), tag);
	}
	public TagType getType() 
	{
		return TagType.COMPOUND;
	}
	public void write(DataOutputStream out)throws IOException
	{
		TagBase[] a = getTags();
		for(int i = 0; i < a.length; i ++)
		{
			TagBase b = a[i];
			b.saveTag(out);
		}
		out.write(0);
	}
}
