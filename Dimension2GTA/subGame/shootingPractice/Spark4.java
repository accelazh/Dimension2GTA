package subGame.shootingPractice;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import utilities.MyPoint;


public class Spark4 
{
	private static final int ballRadius=ShootingPractice6.ballRadius;
	private static final int TIMER_INTERVAL=ShootingPractice6.TIMER_INTERVAL;
	private static final int sparkLineLength=20;
	private static final double sparkBallVelocity=1000;
	private static final int sparkBallNum=5;
	private static final int turnArcDensity=60;
	
	private static final double gravity=ShootingPractice6.gravity;
	
	SparkBall[] sparkBalls=null;
	SparkFlash3 sparkFlash3=null;
		
	private boolean useAble=true;
	public Spark4(MyPoint hitPoint)// hitPoint是JAVA坐标系的点
	{
		//生成sparkBall
		int num=(int)(Math.random()*sparkBallNum+sparkBallNum/2.0);
	    double turnArc=2*Math.PI/turnArcDensity;
	    double scatterArc=Math.PI/4;
	 
	    Point3D sparkStartPoint=new Point3D(0,hitPoint.x,-hitPoint.y);
	    	    
	    sparkBalls=new SparkBall[3*num];
	    for(int i=0;i<num;i++)
	    {
	        double Rx=Math.cos(scatterArc);
            double Ry=Math.sin(scatterArc)*Math.cos((int)(Math.random()*turnArcDensity)*turnArc);
            double Rz=Math.sin(scatterArc)*Math.sin((int)(Math.random()*turnArcDensity)*turnArc);
            
            double vRate=0.5+Math.random();
            Vector3D v=new Vector3D(sparkBallVelocity*vRate*Rx,sparkBallVelocity*vRate*Ry,sparkBallVelocity*vRate*Rz);
            
            sparkBalls[i]=new SparkBall(sparkStartPoint,v,5);
            
         }
	    
	    scatterArc=Math.PI/8;
		
	    for(int i=num;i<2*num;i++)
	    {
	    	double Rx=Math.cos(scatterArc);
            double Ry=Math.sin(scatterArc)*Math.cos((int)(Math.random()*turnArcDensity)*turnArc);
            double Rz=Math.sin(scatterArc)*Math.sin((int)(Math.random()*turnArcDensity)*turnArc);
            
            double vRate=0.5+Math.random();
            Vector3D v=new Vector3D(sparkBallVelocity*vRate*Rx,sparkBallVelocity*vRate*Ry,sparkBallVelocity*vRate*Rz);
            
            sparkBalls[i]=new SparkBall(sparkStartPoint,v,5);
	    }
	    
	    scatterArc=Math.PI/16;
	    
	    for(int i=2*num;i<3*num;i++)
	    {
	    	double Rx=Math.cos(scatterArc);
            double Ry=Math.sin(scatterArc)*Math.cos((int)(Math.random()*turnArcDensity)*turnArc);
            double Rz=Math.sin(scatterArc)*Math.sin((int)(Math.random()*turnArcDensity)*turnArc);
            
            double vRate=0.5+Math.random();
            Vector3D v=new Vector3D(sparkBallVelocity*vRate*Rx,sparkBallVelocity*vRate*Ry,sparkBallVelocity*vRate*Rz);
            
            sparkBalls[i]=new SparkBall(sparkStartPoint,v,5);
	    }
	    
	    //生成sparkFlash
	    sparkFlash3=new SparkFlash3(new Point((int)(hitPoint.x),(int)(hitPoint.y)));
	}
	
	
	public void selfProcess()
	{
		useAble=false;
		//处理sparkLine
		for(int i=0;i<sparkBalls.length;i++)
		{
			if(sparkBalls[i]!=null)
			{
				sparkBalls[i].selfProcess();
				if(sparkBalls[i].isUseAble())
				{
					useAble=true;
				}
				else
				{
					sparkBalls[i]=null;
				}
		    	
			}
		}
		
		//处理sparkFlash
		if (sparkFlash3 != null) {
			sparkFlash3.selfProcess();
			if (sparkFlash3.isUseAble()) {
				useAble = true;
			} else {
				sparkFlash3 = null;
			}
		}
		
				
		
	}
	
	public void paint(Graphics g)
	{
		//画出sparkLines
		for(int i=0;i<sparkBalls.length;i++)
		{
			if(sparkBalls[i]!=null)
			{
				sparkBalls[i].drawSparkBall(g);
			}
		}
		//画出sparkFlash
		if(sparkFlash3!=null)
		{
			sparkFlash3.paint(g);
		}
	}


	public boolean isUseAble() {
		return useAble;
	}
	
	public void screenMove(int step,boolean up)
	{
		if(up)
		{
			for(int i=0;i<sparkBalls.length;i++)
			{
				if(sparkBalls[i]!=null)
				{
					sparkBalls[i].setHeadPoint(new Point3D(sparkBalls[i].getHeadPoint().x,sparkBalls[i].getHeadPoint().y,sparkBalls[i].getHeadPoint().z-step));
				}
			}
			
			if(sparkFlash3!=null)
			{
				sparkFlash3.setCenter(new Point(sparkFlash3.getCenter().x,sparkFlash3.getCenter().y+step));
			}
			
		}
		else
		{
			for(int i=0;i<sparkBalls.length;i++)
			{
				if(sparkBalls[i]!=null)
				{
					sparkBalls[i].setHeadPoint(new Point3D(sparkBalls[i].getHeadPoint().x,sparkBalls[i].getHeadPoint().y,sparkBalls[i].getHeadPoint().z+step));
				}
			}
			
			if(sparkFlash3!=null)
			{
				sparkFlash3.setCenter(new Point(sparkFlash3.getCenter().x,sparkFlash3.getCenter().y-step));
			}
		}
	}
	

}
