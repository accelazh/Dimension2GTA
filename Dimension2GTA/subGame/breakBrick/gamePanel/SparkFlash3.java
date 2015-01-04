package subGame.breakBrick.gamePanel;

import java.awt.Graphics;
import java.awt.*;
import javax.swing.*;
import java.awt.image.*;
public class SparkFlash3
{
	
	public final static int RADIUS=45;
	private int radius1=0;    //动态的爆炸火花的半径
	private int radius2=0;
	private final static int step=6;
	private Point center=null;
	
	private boolean useAble=true;
	
	public SparkFlash3(Point center)
	{
		this.center=new Point(center.x,center.y);
	    
		radius1=RADIUS/2;
		radius2=RADIUS/6;
	
	} 
	
	
	
	public void selfProcess()
	{
		if(radius1<RADIUS)
		{
			radius1+=step;
		    radius2+=step*5/3;
		}
		else
		{ 
			useAble=false;
		}
		
	}
	
	public void paint(Graphics g)
	{
		if(useAble)
		{
			g.setColor(Color.YELLOW);
			g.fillOval(center.x-radius1, center.y-radius1,2*radius1, 2*radius1);
			
			g.setColor(Color.WHITE);
			g.fillOval(center.x-radius2, center.y-radius2,2*radius2, 2*radius2);
			
		}
	}

	public boolean isUseAble() {
		return useAble;
	}

	
	public Point getCenter() {
		return center;
	}

	public void setCenter(Point center) {
		this.center = center;
	}
	
}
