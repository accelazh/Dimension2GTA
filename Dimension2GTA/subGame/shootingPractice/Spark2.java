package subGame.shootingPractice;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import utilities.MyPoint;


public class Spark2 
{
	//for debug
	private static final boolean debug=false;
	private static final boolean debug2=false;
	
	private static final int ballRadius=ShootingPractice6.ballRadius;
	private static final int TIMER_INTERVAL=ShootingPractice6.TIMER_INTERVAL;
	private static final int sparkLineLength=40;
	private static final double sparkLineVelocity=400;
	private static final int sparkLineNum=6;
	
	private static final double gravity=ShootingPractice6.gravity;
	
	SparkPiece[] sparkLines=null;
	SparkFlash sparkFlash=null;
	JPanel wallPanel=null;
	
	private boolean useAble=true;
	
	public Spark2(MyPoint hitPoint,JPanel wallPanel)// hitPoint是JAVA坐标系的点
	{
		this.wallPanel=wallPanel;
		
		//生成sparkLine
		int num=(int)(Math.random()*sparkLineNum+sparkLineNum/2.0);
	    double turnArc=2*Math.PI/num;
	    double scatterArc=Math.PI/4;
	 
	    Point3D sparkStartPoint=new Point3D(0,hitPoint.x,-hitPoint.y);
	    	    
	    Point3D hitPoint3D=new Point3D(ballRadius,0,0);
	    
	    sparkLines=new SparkPiece[3*num];
	    for(int i=0;i<num;i++)
	    {
	        double Rx=Math.cos(scatterArc);
            double Ry=Math.sin(scatterArc)*Math.cos(i*turnArc);
            double Rz=Math.sin(scatterArc)*Math.sin(i*turnArc);
            
            double vRate=0.5+Math.random();
            Vector3D v=new Vector3D(sparkLineVelocity*vRate*Rx,sparkLineVelocity*vRate*Ry,sparkLineVelocity*vRate*Rz);
            
            sparkLines[i]=new SparkPiece(sparkStartPoint,v,i);
            
            if(debug)
            {
            	System.out.println(sparkLines[i].getV());
            }
	    }
	    
	    scatterArc=Math.PI/8;
		
	    for(int i=num;i<2*num;i++)
	    {
	    	double Rx=Math.cos(scatterArc);
            double Ry=Math.sin(scatterArc)*Math.cos(i*turnArc);
            double Rz=Math.sin(scatterArc)*Math.sin(i*turnArc);
            
            double vRate=0.5+Math.random();
            Vector3D v=new Vector3D(sparkLineVelocity*vRate*Rx,sparkLineVelocity*vRate*Ry,sparkLineVelocity*vRate*Rz);
            
            sparkLines[i]=new SparkPiece(sparkStartPoint,v,i);
            
            if(debug)
            {
            	System.out.println(sparkLines[i].getV());
            }
	    }
	    
	    scatterArc=Math.PI/16;
	    
	    for(int i=2*num;i<3*num;i++)
	    {
	    	double Rx=Math.cos(scatterArc);
            double Ry=Math.sin(scatterArc)*Math.cos(i*turnArc);
            double Rz=Math.sin(scatterArc)*Math.sin(i*turnArc);
            
            double vRate=0.5+Math.random();
            Vector3D v=new Vector3D(sparkLineVelocity*vRate*Rx,sparkLineVelocity*vRate*Ry,sparkLineVelocity*vRate*Rz);
            
            sparkLines[i]=new SparkPiece(sparkStartPoint,v,i);
            
            if(debug)
            {
            	System.out.println(sparkLines[i].getV());
            }
	    }
	    
	    //生成sparkFlash
	    sparkFlash=new SparkFlash(new Point((int)(hitPoint.x),(int)(hitPoint.y)));
	}
	
	public Spark2(MyPoint hitPoint,JPanel wallPanel,boolean b)// hitPoint是JAVA坐标系的点
	{
		this.wallPanel=wallPanel;
		
		//生成sparkLine
		int num=(int)(Math.random()*sparkLineNum+sparkLineNum/2.0);
	   	    
	    sparkLines=new SparkPiece[num];
	    for(int i=0;i<num;i++)
	    {
	        sparkLines[i]=null;
                       
	    }
	    
	    
	    
	    //生成sparkFlash
	    sparkFlash=new SparkFlash(new Point((int)(hitPoint.x),(int)(hitPoint.y)));
	}
	
	public void selfProcess()
	{
		//处理sparkLine
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
