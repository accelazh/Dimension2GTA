package subGame.shootingPractice;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class SparkPiece
{

	private static final boolean debug2=false;
	private static final boolean debug3=false;
    
	
	private static final int TIMER_INTERVAL=ShootingPractice6.TIMER_INTERVAL;
	private static final int sparkLineLength=35;

	private static final double gravity=ShootingPractice6.gravity;
	
	
	private Vector3D v=null;
	private Point3D headPoint=null;
	private double t=0;
	
	private int order=0;
	
	private boolean useAble=true;
	
	public SparkPiece(Point3D headPoint, Vector3D v,int order)
	{
		this.headPoint=new Point3D(headPoint.x,headPoint.y,headPoint.z);
		this.v=new Vector3D(v.x,v.y,v.z);
		this.t=0;
		
		this.order=order;
	}
	
	public void selfProcess()
	{
		this.t+=TIMER_INTERVAL/1000;
		
		headPoint.x+=v.x*TIMER_INTERVAL/1000;
		headPoint.y+=v.y*TIMER_INTERVAL/1000;
		headPoint.z+=v.z*TIMER_INTERVAL/1000;
		
		if(debug2&&order==2)
		{
			System.out.println("====in sparkLine["+order+"]'s selfProcess====");
			if(false)
			{	
				
				System.out.println("v: \n" + v);
			}	
				System.out.println("headPoint.x+=" + v.x * TIMER_INTERVAL/ 1000);
				System.out.println("headPoint.y+=" + v.y * TIMER_INTERVAL/ 1000);
				System.out.println("headPoint.z+=" + v.z * TIMER_INTERVAL/ 1000);
		
		        System.out.println("headPoint: \n"+headPoint);
		}
		
		
		
			v.z-=gravity*TIMER_INTERVAL/1000;
		
	        if(headPoint.z<-ShootingPractice6.wallPanelTotalSize.height-sparkLineLength)
	        {
	        	useAble=false;
	        }
	        
	        if(debug3)
	        {
	        	if(!useAble)
	        	{
	        		System.out.println("sparkLine end");
	        	}
	        }
	}
	
	public void drawSparkLine(Graphics g)
	{
		if(false==useAble)
		{
			return;
		}
		
		Color color1=new Color(128,64,0);
		Color color2=new Color(242,176,4);
		
		double arc=v.get2DArc()+Math.PI;
		int length=(int)(sparkLineLength*v.length2D());
		
		int dynamicLength=length;
		g.setColor(color2);
		Point startPoint=new Point((int)headPoint.y,-(int)headPoint.z);
		
		Point endPoint=new Point(startPoint.x+(int)(dynamicLength*Math.cos(arc)),
				startPoint.y-(int)(dynamicLength*Math.sin(arc)));
		g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
		
		dynamicLength*=2/3.0;
		endPoint=new Point(startPoint.x+(int)(dynamicLength*Math.cos(arc)),
				startPoint.y-(int)(dynamicLength*Math.sin(arc)));
		g.setColor(color1);
		g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
		
		g.setColor(color2);
		if(Math.abs(Math.sin(arc))>Math.abs(Math.cos(arc)))
		{
			endPoint.x=endPoint.x-1;
			startPoint.x=startPoint.x-1;
			g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
			
			endPoint.x=endPoint.x+2;
			startPoint.x=startPoint.x+2;
			g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
		
			endPoint.x=endPoint.x-1;
			startPoint.x=startPoint.x-1;
		}
		else
		{
			if(Math.abs(Math.sin(arc))<Math.abs(Math.cos(arc)))
			{endPoint.y=endPoint.y-1;
			startPoint.y=startPoint.y-1;
			g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
			
			endPoint.y=endPoint.y+2;
			startPoint.y=startPoint.y+2;
			g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
		
			endPoint.y=endPoint.y-1;
			startPoint.y=startPoint.y-1;
			}
			else
			{
				int yOffset=0;
				if(Math.sin(arc)>0)
				{
					yOffset=-1;
				}
				else
				{
					yOffset=1;
				}
				
				int xOffset=0;
				if(Math.cos(arc)>0)
				{
					xOffset=1;
				}
				else
				{
					xOffset=-1;
				}
				
				endPoint.x=endPoint.x+xOffset;
				startPoint.x=startPoint.x+xOffset;
				g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
				endPoint.x=endPoint.x-xOffset;
				startPoint.x=startPoint.x-xOffset;
				
				endPoint.y=endPoint.y+yOffset;
				startPoint.y=startPoint.y+yOffset;
				g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
				endPoint.y=endPoint.y-yOffset;
				startPoint.y=startPoint.y-yOffset;
				
			}
		}	
		
		dynamicLength*=2.0/3;
		endPoint=new Point(startPoint.x+(int)(dynamicLength*Math.cos(arc)),
				startPoint.y-(int)(dynamicLength*Math.sin(arc)));
		g.setColor(color1);
		if(Math.abs(Math.sin(arc))>Math.abs(Math.cos(arc)))
		{
			endPoint.x=endPoint.x-1;
			startPoint.x=startPoint.x-1;
			g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
			
			endPoint.x=endPoint.x+2;
			startPoint.x=startPoint.x+2;
			g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
		
			endPoint.x=endPoint.x-1;
			startPoint.x=startPoint.x-1;
		}
		else
		{
			if(Math.abs(Math.sin(arc))<Math.abs(Math.cos(arc)))
			{endPoint.y=endPoint.y-1;
			startPoint.y=startPoint.y-1;
			g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
			
			endPoint.y=endPoint.y+2;
			startPoint.y=startPoint.y+2;
			g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
		
			endPoint.y=endPoint.y-1;
			startPoint.y=startPoint.y-1;
			}
			else
			{
				int yOffset=0;
				if(Math.sin(arc)>0)
				{
					yOffset=-1;
				}
				else
				{
					yOffset=1;
				}
				
				int xOffset=0;
				if(Math.cos(arc)>0)
				{
					xOffset=1;
				}
				else
				{
					xOffset=-1;
				}
				
				endPoint.x=endPoint.x+xOffset;
				startPoint.x=startPoint.x+xOffset;
				g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
				endPoint.x=endPoint.x-xOffset;
				startPoint.x=startPoint.x-xOffset;
				
				endPoint.y=endPoint.y+yOffset;
				startPoint.y=startPoint.y+yOffset;
				g.drawLine(startPoint.x,startPoint.y,endPoint.x,endPoint.y);
				endPoint.y=endPoint.y-yOffset;
				startPoint.y=startPoint.y-yOffset;
				
			}
		}	
		
		
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
