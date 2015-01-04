package subGame.breakBrick.gamePanel;

import java.awt.*;

public class BulletHole 
{
	private Point center=null;
	public final int radius=5;
	
	public BulletHole()
	{
		this.center=new Point(0,0);
	}

	public BulletHole(Point center)
	{
		this.center=center;
	}
	
	public void selfProcess()
	{
		
	}
	
	public void paint(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillOval(center.x-radius,center.y-radius,2*radius,2*radius);
	}
	
	public void screenMove(int step, boolean up)
	{
		if(up)
		{
			center=new Point(center.x,center.y+step);
		}
		else
		{
			center=new Point(center.x,center.y-step);
		}
	}
}
