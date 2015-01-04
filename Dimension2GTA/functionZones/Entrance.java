package functionZones;

import basicConstruction.*;
import gameDisplayProcessor.MainGameWindow;

import java.awt.*;
import javax.swing.*;

import utilities.MyPoint;


//������ǵ�ͼ�ĳ���ڣ�����һ���л���ͼ��ֻ��player����ʹ��
public class Entrance extends FunctionZone
{
	
	

	//data field
	private int mapID=-1;
	private MapContainer landMapContainer=null;
	private MyPoint playerBirthPoint=null;
		
	

	//�����������췽����д����Entrance���벢�ҽ�������ã�����mapID��landMapContainer������һ
	public Entrance(MyPoint location,Dimension totalSize,int mapID,MyPoint playerBirthPoint)
	{
		super(new ImageIcon("pic/default1.jpg"),location,totalSize,false,true);
		this.mapID=mapID;
		this.playerBirthPoint=playerBirthPoint;
		
	}
	public Entrance(MyPoint location,Dimension totalSize,MapContainer landMapContainer,MyPoint playerBirthPoint)
	{
		super(new ImageIcon("pic/default1.jpg"),location,totalSize,false,true);
		this.landMapContainer=landMapContainer;
		this.playerBirthPoint=playerBirthPoint;
	}

	public int getMapID()
	{
		return mapID;
	}

	public void setMapID(int mapID) 
	{
		this.mapID = mapID;
	}

	public MapContainer getLandMapContainer()
	{
		return landMapContainer;
	}

	public void setLandMapContainer(MapContainer landMapContainer)
	{
		this.landMapContainer = landMapContainer;
	}

	public MyPoint getPlayerBirthPoint() 
	{
		return playerBirthPoint;
	}

	public void setPlayerBirthPoint(MyPoint playerBirthPoint)
	{
		this.playerBirthPoint = playerBirthPoint;
	}
	
	public void functionPerformed(Human whoTriggers, MainGameWindow master)
	{
		if(whoTriggers instanceof Player)
		{
			if(mapID!=-1)
			{
				master.switchMapContainer(mapID, playerBirthPoint,null,null);
			}
			else
			{
				if(landMapContainer!=null)
				{
					master.switchMapContainer(landMapContainer, playerBirthPoint,null,null);
				}
			}
		}
		
	}

	
	
	

}
