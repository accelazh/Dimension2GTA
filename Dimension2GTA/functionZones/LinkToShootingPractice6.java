package functionZones;

import gameDisplayProcessor.MainGameWindow;

import java.awt.*;

import javax.swing.*;

import basicConstruction.*;

import subGame.shootingPractice.*;
import utilities.MyPoint;

public class LinkToShootingPractice6 extends FunctionZone
{
	
	
	

	public LinkToShootingPractice6(MyPoint location,Dimension totalSize)
	{
		super(new ImageIcon("pic/default1.jpg"),location,totalSize,false,true);
		
	}
	
	@Override
	public void functionPerformed(Human whoTriggers, MainGameWindow master)
	{
		if(whoTriggers instanceof Player)
		{
			Player player=(Player)whoTriggers;
			ShootingPractice6 shootingPractice6=new ShootingPractice6(player,null,master);
		}
		
	}

}
