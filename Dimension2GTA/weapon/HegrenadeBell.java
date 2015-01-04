package weapon;

import javax.swing.*;
import java.awt.event.*;

/**
 * 
 * @author Administrator
 * �����ģ������׵Ĵ��ӣ�
 * ���human��weaponList
 * ��û��Hegrenade��ʵ����
 * ֻ��HegrenadeBell��ʵ��
 */
public class HegrenadeBell implements Weapon,ActionListener
{
	private int numOfHegrenade;
	private Timer timer;
	private final int TIMER_INTERVAL=1000;
	private int timeCounter;
	private final int INUSEABLE_TIME=(int)(1*1000/TIMER_INTERVAL);
	private boolean useable;   //�����������ʱ�ܲ��ܴӴ������ó�����
	
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
