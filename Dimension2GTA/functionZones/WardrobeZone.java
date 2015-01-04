package functionZones;

import java.awt.*;

import javax.swing.*;

import gameDisplayProcessor.*;
import basicConstruction.*;

import subGame.wardrobe.*;
import utilities.MyPoint;

public class WardrobeZone extends FunctionZone 
{
	
	public WardrobeZone(MyPoint location)
	{
		super(new ImageIcon("pic/showPic/picOnMap/wardrobe.jpg"),location,new Dimension(100,100),true,true);
	    this.setSurfaceImage(new ImageIcon("pic/showPic/picOnMap/wardrobe.jpg"));
	    
 	}

	@Override
	public void functionPerformed(Human whoTriggers, MainGameWindow master)
	{
		if(whoTriggers instanceof Player)
		{
			Wardrobe wardrobe=new Wardrobe((Player)whoTriggers,null,master); 
		}

	}

}
