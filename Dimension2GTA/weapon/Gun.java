package weapon;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import basicConstruction.*;
import gameDisplayProcessor.*;
import utilities.*;

import java.io.*;
import java.util.*;

public abstract class Gun implements Weapon,Item
{
	//for debug
	private boolean debug9=ControlSetting.debug9;;
	private boolean debug10=ControlSetting.debug10;;
	
	//一扣扳机，抢返回一颗子弹，Gun中有主人的引用，以便计算散射角
	//date field
	String gunName;
	Human owner;
	
	//for the current state
	public static final int READY_TO_SHOOT=0;
	public static final int INTERVAL_WHEN_SHOOTING=1;
	public static final int RELOADING=2;
	public static final int OUT_OF_BULLET=3;

	int currentState;
	int intervalWhenShootingCounter;	//用来计算interval when shooting发弹间隔时间
	int intervalWhenShootingSpan;       //当Counter从0开始达到这个变量的值的时候，表示interval已过
	
	int reloadingCounter;        //用来计算换弹夹的时间
	int reloadingSpan;           //当Counter从0开始达到这个变量的值的时候，表示弹夹已经装好
	
	//刻画枪的属性
	int loadCapsity;  //弹夹可以装多少颗子弹
	int numOfLoaded; //弹夹里现在又多少颗子弹
	int numOfLeft;  //还剩下多少颗子弹不再弹夹里
	int killLevel;  //子弹的杀伤力等于这个
	double shakenLevel;  //用来联合主人的gunSkill计算子弹散射角,值从1到10
	
	private int gunNameCode=0;
	
	
	

	//methods
	public Gun()
	{
		this.gunName="defaultGun";
		this.owner=null;
		
		this.currentState=READY_TO_SHOOT;
		this.intervalWhenShootingCounter=0;
		this.intervalWhenShootingSpan=(int)(0.1*1000/ControlSetting.godTimerInterval);
		this.reloadingCounter=0;
		this.reloadingSpan=(int)(2.5*1000/ControlSetting.godTimerInterval);
		
		loadCapsity=30;
		numOfLoaded=30;
		numOfLeft=90;
		
		killLevel=30;
		shakenLevel=4;
		
	}
	
	public Gun(String gunName,Human owner,int killLevel,double shakenLevel,
			double intervalWhenShootingSpan,double reloadingSpan,
			int loadCapsity,int numOfLeft,int gunNameCode)
	{
		this();
		
		setOwner(owner);
		setGunName(gunName);
		setIntervalWhenShootingSpan(intervalWhenShootingSpan);  //这里的和数据域的不同，是以秒为单位的
		setReloadingSpan(reloadingSpan);
		setKillLevel(killLevel);
		setLoadCapsity(loadCapsity);
		setNumOfLeft(numOfLeft);
		setNumOfLoaded(loadCapsity);
		setShakenLevel(shakenLevel);
		
		this.gunNameCode=gunNameCode;
	}

	public String getGunName() {
		return gunName;
	}


	public void setGunName(String gunName) {
		this.gunName = gunName;
	}


	public Human getOwner() {
		return owner;
	}


	public void setOwner(Human owner) {
		this.owner = owner;
	}

	public double getIntervalWhenShootingSpan() 
	{
		return intervalWhenShootingSpan*ControlSetting.godTimerInterval/1000.0;
	}


	public void setIntervalWhenShootingSpan(double seconds) 
	{
		this.intervalWhenShootingSpan = (int)(seconds*1000/ControlSetting.godTimerInterval);
	}



	public double getReloadingSpan() {
		return reloadingSpan*ControlSetting.godTimerInterval/1000.0;
	}


	public void setReloadingSpan(double seconds) {
		this.reloadingSpan = (int)(seconds*1000/ControlSetting.godTimerInterval);
	}


	public int getLoadCapsity() {
		return loadCapsity;
	}


	public void setLoadCapsity(int loadCapsity) {
		this.loadCapsity = loadCapsity;
	}


	public int getNumOfLoaded() {
		return numOfLoaded;
	}


	public void setNumOfLoaded(int numOfLoaded) {
		this.numOfLoaded = numOfLoaded;
	}


	public int getNumOfLeft() {
		return numOfLeft;
	}


	public void setNumOfLeft(int numOfLeft) {
		this.numOfLeft = numOfLeft;
	}


	public int getKillLevel() {
		return killLevel;
	}


	public void setKillLevel(int killLevel) {
		this.killLevel = killLevel;
	}


	public double getShakenLevel() {
		return shakenLevel;
	}


	public void setShakenLevel(double shakenLevel) {
		this.shakenLevel = shakenLevel;
	}

	

	public Bullet selfProcessWhenShot(boolean isShooting)   //无论扣不扣扳机都调用这个方法，自动调整枪的状态；在godTimer中，对所有Human的currentWeapon中的Gun调用这个方法
	{
		if(READY_TO_SHOOT==currentState)
		{
			if(isShooting)
			{
				currentState=INTERVAL_WHEN_SHOOTING;
			    intervalWhenShootingCounter=0;
			    
			    //这里要计算子弹散射角
			    if(debug10)
			    {
			    	System.out.println("when calculate the bullet arc :");
			    	System.out.println("owner's faceToArc : "+owner.getFaceToArc()/Math.PI+"PI");
			    	
			    }
			    double maxScatterArc;
			    double x=shakenLevel;
			    double y=owner.getGunSkill(gunNameCode);
			    maxScatterArc=Math.PI/4*(Math.pow(Math.E,x/10.0)-1)/(Math.E-1)*Math.pow(Math.E,-2*y/1000);
			    if(debug10)
			    {
			    	System.out.println("maxScatterArc == "+maxScatterArc/Math.PI+"PI");
			    }
			    double scatterRate;
			    
			    scatterRate=Math.random();
			    
			    if(debug10)
			    {
			    	System.out.println("scatterRate == "+scatterRate);
			    }
			    double sign;
			    sign=(Math.random()>=0.5)?1:(-1);
			    
			    if(debug10)
			    {
			    	System.out.println("sign == "+sign);
			    }
			    double scatterArc;
			    scatterArc=scatterRate*sign*maxScatterArc;
			    
			    if(debug10)
			    {
			    	System.out.println("the scatterArc == "+ scatterArc/Math.PI+"PI");
			    	System.out.println("the owner.faceToArc()== "+owner.getFaceToArc()/Math.PI+"PI");
			    }
			    
			    Bullet bullet=new Bullet(owner.getCenterPoint(),owner.getFaceToArc()+scatterArc,this.killLevel,owner);
			    
			  
			    //这里调整技能
			    if(owner!=null)
			    {
			    	owner.increaseGunBullets(gunNameCode);
			    }
			    
			    return bullet;
		    }
			else
			{
				return null;
			}
		}
		else
		{

			if (INTERVAL_WHEN_SHOOTING == currentState) {
				intervalWhenShootingCounter++;
				if (intervalWhenShootingSpan == intervalWhenShootingCounter) {
					intervalWhenShootingCounter = 0;
					if (numOfLoaded > 0) {
						currentState = READY_TO_SHOOT;
						numOfLoaded -= 1;
					} else {
						if (numOfLeft > 0) {
							currentState = RELOADING;
							reloadingCounter = 0;
						} else {
							currentState = OUT_OF_BULLET;
						}
					}
				}

			}

			if (RELOADING == currentState) {
				reloadingCounter++;
				if (reloadingSpan == reloadingCounter) {
					reloadingCounter = 0;
					currentState = INTERVAL_WHEN_SHOOTING;
					intervalWhenShootingCounter = 0;
					if (numOfLeft >= loadCapsity-numOfLoaded) {
						numOfLeft -= loadCapsity-numOfLoaded;
                        numOfLoaded = loadCapsity;
					} else {
						numOfLoaded += numOfLeft;
						numOfLeft = 0;
					}

				}

			}

			if (OUT_OF_BULLET == currentState) {
				if (numOfLeft > 0) {
					currentState = RELOADING;
					reloadingCounter = 0;

				}

			}

		return null;
		}
	}
	
	public MyPoint getGunSparkPosition()
	{
		return new MyPoint(owner.getDoubleCenterPoint().x+owner.getRadius()*Math.cos(owner.getFaceToArc()),
				owner.getDoubleCenterPoint().y-owner.getRadius()*Math.sin(owner.getFaceToArc()));
	}
	
	public String toString()
	{
		return gunName;
	}
	
	public void setReloading()
	{
		if(READY_TO_SHOOT==currentState)
		{
			currentState=RELOADING;
		    reloadingCounter=0;
	    }
    }

	public int getCurrentState() {
		return currentState;
	}

	    
	 
	public abstract ImageIcon getMapPic();

	 
	public abstract boolean Buy(Human buyer); 
	
	 
	public String getInfo() 
	{
		try 
		{
			File info = new File("pic/showPic/" + getGunName() + ".txt");
			Scanner input = new Scanner(info);
			String output = "";
			while (input.hasNext()) 
			{
				output += input.nextLine();
			}
			return output;
		} 
		catch (Exception ex)
		{
			return "";
		}
	}

	 
	public ImageIcon getPic() 
	{
		return new ImageIcon("pic/showPic/"+getGunName()+".jpg");
	}

	 
	public abstract int getPrice(); 
	
	 
	public abstract boolean PickUp(Human picker); 
	
	 
	public abstract String getName();
	
	public int getGunNameCode() {
		return gunNameCode;
	}
}