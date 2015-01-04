package basicConstruction;

import java.awt.event.*;

import utilities.MyPoint;

/**
 * 
 * @author Administrator
 * 这个类用来创建枪口子弹轨迹
 */
public class GunLine extends Effect implements ActionListener
{
	private MyPoint startPoint;  //枪口子弹轨迹的起点
    private double arc;
    final double START_LENGTH=100; //指起始的时候的长度，单位像素
    private double offsetLength;  //指从startPoint起向前偏移多少才是真正的画线的起点
    private MyPoint trueStartPoint;
    private MyPoint trueEndPoint;
    
    
    public GunLine(MyPoint startPoint, double arc)
    {
    	this.startPoint=startPoint;
    	this.arc=arc;
    	
    	this.offsetLength=0.3*START_LENGTH+0.2*START_LENGTH*Math.random();
        
    	getTimer().setDelay(1000);
		getTimer().addActionListener(this);
		getTimer().start();
		setOnOrOff(Effect.ON);
		
		trueStartPoint=new MyPoint(startPoint.x+offsetLength*Math.cos(arc),
				startPoint.y-offsetLength*Math.sin(arc));
		trueEndPoint=new MyPoint(startPoint.x+(offsetLength+START_LENGTH)*Math.cos(arc),
				startPoint.y-(offsetLength+START_LENGTH)*Math.sin(arc));
    
    }
    
    public MyPoint getStartPoint()
    {
    	return trueStartPoint;
    }
    public MyPoint getEndPoint()
    {
    	return trueEndPoint;
    	
    }
    
    public void actionPerformed(ActionEvent e)
    {
    	
    		getTimer().stop();
			setOnOrOff(Effect.OFF);
    	
    		
    }
}
