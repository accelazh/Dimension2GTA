package basicConstruction;

import javax.swing.*;
public class Effect 
{
	private Timer timer=new Timer(10,null);  //用作制作动画的计时器
	private int onOrOff=OFF;
	public final static int ON=0;
	public final static int OFF=1;
	
	public Timer getTimer()
	{
		return timer;
	}
	
	public int getOnOrOff()
	{
		return this.onOrOff;
	}
	public void setOnOrOff(int onOrOff)
	{
		this.onOrOff=onOrOff;
	}

}
