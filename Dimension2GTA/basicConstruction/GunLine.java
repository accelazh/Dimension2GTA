package basicConstruction;

import java.awt.event.*;

import utilities.MyPoint;

/**
 * 
 * @author Administrator
 * �������������ǹ���ӵ��켣
 */
public class GunLine extends Effect implements ActionListener
{
	private MyPoint startPoint;  //ǹ���ӵ��켣�����
    private double arc;
    final double START_LENGTH=100; //ָ��ʼ��ʱ��ĳ��ȣ���λ����
    private double offsetLength;  //ָ��startPoint����ǰƫ�ƶ��ٲ��������Ļ��ߵ����
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
