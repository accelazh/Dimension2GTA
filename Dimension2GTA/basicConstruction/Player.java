package basicConstruction;

import gameDisplayProcessor.ControlSetting;
import gameDisplayProcessor.MainGameWindow;

import java.awt.*;
import subGameByMapChanging.gunFight.*;
import subGameByMapChanging.hegrenadeFight.*;
import subGameByMapChangingSuper.*;
import subGame.longFan.*;
import subGame.dancingFight.*;
import subGame.westGun.*;
import duel.*;

public class Player extends Human
{
	//for debug
	private boolean debug13=ControlSetting.debug13;
	
	private Point mouseLocation=new Point(0,0);	
	
	public void setMouseLocation(Point p)
	{
		this.mouseLocation.x=p.x;
		this.mouseLocation.y=p.y;
	}
	public Point getMouseLocation()
	{
		return this.mouseLocation;
	}
	//========date field=======
	SubGameByMapChanging subGameByMapChanging;
	
	//for foot moving condition
	
	
	
	
	
	
	//=======methods=======
	//constructors
	public Player()
	{
		setSolidName("Zhao Yilong");
		setFootMovingCondition(RUN);
		setClothes(getClothesList()[0]);
		
	}
	
	
	public void switchFootMovingCondition()
	{
	   if(WALK==getFootMovingCondition())
	   {
		   setFootMovingCondition(RUN);
	   }
	   else
	   {
		   if(RUN==getFootMovingCondition())
	       {
		       setFootMovingCondition(SPRING);
	       }
		   else
		   {
	   
	           if(SPRING==getFootMovingCondition())
	           {
		           setFootMovingCondition(WALK);
	           }
		   }
	   }
	}
	
	//这个方法负责和某人决斗的问题,决斗只能是player的行为
	public void duelWith(Human aimHuman, MainGameWindow master)
	{
		
		DuelManager duelManager=new DuelManager(this,aimHuman,master);
		
		
		
	}
	public SubGameByMapChanging getSubGameByMapChanging() {
		return subGameByMapChanging;
	}
	public void setSubGameByMapChanging(SubGameByMapChanging subGameByMapChanging) {
		this.subGameByMapChanging = subGameByMapChanging;
	}

	

	
	
	
}
