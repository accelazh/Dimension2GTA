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
	private int hurtValue=10;  //ÿ���˺�����ʱ���۳���Ѫ
	private int hurtInterval=10;  //���ٸ�godTimer���ڣ�����һ���˺�
	private int hurtIntervalCounter=0;
	private int hurtTimes=0;    //�ܹ��������ٴ��˺���0Ϊ������
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
