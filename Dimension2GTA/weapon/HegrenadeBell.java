package weapon;

import javax.swing.*;
import java.awt.event.*;

/**
 * 
 * @author Administrator
 * 这个类模拟挂手雷的袋子，
 * 因此human的weaponList
 * 上没有Hegrenade的实例，
 * 只有HegrenadeBell的实例
 */
public class HegrenadeBell implements Weapon,ActionListener
{
	private int numOfHegrenade;
	private Timer timer;
	private final int TIMER_INTERVAL=1000;
	private int timeCounter;
	private final int INUSEABLE_TIME=(int)(1*1000/TIMER_INTERVAL);
	private boolean useable;   //这个用来看此时能不能从袋子里拿出手雷
	
	public HegrenadeBell(int num)
	{
		this.numOfHegrenade =num;
		timer=new Timer(TIMER_INTERVAL,this);
		timeCounter=0;
	    useable=true;
	}
	
	public HegrenadeBell()
	{
		this(0);
	}

	public int getNumOfHegrenade() {
		return numOfHegrenade;
	}

	public void setNumOfHegrenade(int numOfHegrenade) {
		this.numOfHegrenade = numOfHegrenade;
	}
	
	public boolean isUseable() 
	{
		return useable;
	}

	public void setUseableFalse() 
	{
		this.useable = false;
		timeCounter=0;
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(INUSEABLE_TIME==timeCounter)
		{
			useable=true;
			timer.stop();
			timeCounter=0;
		}
		else
		{
		    timeCounter++;
		}
	}

	
}
