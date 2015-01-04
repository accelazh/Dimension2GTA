package functionZones;

import gameDisplayProcessor.MainGameWindow;

import java.awt.*;

import javax.swing.*;

import basicConstruction.*;

import subGame.shootingPractice.*;
import utilities.MyPoint;

public class LinkToShootingPractice7 extends FunctionZone
{
	
	
	

	public LinkToShootingPractice7(MyPoint location,Dimension totalSize)
	{
		super(new ImageIcon("pic/default1.jpg"),location,totalSize,false,true);
		
	}
	
	@Override
	public void functionPerformed(Human whoTriggers, MainGameWindow master)
	{
		if(whoTriggers instanceof Player)
		{
			Player player=(Player)whoTriggers;
			ShootingPractice7 shootingPractice7=new ShootingPractice7(player,null,master);
		}
		
	}

}
