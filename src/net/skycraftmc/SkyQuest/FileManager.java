package net.skycraftmc.SkyQuest;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import net.skycraftmc.SkyQuest.action.ActionType;
import net.skycraftmc.SkyQuest.objective.ObjectiveType;
import net.skycraftmc.SkyQuest.util.nbt.TagBase;
import net.skycraftmc.SkyQuest.util.nbt.TagByte;
import net.skycraftmc.SkyQuest.util.nbt.TagCompound;
import net.skycraftmc.SkyQuest.util.nbt.TagInt;
import net.skycraftmc.SkyQuest.util.nbt.TagList;
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
				Stage s = loadStage(((TagString)tbfstageid).data, tfstage);
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
				TagBase tbitemico = tag.getTag("iconid");
				if(tbitemico instanceof TagInt)q.setItemIconId(((TagInt)tbitemico).data);
				TagBase tbdesc = tag.getTag("description");
				ArrayList<String>desc = new ArrayList<String>();
				if(tbdesc instanceof TagList)
				{
					TagList tdesc = (TagList)tbdesc;
					for(TagBase b:tdesc.get())
					{
						if(b instanceof TagString)desc.add(((TagString)b).data);
					}
				}
				q.setDescription(desc);
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
		for(Quest q:QuestManager.getInstance().getQuests())
		{
			try
			{
				TagCompound tag = new TagCompound();
				tag.setTag("name", new TagString("name", q.getName()));
				Stage fs = q.getFirstStage();
				TagCompound fstage = saveStage(fs);
				fstage.setName("firststage");
				fstage.setTag("id", new TagString("id", fs.getID()));
				tag.setTag("firststage", fstage);
				TagCompound stages = new TagCompound("stages");
				for(Stage s:q.getStages())stages.setTag(s.getID(), saveStage(s));
				tag.setTag("stages", stages);
				TagCompound objs = new TagCompound("objectives");
				for(Objective o:q.getObjectives())objs.setTag(o.getID(), saveObjective(o));
				tag.setTag("objectives", objs);
				TagList desc = new TagList("description");
				for(String s:q.getDescription())desc.add(new TagString("", s));
				tag.setTag("description", desc);
				tag.setTag("iconid", new TagInt("iconid", q.getItemIconId()));
				File f = new File(file, q.getID() + ".dat");
				DataOutputStream dos = new DataOutputStream(new FileOutputStream(f));
				tag.saveTag(dos);
				dos.flush();
				dos.close();
			}
			catch(IOException ioe)
			{
				ioe.printStackTrace();
			}
		}
	}
	private Stage loadStage(TagCompound tag)
	{
		return loadStage(tag.getName(), tag);
	}
	private Stage loadStage(String id, TagCompound tag)
	{
		Stage st = new Stage(id);
		TagBase tl = tag.getTag("actions");
		if(tl instanceof TagList)
		{
			for(TagBase tb : ((TagList)tl).get())
			{
				if(tb instanceof TagCompound)
				{
					QuestAction qa = loadAction((TagCompound)tb);
					if(qa != null)st.addAction(qa);
				}
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
			TagBase tbicoid = tag.getTag("iconid");
			if(tbicoid instanceof TagInt)o.setItemIconId(((TagInt)tbicoid).data);
			TagBase tbopt = tag.getTag("optional");
			if(tbopt instanceof TagByte)o.setOptional(((TagByte)tbopt).data == 1);
			TagBase tbvis = tag.getTag("visible");
			if(tbvis instanceof TagByte)o.setVisible(((TagByte)tbvis).data == 1);
			TagBase rewards = tag.getTag("rewards");
			if(rewards instanceof TagList)
			{
				for(TagBase b:((TagList)rewards).get())
				{
					if(b instanceof TagCompound)
					{
						QuestAction a = loadAction((TagCompound)b);
						if(a != null)o.addReward(a);
					}
				}
			}
			return o;
		}
		catch(IllegalArgumentException iae)
		{
			return null;
		}
	}
	private TagCompound saveObjective(Objective o)
	{
		TagCompound c = new TagCompound(o.getID());
		c.setTag("name", new TagString("name", o.getName()));
		c.setTag("target", new TagString("target", o.getTarget()));
		c.setTag("type", new TagString("type", o.getType().getClass().getName()));
		c.setTag("iconid", new TagInt("iconid", o.getItemIconId()));
		c.setTag("optional", new TagByte("optional", (byte)(o.isOptional() ? 1 : 0)));
		c.setTag("visible", new TagByte("visible", (byte)(o.isVisible() ? 1 : 0)));
		TagList rewards = new TagList("rewards");
		for(QuestAction a:o.getRewards())rewards.add(saveAction(a));
		c.setTag("rewards", rewards);
		return c;
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
	private TagCompound saveStage(Stage s)
	{
		TagCompound c = new TagCompound(s.getID());
		TagList act = new TagList("actions");
		for(QuestAction a:s.getActions())act.add(saveAction(a));
		c.setTag("actions", act);
		return c;
	}
	private TagCompound saveAction(QuestAction a)
	{
		TagCompound c = new TagCompound();
		c.setTag("type", new TagString("type", a.getType().getClass().getName()));
		c.setTag("data", new TagString("data", a.getAction()));
		return c;
	}
}
