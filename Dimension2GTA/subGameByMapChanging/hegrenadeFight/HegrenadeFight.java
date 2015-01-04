package subGameByMapChanging.hegrenadeFight;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;

import basicConstruction.*;
import gameDisplayProcessor.*;
import subGameByMapChangingSuper.*;
import utilities.MyPoint;
import weapon.*;

public class HegrenadeFight extends SubGameByMapChanging
{

	private int AIAim=1;
	private int AIStuckTimes=0;
	private int zoneRadius=10;
	private boolean isSetDirectionAllowed=false;
	private boolean isInZone=false;
	private int inWhitchZone=0;
	private MyPoint[] AIPoints=new MyPoint[9];
	
	public HegrenadeFight()
	{
		super(null,null,null);
		
	}
	public HegrenadeFight(Player player,Human opponent,MainGameWindow master)
	{
		super(player,opponent,master);
		
		AIPoints[0]=new MyPoint(81,57);
		AIPoints[1]=new MyPoint(257,56);
		AIPoints[2]=new MyPoint(439,53);
		AIPoints[3]=new MyPoint(444,196);
		AIPoints[4]=new MyPoint(440,353);
		AIPoints[5]=new MyPoint(246,349);
		AIPoints[6]=new MyPoint(63,345);
		AIPoints[7]=new MyPoint(76,200);
		AIPoints[8]=new MyPoint(250,197);
	}
	
	public MapContainer createLandMapContainer()
	{
		MapContainer landMapContainer=new MapContainer();
		landMapContainer.setTotalSize(new Dimension(500,400));
		
		FloorSolid floor=new FloorSolid(new ImageIcon("pic/SubGameByMapChanging/defaultMap/background2.jpg"),
				new MyPoint(0,0));
		landMapContainer.addSolid(floor);
		
		BuildingSolid wallUp=new BuildingSolid(new ImageIcon("pic/SubGameByMapChanging/defaultMap/wallUp.jpg"),
				new MyPoint(0,0));
		landMapContainer.addSolid(wallUp);
		
		BuildingSolid wallDown=new BuildingSolid(new ImageIcon("pic/SubGameByMapChanging/defaultMap/wallDown.jpg"),
				new MyPoint(0,383));
		landMapContainer.addSolid(wallDown);
		
		BuildingSolid wallLeft=new BuildingSolid(new ImageIcon("pic/SubGameByMapChanging/defaultMap/wallLeft.jpg"),
				new MyPoint(0,0));
		landMapContainer.addSolid(wallLeft);
		
		BuildingSolid wallRight=new BuildingSolid(new ImageIcon("pic/SubGameByMapChanging/defaultMap/wallRight.jpg"),
				new MyPoint(476,0));
		landMapContainer.addSolid(wallRight);
		
		
		landMapContainer.setMapID(998);
		//设定出生点
		setOpponentBirthPoint(new MyPoint(141,27));
		setPlayerBirthPoint(new MyPoint(333,341));
		
		return landMapContainer;
	}
	
	public void editWeaponList()
	{
		getPlayer().setNumOfHegrenade(100);
		getOpponent().setNumOfHegrenade(100);
	    getOpponent().setCurrentWeapon(2);
	 
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==getAITimer())
		{
			//设置AI移动方向
			isInZone=false;
			for(int i=0;i<AIPoints.length;i++)
			{
				if(getOpponent().getDoubleCenterPoint().distance(AIPoints[i])<zoneRadius)
				{
					isInZone=true;
					inWhitchZone=i+1;
				}
			}
			
			if(!isInZone)
			{
				isSetDirectionAllowed=true;
			}
			
			if(isSetDirectionAllowed&&isInZone)
			{
				//调整目标点
				switch(inWhitchZone)
				{
				    case 1:
				    {
				    	int rand=(int)(Math.random()*2);
				    	if(0==rand)
				    	{
				    		AIAim=2;
				    	}
				    	else
				    	{
				    		AIAim=8;
				    	}
				    	break;
					
				    }
				    case 2:
				    {
				    	int rand=(int)(Math.random()*3);
				    	if(0==rand)
				    	{
				    		AIAim=1;
				    	}
				    	if(1==rand)
				    	{
				    		AIAim=3;
				    	}
				    	if(2==rand)
				    	{
				    		AIAim=9;
				    	}
				    	break;
				    	
				    }
				    case 3:
				    {
				    	int rand=(int)(Math.random()*2);
				    	if(0==rand)
				    	{
				    		AIAim=2;
				    	}
				    	else
				    	{
				    		AIAim=4;
				    	}
				    	break;
				    }
				    case 4:
				    {
				    	int rand=(int)(Math.random()*3);
				    	if(0==rand)
				    	{
				    		AIAim=3;
				    	}
				    	if(1==rand)
				    	{
				    		AIAim=5;
				    	}
				    	if(2==rand)
				    	{
				    		AIAim=9;
				    	}
				    	break;
					
				    }
				    case 5:
				    {
				    	int rand=(int)(Math.random()*2);
				    	if(0==rand)
				    	{
				    		AIAim=4;
				    	}
				    	else
				    	{
				    		AIAim=6;
				    	}
				    	break;
				    }
				    case 6:
				    {
				    	int rand=(int)(Math.random()*3);
				    	if(0==rand)
				    	{
				    		AIAim=5;
				    	}
				    	if(1==rand)
				    	{
				    		AIAim=7;
				    	}
				    	if(2==rand)
				    	{
				    		AIAim=9;
				    	}
				    	break;
				    }
				    case 7:
				    {
				    	int rand=(int)(Math.random()*2);
				    	if(0==rand)
				    	{
				    		AIAim=6;
				    	}
				    	else
				    	{
				    		AIAim=8;
				    	}
				    	break;
					
				    }
				    case 8:
				    {
				    	int rand=(int)(Math.random()*3);
				    	if(0==rand)
				    	{
				    		AIAim=1;
				    	}
				    	if(1==rand)
				    	{
				    		AIAim=7;
				    	}
				    	if(2==rand)
				    	{
				    		AIAim=9;
				    	}
				    	break;
				    }
				    case 9:
				    {
				    	int rand=(int)(Math.random()*4);
				    	if(0==rand)
				    	{
				    		AIAim=2;
				    	}
				    	if(1==rand)
				    	{
				    		AIAim=4;
				    	}
				    	if(2==rand)
				    	{
				    		AIAim=6;
				    	}
				    	if(3==rand)
				    	{
				    		AIAim=8;
				    	}
				    	break;
				    }
				    default :break;
				}
				//setDirection not allowed
				isSetDirectionAllowed=false;
			}
			
			//调整加速度和速度
			if(getOpponent().getVelocity().getAbsoluteValue()<1e-3)
			{
				AIStuckTimes++;
			}
			else
			{
				AIStuckTimes=0;
			}
			
			if(10==AIStuckTimes)
			{
				int closestPoint=0;
				for(int i=0;i<AIPoints.length;i++)
				{
					if(AIPoints[i].distance(getOpponent().getDoubleCenterPoint())<
							AIPoints[closestPoint].distance(getOpponent().getDoubleCenterPoint()))
					{
						closestPoint=i;
					}
				}
				
				getOpponent().setLocation(new MyPoint(AIPoints[closestPoint].x-Human.radius,
						AIPoints[closestPoint].y-Human.radius));
			}
			
			double arc=VectorClass.arcOfPoints(getOpponent().getDoubleCenterPoint(),AIPoints[AIAim-1]);
			getOpponent().setVelocity(new VectorClass(getOpponent().getSpringMaxSpeed(),arc));
			
			//调整其他
			getOpponent().setFaceToArc
			(VectorClass.arcOfPoints(getOpponent().getDoubleCenterPoint(),
					getPlayer().getDoubleCenterPoint()));
			
			if(getOpponent().getDoubleCenterPoint().distance(getPlayer().getDoubleCenterPoint())<=500)
			{
				getOpponent().setAttackRequested(true);
			}
			else
			{
				getOpponent().setAttackRequested(false);
			}
			if(!getPlayer().isAlive())
			{
				winByOpponent();
				endOfGame();
			}
			else
			{
				if(!getOpponent().isAlive())
				{
					winByPlayer();
					endOfGame();
				}
			}
		}
		
	}
	
	public void winByOpponent() {
		// TODO Auto-generated method stub
		
	}

	
	public void winByPlayer() {
		getPlayer().setMoney(getPlayer().getMoney()+60+(int)(60*Math.random()));
		
		
	}
	

}
