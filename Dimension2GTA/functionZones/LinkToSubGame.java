package functionZones;

import basicConstruction.*;
import gameDisplayProcessor.MainGameWindow;

import java.awt.*;

import javax.swing.*;

import utilities.MyPoint;


//�����ֻ�����ӵ�playerһ���˵���Ϸ
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
			//����subGameName�½�subGame,
			//������player��subGameByMapChanging��ֹ��������
		}
	}
	
}
