package subGame.shootingPractice;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import basicConstruction.*;
import javax.swing.*;

import utilities.MyPoint;

public class Spark
{
	//for debug
	private static final boolean debug=false;
	private static final boolean debug2=false;
	
	private static final int ballRadius=ShootingPractice6.ballRadius;
	private static final int TIMER_INTERVAL=ShootingPractice6.TIMER_INTERVAL;
	private static final int sparkLineLength=40;
	private static final double sparkLineVelocity=2000;
	private static final int sparkLineNum=20;
	
	private static final double gravity=ShootingPractice6.gravity;
	
	SparkLine[] sparkLines=null;
	SparkFlash sparkFlash=null;
	JPanel wallPanel=null;
	
	private boolean useAble=true;
	public Spark(MyPoint hitPoint, MyPoint ballLocation,JPanel wallPanel)// hitPoint是相对于球的JAVA坐标系的点
	{
		this.wallPanel=wallPanel;
		
		//生成sparkLine
		int num=(int)(Math.random()*sparkLineNum+sparkLineNum/2.0);
	    double turnArc=2*Math.PI/num;
	    double scatterArc=Math.PI/4;
	 
	    Point3D sparkStartPoint=new Point3D(Math.pow((ballRadius*ballRadius-hitPoint.x*hitPoint.x-hitPoint.y*hitPoint.y),0.5),
	    		ballLocation.x+hitPoint.x,
	    		-ballLocation.y-hitPoint.y);
	    
	    Point3D hitPoint3D=new Point3D(Math.pow((ballRadius*ballRadius-hitPoint.x*hitPoint.x-hitPoint.y*hitPoint.y),0.5),
	    		hitPoint.x,-hitPoint.y);
	    
	    sparkLines=new SparkLine[3*num];
	    for(int i=0;i<num;i++)
	    {
	        double Rx=hitPoint3D.x/ballRadius*Math.cos(scatterArc)+Math.pow((ballRadius*ballRadius-hitPoint3D.x*hitPoint3D.x),0.5)/ballRadius*Math.sin(i*turnArc)*Math.sin(scatterArc);
            double Ry=hitPoint3D.y/ballRadius*Math.cos(scatterArc)+hitPoint3D.z/Math.pow((ballRadius*ballRadius-hitPoint3D.x*hitPoint3D.x),0.5)*Math.cos(i*turnArc)*Math.sin(scatterArc)
                -hitPoint3D.x*hitPoint3D.y/(ballRadius*Math.pow((ballRadius*ballRadius-hitPoint3D.x*hitPoint3D.x),0.5))*Math.sin(i*turnArc)*Math.sin(scatterArc);
            double Rz=hitPoint3D.z/ballRadius*Math.cos(scatterArc)-hitPoint3D.y/Math.pow((ballRadius*ballRadius-hitPoint3D.x*hitPoint3D.x),0.5)*Math.cos(i*turnArc)*Math.sin(scatterArc)
                -hitPoint3D.x*hitPoint3D.z/(ballRadius*Math.pow((ballRadius*ballRadius-hitPoint3D.x*hitPoint3D.x),0.5))*Math.sin(i*turnArc)*Math.sin(scatterArc);
            
            double vRate=0.5+Math.random();
            Vector3D v=new Vector3D(sparkLineVelocity*vRate*Rx,sparkLineVelocity*vRate*Ry,sparkLineVelocity*vRate*Rz);
            
            sparkLines[i]=new SparkLine(sparkStartPoint,v,i);
            
            if(debug)
            {
            	System.out.println(sparkLines[i].getV());
            }
	    }
	    
	    scatterArc=Math.PI/8;
		
	    for(int i=num;i<2*num;i++)
	    {
	        double Rx=hitPoint3D.x/ballRadius*Math.cos(scatterArc)+Math.pow((ballRadius*ballRadius-hitPoint3D.x*hitPoint3D.x),0.5)/ballRadius*Math.sin(i*turnArc)*Math.sin(scatterArc);
            double Ry=hitPoint3D.y/ballRadius*Math.cos(scatterArc)+hitPoint3D.z/Math.pow((ballRadius*ballRadius-hitPoint3D.x*hitPoint3D.x),0.5)*Math.cos(i*turnArc)*Math.sin(scatterArc)
                -hitPoint3D.x*hitPoint3D.y/(ballRadius*Math.pow((ballRadius*ballRadius-hitPoint3D.x*hitPoint3D.x),0.5))*Math.sin(i*turnArc)*Math.sin(scatterArc);
            double Rz=hitPoint3D.z/ballRadius*Math.cos(scatterArc)-hitPoint3D.y/Math.pow((ballRadius*ballRadius-hitPoint3D.x*hitPoint3D.x),0.5)*Math.cos(i*turnArc)*Math.sin(scatterArc)
                -hitPoint3D.x*hitPoint3D.z/(ballRadius*Math.pow((ballRadius*ballRadius-hitPoint3D.x*hitPoint3D.x),0.5))*Math.sin(i*turnArc)*Math.sin(scatterArc);
            
            double vRate=0.5+Math.random();
            Vector3D v=new Vector3D(sparkLineVelocity*vRate*Rx,sparkLineVelocity*vRate*Ry,sparkLineVelocity*vRate*Rz);
            
            sparkLines[i]=new SparkLine(sparkStartPoint,v,i);
            
            if(debug)
            {
            	System.out.println(sparkLines[i].getV());
            }
	    }
	    
	    scatterArc=Math.PI/16;
	    
	    for(int i=2*num;i<3*num;i++)
	    {
	        double Rx=hitPoint3D.x/ballRadius*Math.cos(scatterArc)+Math.pow((ballRadius*ballRadius-hitPoint3D.x*hitPoint3D.x),0.5)/ballRadius*Math.sin(i*turnArc)*Math.sin(scatterArc);
            double Ry=hitPoint3D.y/ballRadius*Math.cos(scatterArc)+hitPoint3D.z/Math.pow((ballRadius*ballRadius-hitPoint3D.x*hitPoint3D.x),0.5)*Math.cos(i*turnArc)*Math.sin(scatterArc)
                -hitPoint3D.x*hitPoint3D.y/(ballRadius*Math.pow((ballRadius*ballRadius-hitPoint3D.x*hitPoint3D.x),0.5))*Math.sin(i*turnArc)*Math.sin(scatterArc);
            double Rz=hitPoint3D.z/ballRadius*Math.cos(scatterArc)-hitPoint3D.y/Math.pow((ballRadius*ballRadius-hitPoint3D.x*hitPoint3D.x),0.5)*Math.cos(i*turnArc)*Math.sin(scatterArc)
                -hitPoint3D.x*hitPoint3D.z/(ballRadius*Math.pow((ballRadius*ballRadius-hitPoint3D.x*hitPoint3D.x),0.5))*Math.sin(i*turnArc)*Math.sin(scatterArc);
            
            double vRate=0.5+Math.random();
            Vector3D v=new Vector3D(sparkLineVelocity*vRate*Rx,sparkLineVelocity*vRate*Ry,sparkLineVelocity*vRate*Rz);
            
            sparkLines[i]=new SparkLine(sparkStartPoint,v,i);
            
            if(debug)
            {
            	System.out.println(sparkLines[i].getV());
            }
	    }
	    
	    //生成sparkFlash
	    sparkFlash=new SparkFlash(new Point((int)(ballLocation.x+hitPoint.x),(int)(ballLocation.y+hitPoint.y)));
	}
	
	
	public void selfProcess()
	{
		useAble=false;
		//处理sparkLine
		for(int i=0;i<sparkLines.length;i++)
		{
			if(sparkLines[i]!=null)
			{
				sparkLines[i].selfProcess();
				if(sparkLines[i].isUseAble())
				{
					useAble=true;
				}
				else
				{
					sparkLines[i]=null;
				}
		    	if(debug)
		    	{
				    System.out.println("sparkLines["+i+"].headPoint: ");
				    System.out.println(sparkLines[i].getHeadPoint());
			    }
			}
		}
		
		//处理sparkFlash
		if (sparkFlash != null) {
			sparkFlash.selfProcess();
			if (sparkFlash.isUseAble()) {
				useAble = true;
			} else {
				sparkFlash = null;
			}
		}
		
				
		
	}
	
	public void paint(Graphics g)
	{
		//画出sparkLines
		for(int i=0;i<sparkLines.length;i++)
		{
			if(sparkLines[i]!=null)
			{
				sparkLines[i].drawSparkLine(g);
			}
		}
		//画出sparkFlash
		if(sparkFlash!=null)
		{
			sparkFlash.paint(g,wallPanel);
		}
	}


	public boolean isUseAble() {
		return useAble;
	}
	
	public void screenMove(int step,boolean up)
	{
		if(up)
		{
			for(int i=0;i<sparkLines.length;i++)
			{
				if(sparkLines[i]!=null)
				{
					sparkLines[i].setHeadPoint(new Point3D(sparkLines[i].getHeadPoint().x,sparkLines[i].getHeadPoint().y,sparkLines[i].getHeadPoint().z-step));
				}
			}
			
			if(sparkFlash!=null)
			{
				sparkFlash.setCenter(new Point(sparkFlash.getCenter().x,sparkFlash.getCenter().y+step));
			}
			
		}
		else
		{
			for(int i=0;i<sparkLines.length;i++)
			{
				if(sparkLines[i]!=null)
				{
					sparkLines[i].setHeadPoint(new Point3D(sparkLines[i].getHeadPoint().x,sparkLines[i].getHeadPoint().y,sparkLines[i].getHeadPoint().z+step));
				}
			}
			
			if(sparkFlash!=null)
			{
				sparkFlash.setCenter(new Point(sparkFlash.getCenter().x,sparkFlash.getCenter().y-step));
			}
		}
	}
	
	
	
	
	
}
