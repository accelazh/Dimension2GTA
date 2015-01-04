package basicConstruction;

import java.awt.*;
import javax.swing.*;

import gameDisplayProcessor.*;
import utilities.MyPoint;
import weapon.*;
import clothes.*;
import nameConstants.*;
import weapon.*;

public class Human extends Creature
{
	
	//for debug
	private static boolean debug7=ControlSetting.debug7;
	private static boolean debug11=ControlSetting.debug11;
	private static boolean debug19=ControlSetting.debug19;
	
	//========private date field=========
	public static final int radius=10; //the picture of human is a rectangle,but sometimes regarded as a circle
	private boolean isAlive;
	
	
	//for moving
	private boolean moveUpRequested;
	private boolean moveDownRequested;
	private boolean moveLeftRequested;
	private boolean moveRightRequested;
	private boolean useRequested;
	//for foot moving condition
	final public static int WALK=0;
	final public static int RUN=1;
	final public static int SPRING=2; //speed running
	private int footMovingCondition; //to use the three constants above
	
	//人物面对的方向
	private double faceToArc;
	
	//personal skills field
		
   
	
	private double walkMaxSpeed;
	private double runMaxSpeed;
	
	private long money;
	//weapon
	private boolean attackRequested;
	
	//not allowed : weaponList==null.
	//weaponList[0] is 徒手攻击
	//weaponList[2] is HegrenadeBell.
	//weaponList[1] is 火箭筒rocketLauncher
	//这三项不允许修改
	private Weapon[] weaponList;   
	
	private int currentWeapon;  //指从头数起第几个武器
	
	//防弹衣vest
	private Vest vest;
	
	//服装设定
	private Clothes[] clothesList;
	private int currentClothes;
	
	//体能和技能设定
	
	//fat
	private double fat=100;
		
	//跑步速度
	public final static  int topSpringSpeedSkill=1000;
	private int springSpeedSkill; //反映跑步的速度，初始为零，计划顶级点数为1000，但到顶后还可以继续练
	
	public final static double springMaxSpeedStatic=260*3;   //这个速度是不考虑技能的基数,而springMaxSpeed表示考虑技能等级的猛冲速度
	
	private long totalSpringTime=0;  //用godTimerInterval计算的
	private long totalRunTime=0;   //用godTimerInterval计算的
	//耐力
	public final static int topStaminaSkill=1000;
	private int staminaSkill=0;
	
	public final static double staminaStatic=5*1000/ControlSetting.godTimerInterval;  //用以得到最大值
	
	private double stamina=getMaxStamina();	
	//生命值
	public final static int topHealthPointSkill=500; 
    private int healthPointSkill;	
	
    public final static int healthPointStatic=100;	
	//Fists攻击力和攻击频率
        //在Fist类中已经设定

    //各种枪械的技能设定
    private GunSkillPack gunSkillPack=new GunSkillPack();    //反应所有Gun的使用技能，计划顶级点数为1000，但到顶后还可以继续练(对于player)
	
	//========methods========

		//constructors
	public Human()
	{
		walkMaxSpeed=80;
		runMaxSpeed=150;
		footMovingCondition=WALK;
		
		moveUpRequested=false;
		moveDownRequested=false;
		moveLeftRequested=false;
		moveRightRequested=false;
		useRequested=false;
		setFootMovingCondition(WALK);
		
		faceToArc=0;
		
		//武器专栏
		attackRequested=false;
		this.weaponList=new Weapon[5];
		for(int i=0;i<weaponList.length;i++)
		{
			weaponList[i]=null;
		}
		weaponList[0]=new Fists(this);
				
		currentWeapon=0;
		
		
		weaponList[2]=new HegrenadeBell(0); //HegrenadeBell是human挂手雷的袋子,用它来装多个手雷,因此human的weaponList上没有Hegrenade的实例，只有HegrenadeBell的实例
		
		//vest
		vest=null;
		
		//personal skill
		springSpeedSkill=0;
		healthPointSkill=0;
		money=1000;
		setHealthPoint(getMaxHealthPoint());
		//person state
		isAlive=true;
		
		//clothes
		clothesList=new Clothes[10];
		for(int i=0;i<clothesList.length;i++)
		{
			clothesList[i]=null;
		}
		clothesList[0]=new Clothes(NameConstants.CLOTHES_PLAYER_STANDARD);
		clothesList[1]=new Clothes(NameConstants.CLOTHES_NPC_STANDARD);
		
		setClothes(clothesList[1]);
	}
	
	//gets and sets

	public double getRunMaxSpeed() {
		return runMaxSpeed;
	}

	public void setRunMaxSpeed(double runMaxSpeed) {
		this.runMaxSpeed = runMaxSpeed;
	}

	public int getMaxHealthPoint()
	{
		if(healthPointSkill<=topHealthPointSkill)
		{
			return (int)((double)healthPointSkill*1.0/topHealthPointSkill*
			    healthPointStatic*5+healthPointStatic);
		}
		else
		{
			return (int)((double)(healthPointSkill-topHealthPointSkill)*1.0/topHealthPointSkill*
				    healthPointStatic*3+6*healthPointStatic);
		}
	}
	
	public void setHealthPoint(int healthPoint)
	{
		super.setHealthPoint(healthPoint);
		if(super.getHealthPoint()<0)
		{
			super.setHealthPoint(0);
		}
		if(super.getHealthPoint()>getMaxHealthPoint())
		{
			super.setHealthPoint(getMaxHealthPoint());
		}
	}
	
	public double getMaxStamina()
	{
		if(staminaSkill<=topStaminaSkill)
		{
			return staminaSkill*1.0/topStaminaSkill*staminaStatic*5+
			staminaStatic;
		}
		else
		{
			return (staminaSkill-topStaminaSkill)*1.0/topStaminaSkill*staminaStatic*3+
			6*staminaStatic;
		}
	}
	
	public double getStamina()
	{
		return this.stamina;
	}
	
	public void setStamina(double newStamina)
	{
		stamina=newStamina;
		if(stamina>getMaxStamina())
		{
			stamina=getMaxStamina();
		}
		if(stamina<0)
		{
			stamina=0;
		}
	}
	
	public double getSpringMaxSpeed()
	{
		double normalSpeed=0;
		if(springSpeedSkill<=topSpringSpeedSkill)
		{
			normalSpeed= (double)springSpeedSkill*1.0/topSpringSpeedSkill*
			    springMaxSpeedStatic*2+springMaxSpeedStatic;
		}
		else
		{
			normalSpeed= (double)(springSpeedSkill-topSpringSpeedSkill)*1.0/topSpringSpeedSkill*
			springMaxSpeedStatic+3*springMaxSpeedStatic;
		}
		
		if(getFat()<10)
		{
			return walkMaxSpeed;
		}
		else
		{
			if(getFat()<200)
			{
				if(stamina>1*1000/ControlSetting.godTimerInterval)
				{
					return normalSpeed;
				}
				else
				{
					return runMaxSpeed;
				}
			}
			else
			{
				return runMaxSpeed;
			}
		}
	}
	
	public void setHealthPointSkill(int s)
	{
		this.healthPointSkill=s;
	}
	
	public int getHealthPointSkill()
	{
		return healthPointSkill;
	}

	public double getSpringMaxSpeedStatic()
	{
		return springMaxSpeedStatic;
	}

	

	public double getWalkMaxSpeed() {
		return walkMaxSpeed;
	}

	public void setWalkMaxSpeed(double walkMaxSpeed) {
		this.walkMaxSpeed = walkMaxSpeed;
	}

	public int getFootMovingCondition()
	{
		return footMovingCondition;
	}
	
	public void setFootMovingCondition(int footMovingCondition)
	{
		this.footMovingCondition =footMovingCondition;
	}
	
	public boolean getMoveUpRequested()
	{
		return moveUpRequested;
	}
	
	public boolean getMoveDownRequested()
	{
		return moveDownRequested;
	}
	public boolean getMoveLeftRequested()
	{
		return moveLeftRequested;
	}
	public boolean getMoveRightRequested()
	{
		return moveRightRequested;
	}
	
	

	
	public boolean getUseRequested() {
		return useRequested;
	}

	public void setUseRequested(boolean useRequested) {
		this.useRequested = useRequested;
	}

	public void setMoveUpRequested(boolean moveUpRequested)
	{
		this.moveUpRequested=moveUpRequested;
	}
	
	public void setMoveDownRequested(boolean moveDownRequested)
	{
		this.moveDownRequested=moveDownRequested;
	}
	public void setMoveLeftRequested(boolean moveLeftRequested)
	{
		this.moveLeftRequested=moveLeftRequested;
	}
	public void setMoveRightRequested(boolean moveRightRequested)
	{
		this.moveRightRequested=moveRightRequested;
	}
	
	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
		if(false==isAlive)
		{
			setSurfaceImage(new ImageIcon("pic/human/died.jpg"));
		}
		else
		{
			setSurfaceImage(getUsualSurfaceImage());
		}
	}
	
	
	public int getRadius()
	{
		return radius;
	}
   
	public void setAttackRequested(boolean attackRequested)
	{
		this.attackRequested =attackRequested;
	}
	
	public boolean getAttackRequested()
	{
		return attackRequested;
	}
	
	public Point getCenterPoint()  //这个Point是大地图左上角的
	{
		return new Point((int)getLocation().x+radius,(int)getLocation().y+radius );   
	}
	public MyPoint getDoubleCenterPoint()
	{
		return new MyPoint(getLocation().x+radius,getLocation().y+radius );   
	}
	
	
	
	public GunSkillPack getGunSkillPack() {
		return gunSkillPack;
	}

	public double getFaceToArc()
	{
		return faceToArc;
	}
	
	public void setFaceToArc(double arc)
	{
		this.faceToArc=arc;
	}
	
	public int getSpringSpeedSkill() {
		return springSpeedSkill;
	}


	public void setSpringSpeedSkill(int springSpeedSkill) {
		this.springSpeedSkill = springSpeedSkill;
	}
    
	
	
	
	//武器专栏
	
	public int getCurrentWeapon()
	{
		return currentWeapon;
	}
	
	public Weapon[] getWeaponList()
	{
		return weaponList;
	}
	
	public void switchCurrentWeaponForth()
	{
		int i;
		
		if(debug7)
		{
			System.out.println("====in method switchCurrentWeapon====");
			System.out.println("before switch, currentWeapon : ");
			System.out.println(weaponList[currentWeapon]);
		}
		
		for(i=currentWeapon+1;(i<weaponList.length)&&(null==weaponList[i]);i++)
			;
		
		if(debug7)
		{
			System.out.println("after searching the end of weapons : ");
			if(i>=weaponList.length)
			{
				System.out.println("i >= weaponList.length");
			}
			else
			{
				System.out.println(weaponList[i]);
			}
		}
		
		if(i>=weaponList.length)
		{
			currentWeapon=0;
		}
		else
		{
			currentWeapon=i;
		}
		
		if(debug7)
		{
			System.out.println("after switch the weapon, currentWeapon :");
			System.out.println(weaponList[currentWeapon]);
			System.out.println("\n\n");
		}
	}
	
	public void switchCurrentWeaponBack()
	{
		int i;
		
		if(debug7)
		{
			System.out.println("====in method switchCurrentWeapon====");
			System.out.println("before switch, currentWeapon : ");
			System.out.println(weaponList[currentWeapon]);
		}
		
		for(i=currentWeapon-1;(i>=0)&&(null==weaponList[i]);i--)
			;
		
		if(debug7)
		{
			System.out.println("after searching the end of weapons : ");
			if(i>=weaponList.length)
			{
				System.out.println("i >= weaponList.length");
			}
			else
			{
				System.out.println(weaponList[i]);
			}
		}
		
		if(i<0)
		{
			for(int j=0;j<weaponList.length;j++)
			{
				if(weaponList[j]!=null)
				{
				    currentWeapon=j;	
				}				
			}
		}
		else
		{
			currentWeapon=i;
		}
		
		if(debug7)
		{
			System.out.println("after switch the weapon, currentWeapon :");
			System.out.println(weaponList[currentWeapon]);
			System.out.println("\n\n");
		}
	}
	
	public void addWeapon(Weapon newWeapon)
	{
		if(debug7)
		{
			System.out.println("====in method addWeapon====");
			
		}
		
		int i;        // to search for the end of the array
		for(i=0;(i<weaponList.length)&&(weaponList[i]!=null);i++)
			;
		if(i<weaponList.length)
		{
			weaponList[i]=newWeapon;
		}
		else
		{
			Weapon[] newWeaponList=new Weapon[weaponList.length*2];
			for(int j=0;j<newWeaponList.length;j++)
			{
				newWeaponList[j]=null;
			}
			System.arraycopy(weaponList,0,newWeaponList,0,weaponList.length);
			
			newWeaponList[weaponList.length]=newWeapon;
			weaponList=newWeaponList;
		}
		
		if(debug7)
		{
			System.out.println("after method addWeapon, the weaponList: ");
			for(int k=0;k<weaponList.length ;k++)
			{
				if(null==weaponList[k])
				{
					System.out.println("weaponList["+k+"] == null");
				}
				else
				{
					System.out.println("weaponList["+k+"] : ");
					System.out.println(weaponList[k]);
				}
			}
		
		System.out.println("\n\n");
		}
	}
	
	public void hittedByBullet(Bullet bullet) 
	{
		if(debug19)
		{
			System.out.println("====in method hittedByBullet====");
			System.out.println("beginning");
			System.out.println("health: "+getHealthPoint());
			if(vest!=null)
			{
				System.out.println("Vest rate: "+vest.getRate());
			    System.out.println("Vest health: "+vest.getHealth());
			}
			else
			{
				System.out.println("vest is null");
			}
		}
		if(vest!=null)
		{
	    	if(getHealthPoint()>0)
	    	{
	         	setHealthPoint(getHealthPoint()-(int)(vest.getRate()*bullet.getKillLevel()));
	         	vest.setHealth(vest.getHealth()-(int)(vest.getRate()*bullet.getKillLevel()));
	    	}
		    if(getHealthPoint()<=0)
		    {
		    	setHealthPoint(0);
		    	setSurfaceImage(new ImageIcon("pic/human/died.jpg"));
		    	isAlive=false;
		    }
		    
		    if(vest.getHealth()<=0)
		    {
		    	vest=null;
		    }
		}
		else
		{
			if(getHealthPoint()>0)
	    	{
	         	setHealthPoint(getHealthPoint()-bullet.getKillLevel());
	         	
	    	}
		    if(getHealthPoint()<=0)
		    {
		    	setHealthPoint(0);
		    	setSurfaceImage(new ImageIcon("pic/human/died.jpg"));
		    	isAlive=false;
		    }
		}
		
		if(debug19)
		{
			System.out.println("ending");
			System.out.println("health: "+getHealthPoint());
			if(vest!=null)
			{
				System.out.println("Vest rate: "+vest.getRate());
			    System.out.println("Vest health: "+vest.getHealth());
			}
			else
			{
				System.out.println("vest is null");
			}
		    System.out.println("\n\n");
		}
	}
	
	public int getNumOfHegrenade() {
		return ((HegrenadeBell)(weaponList[2])).getNumOfHegrenade();
	}

	public void setNumOfHegrenade(int numOfHegrenade) {
		((HegrenadeBell)(weaponList[2])).setNumOfHegrenade(numOfHegrenade);
	}
	
	
	
	public void hurtByHegrenade(MyPoint explosionPoint)
	{
		
		if(debug19)
		{
			System.out.println("====in method hittedByHegrenade====");
			System.out.println("beginning");
			System.out.println("health: "+getHealthPoint());
			if(vest!=null)
			{
				System.out.println("Vest rate: "+vest.getRate());
			    System.out.println("Vest health: "+vest.getHealth());
			}
			else
			{
				System.out.println("vest is null");
			}
		}
		
		double distance=MyPoint.getDistance
		(getDoubleCenterPoint(),explosionPoint);
		distance=Math.abs(distance);
		
		if (vest != null) 
		{
			if (getHealthPoint() > 0) 
			{
				if (distance > Hegrenade.killRadius)
				{
					setHealthPoint(getHealthPoint()
							- (int) (vest.getRate() * 20));
					vest.setHealth(vest.getHealth()
							- (int) (vest.getRate() * 20));
				} 
				else
				{
					double hurt;
					hurt = -(Hegrenade.killLevel - 20) * 1.0
							/ Hegrenade.killRadius * distance
							+ Hegrenade.killLevel;
					setHealthPoint(getHealthPoint()
							- (int) (vest.getRate() * hurt));
					vest.setHealth(vest.getHealth()
							- (int) (vest.getRate() * hurt));
				}
			}
			
			if(vest.getHealth()<=0)
			{
				vest=null;
			}
		}
		else
		{
			if (getHealthPoint() > 0) 
			{
				if (distance > Hegrenade.killRadius)
				{
					setHealthPoint(getHealthPoint()
							-  20);
				} 
				else
				{
					double hurt;
					hurt = -(Hegrenade.killLevel - 20) * 1.0
							/ Hegrenade.killRadius * distance
							+ Hegrenade.killLevel;
					setHealthPoint(getHealthPoint()	- (int) hurt);
				
				}
			}
		}
		
		if(getHealthPoint()<=0)
		{
			setHealthPoint(0);
			setSurfaceImage(new ImageIcon("pic/human/died.jpg"));
			isAlive=false;
		}
		
		//调整速度
		double arc=VectorClass.arcOfPoints(explosionPoint, getDoubleCenterPoint());
		VectorClass plusVelocity=new VectorClass(40000.0/distance,arc);
		
		setVelocity(getVelocity().addition(plusVelocity));
		
		if(debug19)
		{
			System.out.println("ending");
			System.out.println("health: "+getHealthPoint());
			if(vest!=null)
			{
				System.out.println("Vest rate: "+vest.getRate());
			    System.out.println("Vest health: "+vest.getHealth());
			}
			else
			{
				System.out.println("vest is null");
			}
		}
	}
	
	public Hegrenade throwHegrenade()
	{
		if(getNumOfHegrenade()>0)
		{
			if(((HegrenadeBell)(weaponList[2])).isUseable())
			{
		    	setNumOfHegrenade(getNumOfHegrenade()-1);
			
		    	//这里手雷的发射点的设计是为了避免手雷先和human相撞，影响运行方向
		    	MyPoint humanCenter=getDoubleCenterPoint();
		    	MyPoint preparedHeThrowPoint=new MyPoint(humanCenter.x+radius*1.5*Math.cos(faceToArc),
		    			humanCenter.y-radius*1.5*Math.sin(faceToArc));
		    	MyPoint HeLocation=new MyPoint(preparedHeThrowPoint.x-Hegrenade.hegrenadeSize/2,
		    			preparedHeThrowPoint.y-Hegrenade.hegrenadeSize/2);
		    	
		    	VectorClass HeVelocity;  //velocity of the grenade
		    	VectorClass HeStillVelocity;  //人静止的时候手雷应有的速度
		    	HeStillVelocity=new VectorClass(springMaxSpeedStatic*1.5,faceToArc);
		    	HeVelocity=HeStillVelocity.addition(getVelocity());
			
		    	((HegrenadeBell)(weaponList[2])).setUseableFalse();
		    	
		    	if(debug11)
		    	{
		    		System.out.println("====in method throwHegrenade====");
		    		System.out.println("HeVelocity : ");
		    		System.out.println(HeVelocity);
		    		
		    	}
		    	
			    return new Hegrenade(HeLocation,HeVelocity);
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
		
	}
	
	public Rocket launchRocket()
	{
		RocketLauncher rocketLauncher=null;
		for(int i=0;i<weaponList.length;i++)
		{
			if(weaponList[i] instanceof RocketLauncher)
			{
				rocketLauncher=(RocketLauncher)(weaponList[i]);
			}
		}
		
		if(rocketLauncher!=null)
		{
			
		
		if(rocketLauncher.getNumOfRocket()>0)
		{
			if(rocketLauncher.isUseable())
			{
				rocketLauncher.setNumOfRocket(rocketLauncher.getNumOfRocket()-1);
			
		    	//这里火箭弹的发射点的设计是为了避免火箭弹先和human相撞，影响运行方向
		    	MyPoint humanCenter=getDoubleCenterPoint();
		    	MyPoint preparedHeThrowPoint=new MyPoint(humanCenter.x+radius*2*Math.cos(faceToArc),
		    			humanCenter.y-radius*2*Math.sin(faceToArc));
		    	MyPoint RocketLocation=new MyPoint(preparedHeThrowPoint.x-Rocket.rocketSize/2,
		    			preparedHeThrowPoint.y-Rocket.rocketSize/2);
		    	
		    	VectorClass RocketVelocity=null;  //velocity of the rocket
		    	VectorClass RocketStillVelocity=null;  //人静止的时候手雷应有的速度
		    	RocketStillVelocity=new VectorClass(0,faceToArc);
		    	RocketVelocity=RocketStillVelocity.addition(getVelocity());
			
		    	rocketLauncher.setUseableFalse();
		    	
		    	
		    	
			    return new Rocket(RocketLocation,RocketVelocity,faceToArc);
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
		}
		else
		{
			return null;
		}
	}
	
	public void hittedByFists(Fists fists) 
	{
		if(getHealthPoint()>0)
		{
	     	setHealthPoint(getHealthPoint()-fists.getAttackStrength());
		}
		if(getHealthPoint()<=0&&isAlive)
		{
			setSurfaceImage(new ImageIcon("pic/human/died.jpg"));
			isAlive=false;
		}
		
		VectorClass attackVelocity=new VectorClass
		(fists.getAttackStrength()*4,fists.getArc());
		setVelocity(getVelocity().addition(attackVelocity));
		
	}
	
	public void hurtByValue(int value)
	{
		if(getHealthPoint()>0)
		{
	     	setHealthPoint(getHealthPoint()-value);
		}
		if(getHealthPoint()<=0&&isAlive)
		{
			setSurfaceImage(new ImageIcon("pic/human/died.jpg"));
			isAlive=false;
		}
		
	}
	
	public void hurtByMine(MyPoint explosionPoint,int hurtValue)
	{
		double distance=MyPoint.getDistance
		(getDoubleCenterPoint(),explosionPoint);
		distance=Math.abs(distance);
		
		if (vest != null) 
		{
			setHealthPoint(getHealthPoint()	- (int) (vest.getRate() * hurtValue));
			vest.setHealth(vest.getHealth()	- (int) (vest.getRate() * hurtValue));
		
		   if(vest.getHealth()<=0)
		   {
			   vest=null;
		   }
		}
		else
		{
			setHealthPoint(getHealthPoint()	-  hurtValue);
		}
		
		if(getHealthPoint()<=0)
		{
			setHealthPoint(0);
			setSurfaceImage(new ImageIcon("pic/human/died.jpg"));
			isAlive=false;
		}
		
		//调整速度
		double arc=VectorClass.arcOfPoints(explosionPoint, getDoubleCenterPoint());
		VectorClass plusVelocity=new VectorClass(30000.0/distance,arc);
		
		setVelocity(getVelocity().addition(plusVelocity));
	}
	
	public void hurtByRocket(MyPoint explosionPoint) 
	{
		double distance=MyPoint.getDistance
		(getDoubleCenterPoint(),explosionPoint);
		distance=Math.abs(distance);
		
		if (vest != null) 
		{
			if (getHealthPoint() > 0) 
			{
				if (distance > Rocket.killRadius)
				{
					setHealthPoint(getHealthPoint()
							- (int) (vest.getRate() * 20));
					vest.setHealth(vest.getHealth()
							- (int) (vest.getRate() * 20));
				} 
				else
				{
					double hurt;
					hurt = -(Rocket.killLevel - 20) * 1.0
							/ Rocket.killRadius * distance
							+ Rocket.killLevel;
					setHealthPoint(getHealthPoint()
							- (int) (vest.getRate() * hurt));
					vest.setHealth(vest.getHealth()
							- (int) (vest.getRate() * hurt));
				}
			}
			
			if(vest.getHealth()<=0)
			{
				vest=null;
			}
		}
		else
		{
			if (getHealthPoint() > 0) 
			{
				if (distance > Rocket.killRadius)
				{
					setHealthPoint(getHealthPoint()
							-  20);
				} 
				else
				{
					double hurt;
					hurt = -(Rocket.killLevel - 20) * 1.0
							/ Rocket.killRadius * distance
							+ Rocket.killLevel;
					setHealthPoint(getHealthPoint()	- (int) hurt);
				
				}
			}
		}
		
		if(getHealthPoint()<=0)
		{
			setHealthPoint(0);
			setSurfaceImage(new ImageIcon("pic/human/died.jpg"));
			isAlive=false;
		}
		
		//调整速度
		double arc=VectorClass.arcOfPoints(explosionPoint, getDoubleCenterPoint());
		VectorClass plusVelocity=new VectorClass(3000*(radius+Rocket.rocketSize/2)/distance,arc);
		
		setVelocity(getVelocity().addition(plusVelocity));
		
	}
	
	//这个方法负责更换地图时的初始化,参数是应传给它的新的位置
	public void initWhenMapChanged(MyPoint location)
	{
		this.setLocation(location);
        this.setVelocity(new VectorClass(0,0));
        this.setAcceleration(new VectorClass(0,0));
	}

	public void setCurrentWeapon(int currentWeapon) {
		this.currentWeapon = currentWeapon;
	}

	public void setWeaponList(Weapon[] weaponList) {
		this.weaponList = weaponList;
	}

	public long getMoney() {
		return money;
	}

	public void setMoney(long money) {
		this.money = money;
	}

	public Vest getVest() {
		return vest;
	}

	public void setVest(Vest vest) {
		this.vest = vest;
	}

	public void setClothes(Clothes c)
	{
		if (isAlive)
		{
			if (c != null)
			{
				currentClothes = c.getNameCode();
				this.setSurfaceImage(c.getPic());
			} 
			else
			{
				currentClothes = -1;
			}
		}
		else
		{
			;
		}
	}
	
	public Clothes getClothes()
	{
		if(currentClothes!=-1)
		{
			return new Clothes(currentClothes);
		}
		else
		{
			return null;
		}
		
	}

	public Clothes[] getClothesList() {
		return clothesList;
	}

	
	
	public void addClothes(Clothes clothes)
	{
		int i;        // to search for the end of the array
		for(i=0;(i<clothesList.length)&&(clothesList[i]!=null);i++)
			;
		if(i<clothesList.length)
		{
			clothesList[i]=clothes;
		}
		else
		{
			Clothes[] newClothesList=new Clothes[clothesList.length*2];
			for(int j=0;j<newClothesList.length;j++)
			{
				newClothesList[j]=null;
			}
			System.arraycopy(clothesList,0,newClothesList,0,clothesList.length);
			
			newClothesList[clothesList.length]=clothes;
			clothesList=newClothesList;
		}
	}

	public double getFat() {
		return fat;
	}

	public void setFat(double fat) 
	{
		this.fat = fat;
		if(fat<0)
		{
			fat=0;
		}
	}
	
	public Fists getFists()
	{
		return (Fists)(weaponList[0]);
	}
	
	
	public void movementPerformance()
	{
		super.movementPerformance();
		
		boolean isMoving=getMoveUpRequested()||
		    getMoveDownRequested()||
		    getMoveLeftRequested()||
            getMoveRightRequested();
		
		if (isMoving) 
		{
			// 处理耐力消耗或恢复
			if (getFootMovingCondition() == Human.WALK) 
			{
				setStamina(getStamina() + getMaxStamina()
						/ (1.0 * 20 * 1000 / ControlSetting.godTimerInterval));
			}
			if (getFootMovingCondition() == Human.SPRING) 
			{
				setStamina(getStamina() - 1);
			}
			
			// 处理消耗fat
			if (getFootMovingCondition() == Human.SPRING) 
			{
				setFat(getFat() - 100 * 1.0
						/ (15 * 60 * 1000 / ControlSetting.godTimerInterval));
			}
			if (getFootMovingCondition() == Human.RUN) 
			{
				setFat(getFat() - 100 * 1.0
						/ (30 * 60 * 1000 / ControlSetting.godTimerInterval));
			}

			//处理技能增加
			if(getFootMovingCondition()==Human.SPRING)
			{
				totalSpringTime++;
				
				
			}
			if(getFootMovingCondition()==Human.RUN)
			{
				totalRunTime++;
				
				
			}
			
		}
		else
		{
			setStamina(getStamina() + getMaxStamina()
					/ (1.0 * 20 * 1000 / ControlSetting.godTimerInterval));
		}
	    
		springSpeedSkill=(int)(totalSpringTime/(60*1000/ControlSetting.godTimerInterval))
	    +(int)(totalRunTime/(600*1000/ControlSetting.godTimerInterval));
	
	    staminaSkill=(int)((totalSpringTime+totalRunTime)/(36*1000/ControlSetting.godTimerInterval));
		
	    healthPointSkill=(int)((totalSpringTime+totalRunTime)/(60*1000/ControlSetting.godTimerInterval));
	}
	
	
	public int getGunSkill(int index)
	{
		return gunSkillPack.gunSkill[index];
	}
	public void increaseGunBullets(int index)
	{
		gunSkillPack.gunBullets[index]++;
		//换算机能点数
		gunSkillPack.caculateGunSkill();
	}
	public long getGunBullets(int index)
	{
		return gunSkillPack.gunBullets[index];
	}
	
    public void setGunSkill(int index, int skillAdded)
    {
    	gunSkillPack.gunSkill[index]+=skillAdded;
    }
	
	private class GunSkillPack
	{
		//这个类用来记录不同种类枪支的技能和子弹总数，index对应的枪支为NameConstants中同样数值的枪支
		
		public static final int N=100;
		private int[] gunSkill=new int[N];
		private long[] gunBullets=new long[N];
		
		public GunSkillPack()
		{
			for(int i=0;i<N;i++)
			{
				gunSkill[i]=0;
				gunBullets[i]=0;
			}
		}
		
		public void caculateGunSkill()
		{
		    for(int i=0;i<N;i++)
		    {
		    	switch(i)
		    	{
		    	case NameConstants.WEAPON_GUN_AK47:
		    	{
		    		Gun gun=new AK47(null);
		    		gunSkill[i]=(int)(gunBullets[i]/gun.getLoadCapsity()*5);
		    		break;
		    		
		    	}
		    	case NameConstants.WEAPON_GUN_AUG:
		    	{
		    		Gun gun=new AUG(null);
		    		gunSkill[i]=(int)(gunBullets[i]/gun.getLoadCapsity()*5);
		    		break;
		    	}
		    	case NameConstants.WEAPON_GUN_AWP:
		    	{
		    		Gun gun=new AWP(null);
		    		gunSkill[i]=(int)(gunBullets[i]/gun.getLoadCapsity()*5);
		    		break;
		    	}
		    	case NameConstants.WEAPON_GUN_CHEAP_GUN:
		    	{
		    		Gun gun=new CheapGun(null);
		    		gunSkill[i]=(int)(gunBullets[i]/gun.getLoadCapsity()*5);
		    		break;
		    	}
		    	case NameConstants.WEAPON_GUN_DESERT_EAGLE:
		    	{
		    		Gun gun=new DesertEagle(null);
		    		gunSkill[i]=(int)(gunBullets[i]/gun.getLoadCapsity()*5);
		    		break;
		    	}
		    	case NameConstants.WEAPON_GUN_M249:
		    	{
		    		Gun gun=new M249(null);
		    		gunSkill[i]=(int)(gunBullets[i]/gun.getLoadCapsity()*5);
		    		break;
		    	}
		    	case NameConstants.WEAPON_GUN_M4:
		    	{
		    		Gun gun=new M4(null);
		    		gunSkill[i]=(int)(gunBullets[i]/gun.getLoadCapsity()*5);
		    		break;
		    	}
		    	case NameConstants.WEAPON_GUN_METAL_STORM:
		    	{
		    		Gun gun=new MetalStorm(null);
		    		gunSkill[i]=(int)(gunBullets[i]/gun.getLoadCapsity()*5);
		    		break;
		    	}
		    	case NameConstants.WEAPON_GUN_MP5:
		    	{
		    		Gun gun=new MP5(null);
		    		gunSkill[i]=(int)(gunBullets[i]/gun.getLoadCapsity()*5);
		    		break;
		    	}
		    	case NameConstants.WEAPON_GUN_SIG552:
		    	{
		    		Gun gun=new SIG552(null);
		    		gunSkill[i]=(int)(gunBullets[i]/gun.getLoadCapsity()*5);
		    		break;
		    	}
		    	default:
		    	{
		    		break;
		    	}
		    	
		    	
		    	}
		    }
		}

		
		
	}
	
	public String toString()
	{
		String output="";
		output+=super.toString()+"\n";

		output+="==Skill Field: \n";
		
		output+="=fatField: \n";
		output+="fat: "+fat+"\n";
	    		
		output+="=springField: \n";
		output+="totalSpringTime: "+totalSpringTime+"\n";
		output+="totalRunTime: "+totalRunTime+"\n";
		output+="springSpeedSkill: "+springSpeedSkill+"\n";
		output+="springMaxSpeed: "+getSpringMaxSpeed()+"\n";
		
		output+="=staminaField: \n";
		output+="currentStamina: "+stamina+"\n";
		output+="maxStamina: "+getMaxStamina()+"\n";
		output+="staminaSkill: "+staminaSkill+"\n";
		
		Fists fists=(Fists)weaponList[0];
		output+="=fistsField: \n";
		output+="attackTimes: "+fists.getAttackTimes()+"\n";
		output+="attackStrength: "+fists.getAttackStrength()+"\n";
		output+="attackInterval: "+fists.getAttackInterval()+"\n";
		
		output+="=gunField: \n";
		for(int i=0;i<GunSkillPack.N;i++)
		{
			switch(i)
	    	{
	    	case NameConstants.WEAPON_GUN_AK47:
	    	{
	    		Gun gun=new AK47(null);
	    		output+=(gun.getName()+" skill: "+getGunSkill(i)+", bullets: "+getGunBullets(i));
	    		output+="\n";
	    		break;
	    		
	    	}
	    	case NameConstants.WEAPON_GUN_AUG:
	    	{
	    		Gun gun=new AUG(null);
	    		output+=(gun.getName()+" skill: "+getGunSkill(i)+", bullets: "+getGunBullets(i));
	    		output+="\n";
	    		break;
	    	}
	    	case NameConstants.WEAPON_GUN_AWP:
	    	{
	    		Gun gun=new AWP(null);
	    		output+=(gun.getName()+" skill: "+getGunSkill(i)+", bullets: "+getGunBullets(i));
	    		output+="\n";
	    		break;
	    	}
	    	case NameConstants.WEAPON_GUN_CHEAP_GUN:
	    	{
	    		Gun gun=new CheapGun(null);
	    		output+=(gun.getName()+" skill: "+getGunSkill(i)+", bullets: "+getGunBullets(i));
	    		output+="\n";
	    		break;
	    	}
	    	case NameConstants.WEAPON_GUN_DESERT_EAGLE:
	    	{
	    		Gun gun=new DesertEagle(null);
	    		output+=(gun.getName()+" skill: "+getGunSkill(i)+", bullets: "+getGunBullets(i));
	    		output+="\n";
	    		break;
	    	}
	    	case NameConstants.WEAPON_GUN_M249:
	    	{
	    		Gun gun=new M249(null);
	    		output+=(gun.getName()+" skill: "+getGunSkill(i)+", bullets: "+getGunBullets(i));
	    		output+="\n";
	    		break;
	    	}
	    	case NameConstants.WEAPON_GUN_M4:
	    	{
	    		Gun gun=new M4(null);
	    		output+=(gun.getName()+" skill: "+getGunSkill(i)+", bullets: "+getGunBullets(i));
	    		output+="\n";
	    		break;
	    	}
	    	case NameConstants.WEAPON_GUN_METAL_STORM:
	    	{
	    		Gun gun=new MetalStorm(null);
	    		output+=(gun.getName()+" skill: "+getGunSkill(i)+", bullets: "+getGunBullets(i));
	    		output+="\n";
	    		break;
	    	}
	    	case NameConstants.WEAPON_GUN_MP5:
	    	{
	    		Gun gun=new MP5(null);
	    		output+=(gun.getName()+" skill: "+getGunSkill(i)+", bullets: "+getGunBullets(i));
	    		output+="\n";
	    		break;
	    	}
	    	case NameConstants.WEAPON_GUN_SIG552:
	    	{
	    		Gun gun=new SIG552(null);
	    		output+=(gun.getName()+" skill: "+getGunSkill(i)+", bullets: "+getGunBullets(i));
	    		output+="\n";
	    		break;
	    	}
	    	default:
	    	{
	    		break;
	    	}
	    	}
			
		}
		return output;
	}

	public int getStaminaSkill() {
		return staminaSkill;
	}

	public void setStaminaSkill(int staminaSkill) {
		this.staminaSkill = staminaSkill;
	}
}

/* 体能与技能系统，移动、开枪、买东西、到健身房、到射击练习场都会改变
 * 金钱在买东西，小游戏、决斗的时候都会改变
 *
 * 
 *
 */