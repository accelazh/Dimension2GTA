package weapon;

import javax.swing.*;

import utilities.MyPoint;

import java.awt.event.*;
import basicConstruction.*;

import java.awt.*;
/**
 * 
 * @author Administrator
 *�����ģ���˵�ȭͷ��������ʱʹ��
 */
public class Fists implements Weapon,ActionListener
{
	//for debug
	private boolean localDebuger=false;
	
	Timer timer;
	private static final int TIMER_INTERVAL=100;
	
	private int attackStrength;   //����������healthPointͬһ��λ
	public static final int TOP_ATTACK_STRENGTH=200;	
	private int attackInterval;   //����ʱ��������ֵ��ʾ��timer������ٴκ�ɹ���
	private int attackIntervalCurrentState;  
	private static  final int INITIAL_ATTACK_STRENGTH=15;
	private static final int INITIAL_ATTACK_INTERVAL=(int)(0.4*1000/TIMER_INTERVAL);
		
	private boolean attackReady;  //��ʾ���Գ���һȭ
	
	private final int ATTACK_RADIUS=(int)(Human.radius*2.5);
	
	Human owner;
	
	//skillר��
	private int attackTimes=0;
	
	//for findWhoIsAttacked
	private Point hitPoint;
	private boolean isHitSomething;
	private Solid hittedSolid;
	private double arc;   //�����ʾȭͷ����ķ���
	
	public Fists()
	{
		this(null);
	}
	
	public Fists(Human owner)
	{
		attackStrength=INITIAL_ATTACK_STRENGTH;
		attackInterval=INITIAL_ATTACK_INTERVAL;
		attackIntervalCurrentState=0;
		
		attackReady=true;
		
		timer=new Timer(TIMER_INTERVAL,this);
		
		this.owner=owner;
		
		hitPoint=null;
		isHitSomething=false;
		hittedSolid=null;
		arc=0;
	}
	
	
	public boolean attackSelfProcess(boolean attackRequested)    //�����󹥻���ʱ����������������������true���ʾ���������������ʾ����������
	{
		if (attackRequested) 
		{
			if (attackReady) 
			{
				attackReady = false;
				attackIntervalCurrentState = 0;
				
				timer.start();
 
				if(localDebuger)
				{
					System.out.println("====in Fists's attackSelfProcess====");
					System.out.println("attackStrength == "+attackStrength);
					System.out.println("attackInterval == "+attackInterval);
					System.out.println("attackTimes == "+attackTimes);
				}
				
				
				return true;
			} 
			else 
			{
				return false;
			}
		}
		else
		{
		    return false;	
		}
	}
	
	public void findWhoIsAttacked(Solid[] solidsInScreen)
	{
		//�õ�������Χ�ڵ�solids�͹�����Χ����
		Rectangle rangeRect=new Rectangle();
		rangeRect.setSize(2*ATTACK_RADIUS,2*ATTACK_RADIUS);
		rangeRect.setLocation(owner.getCenterPoint().x-ATTACK_RADIUS,
				owner.getCenterPoint().y-ATTACK_RADIUS);
		
		Solid[] solidsInRange=MapContainer.SolidsInRectangle(rangeRect, solidsInScreen);
		
		//���õ����е�
		MyPoint currentPos=new MyPoint(owner.getDoubleCenterPoint().x,
				owner.getDoubleCenterPoint().y);
		this.arc=owner.getFaceToArc();
		
		boolean isHitted=false;
		boolean isOut=false;
		do
		{
			
			isHitted=false;
			for(int k=0;k<solidsInRange.length;k++)
			{
				if((solidsInRange[k]!=null)&&(owner!=solidsInRange[k]))
				{
					if(!solidsInRange[k].isAccesibleFromMap(currentPos))  //accessible����Ҫ�����Լ����ϽǵĶ����Ǵ��ͼ���Ͻ�
					{
						hittedSolid=solidsInRange[k];
						hitPoint=currentPos.getPoint();
						isHitted=true;
					}
				}
				
			}
			
			isOut=false;
			if(!rangeRect.contains(currentPos.getPoint()))
			{
				isOut=true;
			}
			
			
			currentPos.setLocation(currentPos.x+2*Math.cos(arc),currentPos.y-2*Math.sin(arc));
			
		}while((!isHitted)&&(!isOut));
		
		if(isHitted)
		{
			isHitSomething=true;
			
			//��������
			attackTimes++;
			
			if((0==attackTimes%65)
					&&(attackTimes>=65))
			{
				attackStrength++;
			}
			if((0==attackTimes%400)
				&&(attackTimes>=400))
			{
				attackIntervalCurrentState=0;
				attackInterval--;
				if(attackInterval<=0)
				{
					attackInterval=0;
				}
			}
						
		}
		else
		{
			isHitSomething=false;
			hitPoint=null;
			hittedSolid=null;
		}
		
	}
	
	
		
	public void actionPerformed(ActionEvent e)
	{
		if(attackInterval==attackIntervalCurrentState)
		{
			attackReady=true;
			timer.stop();
			attackIntervalCurrentState=0;
		}
		else
		{
			if(attackIntervalCurrentState>attackInterval)
			{
				attackIntervalCurrentState=0;
			}
			else
			{
			    attackIntervalCurrentState++;
			}
		}
	}

	public Point getHitPoint() {
		return hitPoint;
	}

	public void setHitPoint(Point hitPoint) {
		this.hitPoint = hitPoint;
	}

	public boolean isHitSomething() {
		return isHitSomething;
	}

	public void setHitSomething(boolean isHitSomething) {
		this.isHitSomething = isHitSomething;
	}

	public Solid getHittedSolid() {
		return hittedSolid;
	}

	public void setHittedSolid(Solid hittedSolid) {
		this.hittedSolid = hittedSolid;
	}

	public int getAttackStrength() {
		return attackStrength;
	}

	public double getArc() {
		return arc;
	}
	
	public void copyFistsSkillToMe(Fists fists)
	{
		this.attackTimes=fists.attackTimes;
		this.attackStrength=fists.attackStrength;
		this.attackInterval=fists.attackInterval;
		this.attackIntervalCurrentState=fists.attackIntervalCurrentState;
	}
	
	public String toString()
	{
		String output="=Fists=\n";
		output+="attackTimes: "+attackTimes+"\n";
		output+="attackStrength: "+attackStrength+"\n";
		output+="attackInterval: "+attackInterval+"\n";
		output+="\n";
		
		return output;
	}

	public static int getTIMER_INTERVAL() {
		return TIMER_INTERVAL;
	}

	public int getAttackInterval() {
		return attackInterval;
	}

	public int getAttackTimes() {
		return attackTimes;
	}
	
	public void increaseAttackTimesBy(int times)
	{
		for (int i = 0; i < times; i++) {// ��������
			attackTimes++;

			if ((0 == attackTimes % 65) && (attackTimes >= 65)) {
				attackStrength++;
			}
			if ((0 == attackTimes % 400) && (attackTimes >= 400)) {
				attackIntervalCurrentState = 0;
				attackInterval--;
				if (attackInterval <= 0) {
					attackInterval = 0;
				}
			}
		}
	}
	
	public void increaseAttackStrengthBy(int value)
	{
		attackStrength+=value;
	}
	
}
