package functionZones;

import basicConstruction.*;
import gameDisplayProcessor.*;

import java.awt.*;

import javax.swing.*;

import utilities.MyPoint;


//这个类模拟地雷
public class Mine extends FunctionZone 
{
	
	private int hurtValue;
	
	public Mine(int hurtValue,MyPoint location)
	{
		super(new ImageIcon("pic/default1.jpg"),location,new Dimension(10,10),true,false);
		
		this.setSurfaceImage(new ImageIcon("pic/showPic/picOnMap/mine.jpg"));
	    this.hurtValue=hurtValue;
	}

	@Override
	public void functionPerformed(Human whoTriggers, MainGameWindow master) 
	{
		int killRadius=hurtValue*2;
		
		//创建爆炸的火花
	    Rectangle explodeRect=new Rectangle();  //得到杀伤区的矩形
	    explodeRect.setSize(2*killRadius,2*killRadius);
	    explodeRect.setLocation(new Point((int)(getDoubleCenterPoint().x)-killRadius,
	    		(int)(getDoubleCenterPoint().y)-killRadius));
	    
	    MyPoint sparkLocation=new MyPoint(explodeRect.getLocation());
	   	    
	    for(int n=0;n<((hurtValue/2>50)?50:(hurtValue/2));n++)
	    {
	    	double l=Math.random()*killRadius*((Math.random()<0.5)?1:(-1));
	        double h=Math.random()*killRadius*((Math.random()<0.5)?1:(-1));
	    	
	        sparkLocation=new MyPoint(getDoubleCenterPoint().x+l,getDoubleCenterPoint().y+h);
	    		
	    	master.getLandMapContainer().addSpark1(sparkLocation);
	    	
	      
	    }
		//处理被炸伤的solids
		
	    Solid[] damagedSolids=master.getLandMapContainer().SolidsInRectangle(explodeRect);
	    for(int j=0;j<damagedSolids.length;j++) 
	    {
	    	if((damagedSolids[j]!=null)&&(damagedSolids[j]!=this)&&(!(damagedSolids[j] instanceof Building))) 
	    	{    		
	    		damagedSolids[j].hurtByMine(getDoubleCenterPoint(),hurtValue);
	    		
	    	}
	    }
	    
	    //自删除
	    master.getLandMapContainer().removeSolid(this);
		
	}
	
	

}
