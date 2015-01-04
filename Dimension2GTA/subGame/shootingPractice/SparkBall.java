package subGame.shootingPractice;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import utilities.MyPoint;
import basicConstruction.*;
public class SparkBall 
{

	private static final boolean debug2=false;
	private static final boolean debug3=false;
	
	private static final int TIMER_INTERVAL=ShootingPractice6.TIMER_INTERVAL;
	
	private static final double gravity=ShootingPractice6.gravity;
	
	
	private Vector3D v=null;
	private Point3D headPoint=null;
	
	private boolean useAble=true;
	
	private int averageRadius=20;
	private int headRadius=0;
	public SparkBall(Point3D headPoint, Vector3D v)
	{
		this.headPoint=new Point3D(headPoint.x,headPoint.y,headPoint.z);
		this.v=new Vector3D(v.x,v.y,v.z);
		
		this.headRadius=averageRadius/2+(int)(Math.random()*averageRadius);
	}
	
	public SparkBall(Point3D headPoint, Vector3D v, int averageRadius)
	{
		this.headPoint=new Point3D(headPoint.x,headPoint.y,headPoint.z);
		this.v=new Vector3D(v.x,v.y,v.z);
		
		this.headRadius=averageRadius/2+(int)(Math.random()*averageRadius);
	    this.averageRadius=averageRadius;
	}
	
	public void selfProcess()
	{
		headPoint.x+=v.x*TIMER_INTERVAL/1000;
		headPoint.y+=v.y*TIMER_INTERVAL/1000;
		headPoint.z+=v.z*TIMER_INTERVAL/1000;
		
		v.z-=gravity*TIMER_INTERVAL/1000;
		
	    if(headPoint.z<-ShootingPractice6.wallPanelTotalSize.height-600)
	    {
	       	useAble=false;
	    }
	        
	    
	}
	
	public void drawBall(Graphics g, MyPoint center, int r, double arc)
	{
		g.setColor(Color.YELLOW);
		g.fillOval((int)center.x-r,(int)center.y-r,2*r,2*r);
		
		int r2=r*3/4;
		MyPoint center2=new MyPoint(center.x-(r-r2)*Math.cos(arc),center.y+(r-r2)*Math.sin(arc));
		g.setColor(Color.WHITE);
		g.fillOval((int)center2.x-r2,(int)center2.y-r2,2*r2,2*r2);
    }
	
	public void drawBallLine(Graphics g, MyPoint headCenter, double Vyz, double arc)
	{
		double dertLength=Vyz*0.01;
				
		int n=3;
		for(int i=n;i>=0;i--)
		{
			MyPoint center=new MyPoint(headCenter.x+dertLength*i*Math.cos(arc),
					headCenter.y-dertLength*i*Math.sin(arc));
		    int currentRadius=(int)(1.0*headRadius*(n+1-i)/(n+1));
		    
		    drawBall(g,center,currentRadius,arc);
		
		}
		
	}
	
	public void drawSparkBall(Graphics g)
	{
		if(false==useAble)
		{
			return;
		}
		
		double arc=v.get2DArc()+Math.PI;
		MyPoint startPoint=new MyPoint(headPoint.y,-headPoint.z);
		double Vyz=Math.pow(v.y*v.y+v.z*v.z,0.5);
		
		drawBallLine(g,startPoint,Vyz,arc);
		
		
	}

	public Vector3D getV() {
		return v;
	}

	public Point3D getHeadPoint() {
		return headPoint;
	}

	public boolean isUseAble() {
		return useAble;
	}

	public void setHeadPoint(Point3D headPoint) {
		this.headPoint = headPoint;
	}
}
