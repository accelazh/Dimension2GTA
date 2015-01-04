package subGameByMapChangingSuper;

import basicConstruction.*;
import gameDisplayProcessor.*;
import utilities.ImageViewer;
import utilities.MyPoint;
import weapon.*;
import nameConstants.*;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

public abstract class SubGameByMapChanging implements ActionListener
{
	//for debug
	private boolean debug17=ControlSetting.debug17;
	//data field
	private MapContainer newLandMapContainer;
	private MapContainer origLandMapContainer;
	
	private Player player;
	private MyPoint playerBirthPoint;
	private MyPoint playerOrigLocation;
	private Weapon[] playerOrigWeaponList;
	private Weapon[] playerNewWeaponList;
	private Vest playerOrigVest;
	
	
	private Human opponent;
	private MyPoint opponentBirthPoint;
	private MyPoint opponentOrigLocation;
	private Weapon[] opponentOrigWeaponList;
	private Weapon[] opponentNewWeaponList;
	
	private Vest opponentOrigVest;
	
	private MainGameWindow master;
	
	//选择枪的种类
	private int itemName=NameConstants.WEAPON_GUN_CHEAP_GUN;
	
	//AI区
	private Timer AITimer;
	private final int AI_TIMER_INTERVAL=100; 
	private double AISpeedPlus=0.8+Math.random()*0.7;  //电脑一动速度加成
	
	
	private int AIAim=1;
	private int AIStuckTimes=0;
	private int zoneRadius=25;
	private boolean isSetDirectionAllowed=false;
	private boolean isInZone=false;
	private int inWhitchZone=0;
	private MyPoint[] AIPoints=new MyPoint[9];
	
	//methods
	
	public SubGameByMapChanging(Player player,Human opponent,MainGameWindow master)
	{
		this.player=player;
		this.opponent=opponent;
		this.master=master;
		
		this.AITimer=new Timer(AI_TIMER_INTERVAL,this);
		AITimer.start();
			
		initialization();
	}
	//用于各种枪械战斗的构造方法
	public SubGameByMapChanging(Player player,Human opponent,MainGameWindow master,int itemName)
	{
		this.player=player;
		this.opponent=opponent;
		this.master=master;
		this.itemName=itemName;
		
		this.AITimer=new Timer(AI_TIMER_INTERVAL,this);
		AITimer.start();
			
		initialization();
	}
	
	//构造地图的方法，允许子类覆盖
	//该方法必须同时设定数据域中的playerBirthPoint和opponentBirthPoint
	public MapContainer createLandMapContainer()
	{
		MapContainer landMapContainer=new MapContainer();
		landMapContainer.setTotalSize(new Dimension(500,400));
		
		FloorSolid floor=new FloorSolid(new ImageIcon("pic/SubGameByMapChanging/defaultMap/background.jpg"),
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
		
		
		
		PillarBuilding pillar1=new PillarBuilding(new ImageIcon("pic/SubGameByMapChanging/defaultMap/pillar.jpg"),
				new MyPoint(117,77));
		landMapContainer.addSolid(pillar1);
		
		PillarBuilding pillar2=new PillarBuilding(new ImageIcon("pic/SubGameByMapChanging/defaultMap/pillar.jpg"),
				new MyPoint(307,81));
		landMapContainer.addSolid(pillar2);
		
		PillarBuilding pillar3=new PillarBuilding(new ImageIcon("pic/SubGameByMapChanging/defaultMap/pillar.jpg"),
				new MyPoint(114,232));
		landMapContainer.addSolid(pillar3);
		
		PillarBuilding pillar4=new PillarBuilding(new ImageIcon("pic/SubGameByMapChanging/defaultMap/pillar.jpg"),
				new MyPoint(303,235));
		landMapContainer.addSolid(pillar4);
		
		landMapContainer.setMapID(999);
		//设定出生点
		opponentBirthPoint=new MyPoint(141,27);
		playerBirthPoint=new MyPoint(333,341);
		
		//设置AI路径点
		
		AIPoints[0]=new MyPoint(81,57);
		AIPoints[1]=new MyPoint(257,56);
		AIPoints[2]=new MyPoint(439,53);
		AIPoints[3]=new MyPoint(444,196);
		AIPoints[4]=new MyPoint(440,353);
		AIPoints[5]=new MyPoint(246,349);
		AIPoints[6]=new MyPoint(63,345);
		AIPoints[7]=new MyPoint(76,200);
		AIPoints[8]=new MyPoint(250,197);
		
		return landMapContainer;
	}

	//这个方法备份player和opponent的weaponList，以便恢复时用
	//并且为player和opponent装上新的空的weaponList，在这个地图中使用
	final public void copyWeaponList()
	{
		playerOrigWeaponList=player.getWeaponList();
		opponentOrigWeaponList=opponent.getWeaponList();
		
		playerNewWeaponList=new Weapon[5];
		for(int i=0;i<playerNewWeaponList.length;i++)
		{
			playerNewWeaponList[i]=null;
		}
		playerNewWeaponList[0]=new Fists(player);
		((Fists)playerNewWeaponList[0]).copyFistsSkillToMe
		    (((Fists)playerOrigWeaponList[0]));
				
		player.setCurrentWeapon(0);
		playerNewWeaponList[2]=new HegrenadeBell(0);
		
		player.setWeaponList(playerNewWeaponList);

		opponentNewWeaponList=new Weapon[5];
		for(int i=0;i<opponentNewWeaponList.length;i++)
		{
			opponentNewWeaponList[i]=null;
		}
		opponentNewWeaponList[0]=new Fists(opponent);
		((Fists)opponentNewWeaponList[0]).copyFistsSkillToMe
	        (((Fists)opponentOrigWeaponList[0]));
	
				
		opponent.setCurrentWeapon(0);
		opponentNewWeaponList[2]=new HegrenadeBell(0);
		
		opponent.setWeaponList(opponentNewWeaponList);

	}
	
	final public void copyVest()
	{
		playerOrigVest=player.getVest();
		opponentOrigVest=opponent.getVest();
	}

	//在endOfGame中调用，恢复WeaponList,允许子类覆盖,
	public void retriveWeaponList()
	{
		player.setWeaponList(playerOrigWeaponList);
		player.setCurrentWeapon(0);
		
		opponent.setWeaponList(opponentOrigWeaponList);
		opponent.setCurrentWeapon(0);
		
	}
	
	public void retriveVest()
	{
		player.setVest(playerOrigVest);
		opponent.setVest(opponentOrigVest);
	}

	//在initialization中调用,允许子类覆盖
	public void editWeaponList()
	{
		
		switch (itemName) {
		case NameConstants.WEAPON_GUN_AK47: {
			(player.getWeaponList())[1]=new AK47(player);
			(opponent.getWeaponList())[1]=new AK47(opponent);
			break;
		}
		case NameConstants.WEAPON_GUN_AUG: {
			(player.getWeaponList())[1]=new AUG(player);
			(opponent.getWeaponList())[1]=new AUG(opponent);
			break;
		}
		case NameConstants.WEAPON_GUN_AWP: {
			(player.getWeaponList())[1]=new AWP(player);
			(opponent.getWeaponList())[1]=new AWP(opponent);
			break;
		}
		case NameConstants.WEAPON_GUN_DESERT_EAGLE: {
			(player.getWeaponList())[1]=new DesertEagle(player);
			(opponent.getWeaponList())[1]=new DesertEagle(opponent);
			break;
		}
		case NameConstants.WEAPON_GUN_M249: {
			(player.getWeaponList())[1]=new M249(player);
			(opponent.getWeaponList())[1]=new M249(opponent);
			break;
		}
		case NameConstants.WEAPON_GUN_M4: {
			(player.getWeaponList())[1]=new M4(player);
			(opponent.getWeaponList())[1]=new M4(opponent);
			break;
		}
		case NameConstants.WEAPON_GUN_MP5: {
			(player.getWeaponList())[1]=new MP5(player);
			(opponent.getWeaponList())[1]=new MP5(opponent);
			break;
		}
		case NameConstants.WEAPON_GUN_SIG552: {
			(player.getWeaponList())[1]=new SIG552(player);
			(opponent.getWeaponList())[1]=new SIG552(opponent);
			break;
		}
		case NameConstants.WEAPON_GUN_CHEAP_GUN: {
			(player.getWeaponList())[1]=new CheapGun(player);
			(opponent.getWeaponList())[1]=new CheapGun(opponent);
			break;
		}
		
		default:
		{
			(player.getWeaponList())[1]=new CheapGun(player);
			(opponent.getWeaponList())[1]=new CheapGun(opponent);
			break;
		}
	    }
		
			    
		
		
		opponent.setCurrentWeapon(1);
		player.setCurrentWeapon(1);
			
	}
	public void editVest()
	{
		player.setVest(new Vest(0.5,300));
		opponent.setVest(new Vest(0.5,300));
	}

	
	
	final private void initialization()
	{
		origLandMapContainer=master.getLandMapContainer();
		newLandMapContainer=createLandMapContainer();
		
		playerOrigLocation=new MyPoint(player.getLocation().x,
				player.getLocation().y);
		opponentOrigLocation=new MyPoint(opponent.getLocation().x,
				opponent.getLocation().y);
		
		copyWeaponList();
		copyVest();
		
		editWeaponList();
		editVest();
		
		master.getScreenDisplayPanel().setDrawBackgroundPic(true);
		master.switchMapContainer(newLandMapContainer,playerBirthPoint,
				opponent,opponentBirthPoint);
		
		if(debug17)
		{
			System.out.println("initialization finished");
		}
	
		master.getPlayer().setUseRequested(false);
		master.getPlayer().setMoveUpRequested(false);
		master.getPlayer().setMoveDownRequested(false);
		master.getPlayer().setMoveLeftRequested(false);
		master.getPlayer().setMoveRightRequested(false);
	}
	
	final public void endOfGame()
	{
		retriveWeaponList();
		retriveVest();
		
		master.getScreenDisplayPanel().setDrawBackgroundPic(false);
		master.switchMapContainer(origLandMapContainer,playerOrigLocation,
				opponent,opponentOrigLocation);
		AITimer.stop();
		
		opponent.setMoveDownRequested(false);
		opponent.setMoveLeftRequested(false);
		opponent.setMoveRightRequested(false);
		opponent.setMoveUpRequested(false);
		opponent.setAttackRequested(false);
		
		master.getPlayer().setUseRequested(false);
		master.getPlayer().setMoveUpRequested(false);
		master.getPlayer().setMoveDownRequested(false);
		master.getPlayer().setMoveLeftRequested(false);
		master.getPlayer().setMoveRightRequested(false);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==AITimer)
		{
			//设置AI移动方向
			isInZone=false;
			for(int i=0;i<AIPoints.length;i++)
			{
				if(opponent.getDoubleCenterPoint().distance(AIPoints[i])<zoneRadius)
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
			if(opponent.getVelocity().getAbsoluteValue()<1e-3)
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
					if(AIPoints[i].distance(opponent.getDoubleCenterPoint())<
							AIPoints[closestPoint].distance(opponent.getDoubleCenterPoint()))
					{
						closestPoint=i;
					}
				}
				
				opponent.setLocation(new MyPoint(AIPoints[closestPoint].x-Human.radius,
						AIPoints[closestPoint].y-Human.radius));
			}
			
			double arc=VectorClass.arcOfPoints(opponent.getDoubleCenterPoint(),AIPoints[AIAim-1]);
			opponent.setVelocity(new VectorClass(opponent.getSpringMaxSpeed()*AISpeedPlus,arc));
			
			//调整其他
			opponent.setFaceToArc
			(VectorClass.arcOfPoints(opponent.getDoubleCenterPoint(),
					player.getDoubleCenterPoint()));
			
			if(opponent.getDoubleCenterPoint().distance(player.getDoubleCenterPoint())<=200)
			{
				opponent.setAttackRequested(true);
			}
			else
			{
				opponent.setAttackRequested(false);
			}
			if(!player.isAlive())
			{
				winByOpponent();
				endOfGame();
			}
			else
			{
				if(!opponent.isAlive())
				{
					winByPlayer();
					endOfGame();
				}
			}
		}
		
	}
	
	//这个两个方法提醒子类要实现player和opponent的输赢处理办法
	//如果子类并不是一个输赢类的游戏，那么这两个方法为空即可
	//这两个方法会被自动调用
	public abstract void winByPlayer();
	
	public abstract void winByOpponent();
	
	
	public Player getPlayer() {
		return player;
	}

	public Human getOpponent() {
		return opponent;
	}
		
	public Weapon[] getPlayerOrigWeaponList() {
		return playerOrigWeaponList;
	}

	public Weapon[] getPlayerNewWeaponList() {
		return playerNewWeaponList;
	}

	public Weapon[] getOpponentOrigWeaponList() {
		return opponentOrigWeaponList;
	}

	public Weapon[] getOpponentNewWeaponList() {
		return opponentNewWeaponList;
	}

	
	public void setPlayerBirthPoint(MyPoint playerBirthPoint) {
		this.playerBirthPoint = playerBirthPoint;
	}

	
	public void setOpponentBirthPoint(MyPoint opponentBirthPoint) {
		this.opponentBirthPoint = opponentBirthPoint;
	}

	
		
	public Timer getAITimer() {
		return AITimer;
	}
	
		
	
}
