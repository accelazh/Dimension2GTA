package functionZones;

import basicConstruction.*;
import gameDisplayProcessor.MainGameWindow;

import java.awt.*;

import javax.swing.*;

import utilities.MyPoint;

public class SavePlace extends FunctionZone
{

	public SavePlace(MyPoint location, Dimension totalSize)
	{
		super(new ImageIcon("pic/default1.jpg"),location,totalSize,false,true);
	    
	}


	public void functionPerformed(Human whoTriggers, MainGameWindow master) 
	{
		if(whoTriggers instanceof Player)
		{
			whoTriggers.setHealthPoint(whoTriggers.getMaxHealthPoint());
			
			//然后存储进度，并延时
		}
		
	}
	
	
}
