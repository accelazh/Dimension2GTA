package functionZones;

import basicConstruction.*;
import gameDisplayProcessor.*;

import java.awt.*;

import javax.swing.*;

import utilities.MyPoint;

public class TriggerHurt extends FunctionZone
{
	//for debug
	private final static boolean debug19=ControlSetting.debug19;
	
	//date field
	private int hurtValue=10;  //每次伤害发生时，扣除的血
	private int hurtInterval=10;  //多少个godTimer周期，发生一次伤害
	private int hurtIntervalCounter=0;
	private int hurtTimes=0;    //总共发生多少次伤害，0为无数次
	private int hurtTimesCounter=0;
	
	
	public TriggerHurt(int hurtValue,double hurtIntervalSeconds,int hurtTimes, MyPoint location,Dimension totalSize)
	{
		super(new ImageIcon("pic/default1.jpg"),location,totalSize,false,false);
	
		this.hurtValue=hurtValue;
		this.hurtInterval=(int)(hurtIntervalSeconds*1000/ControlSetting.godTimerInterval);
	
		this.hurtIntervalCounter=0;
		this.hurtTimesCounter=0;
		this.hurtTimes=hurtTimes;
	}

	@Override
	public void functionPerformed(Human whoTriggers, MainGameWindow master) 
	{
		if(debug19)
		{
			System.out.println("TriggerHurt active");
		}
		
				
		if(hurtInterval==hurtIntervalCounter)
		{
			whoTriggers.hurtByValue(hurtValue);
			hurtIntervalCounter=0;
			
			if(hurtTimes>0)
			{
				hurtTimesCounter++;
			}
		}
		else
		{
			hurtIntervalCounter++;
		}
		
		if(hurtTimes>0)
		{
			if(hurtTimes==hurtTimesCounter)
			{
				master.getLandMapContainer().removeSolid(this);
			}
		}
	}
	
	

}
