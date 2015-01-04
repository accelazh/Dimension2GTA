package subGame.westGun;


import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class GunOfWestGun implements ActionListener 
{
	Timer timer;
	private final int interval=100;
	private boolean isShootAble;
	private final int INTERVAL_WHEN_SHOOTING=(int)(0.6*1000/interval);
    private int intervalWhenShootingCount;
    
    public GunOfWestGun()
    {
    	timer=new Timer(interval,this);
    	isShootAble=true;
    	intervalWhenShootingCount=0;
    }
    
    public boolean isShootAble()
    {
    	if(isShootAble)
    	{
    		isShootAble=false;
    		timer.start();
    		intervalWhenShootingCount=0;
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    public void actionPerformed(ActionEvent e)
    {
    	if(e.getSource()==timer)
    	{
    		if(INTERVAL_WHEN_SHOOTING==intervalWhenShootingCount)
    		{
    			intervalWhenShootingCount=0;
    			timer.stop();
                isShootAble=true;    			
    		}
    		else
    		{
    			intervalWhenShootingCount++;
    		}
    	}
    }
    
    public Timer getTimer()
    {
    	return timer;
    }
}

