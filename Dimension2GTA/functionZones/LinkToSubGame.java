package functionZones;

import basicConstruction.*;
import gameDisplayProcessor.MainGameWindow;

import java.awt.*;

import javax.swing.*;

import utilities.MyPoint;


//这个类只能链接到player一个人的游戏
public class LinkToSubGame extends FunctionZone
{
	
	private int subGameName=-1;
	
	public LinkToSubGame(MyPoint location, Dimension totalSize,int subGameName)
	{
		super(new ImageIcon("pic/default1.jpg"),location,totalSize,false,true);
	    this.subGameName=subGameName;
	}

	public int getSubGameName() {
		return subGameName;
	}

	public void setSubGameName(int subGameName) {
		this.subGameName = subGameName;
	}
	
	public void functionPerformed(Human whoTriggers, MainGameWindow master)
	{
		if(whoTriggers instanceof Player)
		{
			//根据subGameName新建subGame,
			//可利用player的subGameByMapChanging防止垃圾回收
		}
	}
	
}
