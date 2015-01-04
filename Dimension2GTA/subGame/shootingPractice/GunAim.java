package subGame.shootingPractice;

import java.awt.*;

public class GunAim 
{
	private Point center=null;
	private final int ORIG_RADIUS=10;
	private final int MAX_RADIUS=30;
	private int radius=ORIG_RADIUS;
	private int radiusIncrease=10;
	
	private Color color=Color.green;
	private int colorPointer=0;
	
	//控制准星的回复
	private final int COUNTER_SPAN=5; 
	private int counter=0;
	
	public GunAim()
	{
		this.center=new Point(100,100);
		
	}
	
	public GunAim(Point center)
	{
		this.center=center;
		
	}
	
	public void setCenter(Point point)
	{
		this.center=point;
	}
	
	public int getRadius()
	{
		return this.radius;
	}
	
	public void setRadius(int newRadius)
	{
		this.radius=newRadius;
		if(radius<ORIG_RADIUS)
		{
			radius=ORIG_RADIUS;
		}
		
		if(radius>MAX_RADIUS)
		{
			radius=MAX_RADIUS;
		}
	}
	
	public void shootAdjust()
	{
		setRadius(getRadius()+radiusIncrease);
	}
	
	public void selfProcess()
	{
		if(counter<COUNTER_SPAN)
		{
			counter++;
		}
		else
		{
			counter=0;
			setRadius(getRadius()-1);
		}
	}
	
	private void drawLine(Graphics g, Point startPoint,int length, boolean isHorizontal)
	{
		if(isHorizontal)
		{
			g.drawLine(startPoint.x,startPoint.y,startPoint.x+length,startPoint.y);
		}
		else
		{
			g.drawLine(startPoint.x,startPoint.y,startPoint.x,startPoint.y+length);
		}
	}
	
	public void paint(Graphics g)
	{
		if(null==g)
		{
			return;
		}
		
		double rate=1.0/3;
		
		g.setColor(color);
		drawLine(g,new Point(center.x-radius,center.y-radius),(int)(rate*2*radius),true);
		drawLine(g,new Point(center.x-radius,center.y+radius),(int)(rate*2*radius),true);
        
		drawLine(g,new Point((int)(center.x+radius*(1-2*rate)),center.y-radius),(int)(rate*2*radius),true);
		drawLine(g,new Point((int)(center.x+radius*(1-2*rate)),center.y+radius),(int)(rate*2*radius),true);
	
		drawLine(g,new Point(center.x-radius,center.y-radius),(int)(rate*2*radius),false);
		drawLine(g,new Point(center.x+radius,center.y-radius),(int)(rate*2*radius),false);
	
		drawLine(g,new Point(center.x-radius,(int)(center.y+radius*(1-2*rate))),(int)(rate*2*radius),false);
		drawLine(g,new Point(center.x+radius,(int)(center.y+radius*(1-2*rate))),(int)(rate*2*radius),false);
	
		int subLength=(int)(2*radius*(1-rate));
		drawLine(g,new Point((int)(center.x-2*radius*(1-2*rate)-subLength),center.y),subLength,true);
		drawLine(g,new Point((int)(center.x+2*radius*(1-2*rate)),center.y),subLength,true);
	
		drawLine(g,new Point(center.x,(int)(center.y-2*radius*(1-2*rate)-subLength)),subLength,false);
		drawLine(g,new Point(center.x,(int)(center.y+2*radius*(1-2*rate))),subLength,false);
		
	}
	
	public void switchAimColor()
	{
		switch(colorPointer)
		{
		case 0:
		{
			color=Color.YELLOW;
			colorPointer=1;
			break;
		}
		case 1:
		{
			color=Color.BLUE;
			colorPointer=2;
			break;
		}
		case 2:
		{
			color=Color.RED;
			colorPointer=3;
			break;
		}
		case 3:
		{
			color=Color.WHITE;
			colorPointer=4;
			
			break;
		}
		case 4:
		{
			color=Color.BLACK;
			colorPointer=5;
			
			break;
		}
		case 5:
		{
			color=Color.GREEN;
			colorPointer=0;
			break;
		}
		default:
		{
			break;
		}
		}
	}

	public void switchAimColor(int to)
	{
		colorPointer=Math.abs(to)%6;
		switch(colorPointer)
		{
		case 1:
		{
			color=Color.YELLOW;
			colorPointer=1;
			break;
		}
		case 2:
		{
			color=Color.BLUE;
			colorPointer=2;
			break;
		}
		case 3:
		{
			color=Color.RED;
			colorPointer=3;
			break;
		}
		case 4:
		{
			color=Color.WHITE;
			colorPointer=4;
			
			break;
		}
		case 5:
		{
			color=Color.BLACK;
			colorPointer=5;
			
			break;
		}
		case 0:
		{
			color=Color.GREEN;
			colorPointer=0;
			break;
		}
		default:
		{
			break;
		}
		}
	}
	
	public Point getCenter() {
		return center;
	}

}
