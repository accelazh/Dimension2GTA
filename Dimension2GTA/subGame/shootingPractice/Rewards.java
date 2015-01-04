package subGame.shootingPractice;

import java.awt.*;
import javax.swing.*;

import utilities.MyPoint;
import basicConstruction.*;
import java.awt.image.*;

public class Rewards 
{
	public final static double gravity=ShootingPractice6.gravity;
	public final static int TIMER_INTERVAL=ShootingPractice6.TIMER_INTERVAL;
	public final static Dimension wallPanelSize=ShootingPractice6.wallPanelTotalSize;
	
	public final static Dimension size=new Dimension(30,30);
	public final static double rewardAverageVelocity=800;
	
	public final static int NONE=-1;
	public final static int FIRE_BALL=0;
	public final static int IRON_BALL=1;
	public final static int ROCKET=2;
	private int type=-1;
	
	public final static Image[] images={
		Toolkit.getDefaultToolkit().getImage("pic/SubGame/shootingPractice/fireBallReward.gif"),
		Toolkit.getDefaultToolkit().getImage("pic/SubGame/shootingPractice/ironBallReward.gif"),
		Toolkit.getDefaultToolkit().getImage("pic/SubGame/shootingPractice/rocketReward.gif"),
			
	};
	
	private MyPoint location=null;
	private double Vx=0;
	private double Vy=0;
	
	private boolean useAble=true;
	
	private boolean hitted=false;
	private int hittedProcessSize=size.width;
	
	public Rewards(int type)
	{
		if(type>=0&&type<=2)
		{
			this.type=type;
		}
		else
		{
			this.type=2;
		}
	}
	
	public Rewards(int type, Point startPoint, double Vx,double Vy)
	{
		if(type>=0&&type<=2)
		{
			this.type=type;
		}
		else
		{
			this.type=2;
		}
		
		this.location=new MyPoint(startPoint.x,startPoint.y);
		this.Vx=Vx;
		this.Vy=Vy;
	}
	
	public void selfProcess()
	{
		if(hitted)
		{
			return;
		}
		
		double dertX = Vx * TIMER_INTERVAL / 1000.0;
		double dertY = Vy * TIMER_INTERVAL / 1000.0;
		location=new MyPoint(location.x + dertX,location.y - dertY);

		double dertVy = -gravity * TIMER_INTERVAL / 1000.0;
		Vy+=dertVy;
		
		if(location.y>wallPanelSize.height)
		{
			useAble=false;
		}
	}
	
	public void paint(Graphics g,ImageObserver observer)
	{
		if(useAble)
		{
			if(!hitted)
			{
				g.drawImage(images[type],(int)location.x-size.width/2,(int)location.y-size.height/2,observer);
			}
			else
			{
				g.setColor(Color.GREEN);
				g.fillRect((int)location.x-hittedProcessSize/2-2, (int)location.y-hittedProcessSize/2-2,hittedProcessSize+4,hittedProcessSize+4);
							
				g.setColor(Color.WHITE);
				g.fillRect((int)location.x-hittedProcessSize/2, (int)location.y-hittedProcessSize/2,hittedProcessSize,hittedProcessSize);
				
				hittedProcessSize-=2;
				if(hittedProcessSize<=0)
				{
					useAble=false;
				}
			}
		}
	}

	public boolean isUseAble() {
		return useAble;
	}
	
	public void initialize(Point brickCenter)
	{
		this.location=new MyPoint(brickCenter.x-size.width/2,brickCenter.y-size.height/2);
		
		double arc=Math.random()*2*Math.PI;
		double newVx=rewardAverageVelocity*Math.cos(arc);
		double newVy=rewardAverageVelocity*Math.sin(arc);
		
		this.Vx=newVx;
		this.Vy=newVy;
		this.useAble=true;
	}
	
	public boolean contains(Point point)
	{
		Rectangle rect=new Rectangle();
		rect.setLocation(location.getPoint());
		rect.setSize(size);
		
		return rect.contains(point);
	}

	public int getType() {
		return type;
	}

	public boolean isHitted() {
		return hitted;
	}

	public void setHitted(boolean hitted) {
		this.hitted = hitted;
	}
	

}
