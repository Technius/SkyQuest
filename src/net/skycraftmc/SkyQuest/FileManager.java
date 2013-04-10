package net.skycraftmc.SkyQuest;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.skycraftmc.SkyQuest.action.ActionType;
import net.skycraftmc.SkyQuest.objective.ObjectiveType;
import net.skycraftmc.SkyQuest.util.nbt.TagBase;
import net.skycraftmc.SkyQuest.util.nbt.TagCompound;
import net.skycraftmc.SkyQuest.util.nbt.TagString;

public class FileManager 
{
	public void loadData(File file, QuestManager qm) throws IOException
	{
		if(!file.exists())return;
		File players = new File(file, "Players");
		if(!players.exists())players.mkdir();
		File quests = new File(file, "Quests");
		if(!quests.exists())quests.mkdir();
		for(File f:quests.listFiles())
		{
			if(!f.getName().endsWith(".dat"))continue;
			String fname = f.getName();
			String id = fname.substring(0, fname.length() - 4);
			try
			{
				DataInputStream dis = new DataInputStream(new FileInputStream(f));
				TagBase btag = TagBase.loadTag(dis);
				dis.close();
				if(!(btag instanceof TagCompound))continue;
				TagCompound tag = (TagCompound)btag;
				TagBase tbname = tag.getTag("name");
				if(!(tbname instanceof TagString))continue;
				String name = ((TagString)tbname).data;
				TagBase tbfstage = tag.getTag("firststage");
				if(!(tbfstage instanceof TagCompound))continue;
				TagCompound tfstage = (TagCompound)tbfstage;
				TagBase tbfstageid = tfstage.getTag("id");
				if(!(tbfstageid instanceof TagString))continue;
				Stage s = loadStage(((TagString)tbfstageid).data, (tfstage));
				if(s == null)continue;
				Quest q = new Quest(id, name, s);
				TagBase tbstages = tag.getTag("stages");
				if(tbstages instanceof TagCompound)
				{
					TagCompound stages = (TagCompound)tbstages;
					for(TagBase tb:stages.getTags())
					{
						if(tb instanceof TagCompound)
						{
							Stage a = loadStage((TagCompound)tb);
							if(a != null)q.addStage(a);
						}
					}
				}
				TagBase tbobj = tag.getTag("objectives");
				if(tbobj instanceof TagCompound)
				{
					TagCompound obj = (TagCompound)tbobj;
					for(TagBase b: obj.getTags())
					{
						if(!(b instanceof TagCompound))continue;
						TagCompound c = (TagCompound)b;
						Objective o = loadObjective(c);
						if(o != null)q.addObjective(o);
					}
				}
				QuestManager.getInstance().addQuest(q);
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}
	public void saveData(File file, QuestManager qm) throws IOException
	{
		if(!file.exists())file.mkdir();
		File players = new File(file, "Players");
		if(!players.exists())players.mkdir();
		File quests = new File(file, "Quests");
		if(!quests.exists())quests.mkdir();
		//TODO Save
	}
	private Stage loadStage(TagCompound tag)
	{
		return loadStage(tag.getName(), tag);
	}
	private Stage loadStage(String id, TagCompound tag)
	{
		Stage st = new Stage(id);
		for(TagBase tb : tag.getTags())
		{
			if(tb instanceof TagCompound)
			{
				QuestAction qa = loadAction((TagCompound)tb);
				if(qa != null)st.addAction(qa);
			}
		}
		return st;
	}
	private Objective loadObjective(TagCompound tag)
	{
		try
		{
			TagBase tbname = tag.getTag("name");
			if(!(tbname instanceof TagString))return null;
			TagBase tbtarget = tag.getTag("target");
			if(!(tbtarget instanceof TagString))return null;
			TagBase tbtypes = tag.getTag("type");
			if(!(tbtypes instanceof TagString))return null;
			ObjectiveType t = QuestManager.getInstance().getRegisteredObjectiveType(((TagString)tbtypes).data);
			if(t == null)return null;
			Objective o = new Objective(tag.getName(), ((TagString)tbname).data, t, ((TagString)tbtarget).data);
			return o;
		}
		catch(IllegalArgumentException iae)
		{
			return null;
		}
	}
	private QuestAction loadAction(TagCompound tag)
	{
		try
		{
			TagBase ttype = tag.getTag("type");
			if(!(ttype instanceof TagString))return null;
			ActionType t = QuestManager.getInstance().getRegisteredActionType(((TagString)ttype).data);
			if(t == null)return null;
			TagBase tdata = tag.getTag("data");
			if(!(tdata instanceof TagString))return null;
			return new QuestAction(t, ((TagString)tdata).data);
		}
		catch(IllegalArgumentException iae)
		{
			return null;
		}
	}
}
