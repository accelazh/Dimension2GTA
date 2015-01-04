package basicConstruction;

import java.awt.*;

import utilities.MyPoint;
import gameDisplayProcessor.*;

public class VectorClass 
{
	//for debug
	private static final boolean debug13=ControlSetting.debug13;
	//private data field
	private double absoluteValue; //向量的模
	private double arc; //以数学中的极坐标系形式描述向量的方向
    
    //methods
	public VectorClass()
	{
		this(0,0);
	}
	public VectorClass(double absoluteValue,double arc)
	{
		if(absoluteValue>0)
		{
		    this.absoluteValue =simplizeNum(absoluteValue);
		    arc=simplizeArc(arc);
		    this.arc=arc;
		}
		else
		{
			if(0==absoluteValue)
			{
				this.absoluteValue =0;
				this.arc=0;
			}
			else
			{
				this.absoluteValue=-absoluteValue;
				this.arc=simplizeArc(arc+Math.PI);
			}
		}
	}
	
	public VectorClass(double vx,double vy,boolean b)   //用水平与竖直分量建立对象
	{
		double newAbsoluteValue=Math.pow(vx*vx+vy*vy,0.5);
		double sinArc=vy/newAbsoluteValue;
		double newArc;
		
		if(0==vx&&0==vy)
		{
			this.absoluteValue =0;
			this.arc=0;
		}
		else
		{
		    if(vx>=0)
		    {
			    newArc=Math.asin(sinArc);
		    }
		    else
		    {
		        newArc=Math.PI-Math.asin(sinArc);
		    }

		    this.absoluteValue =simplizeNum(newAbsoluteValue);
			this.arc=simplizeArc(newArc);
		}
		
		
	}
	
	//gets and sets
	public double getAbsoluteValue()
	{
		return absoluteValue;
	}
	public double getArc()
	{
		return arc;
	}
	
	public void setAbsoluteValue(double value)
	{
		this.absoluteValue =simplizeNum(value);
	}
	public void setArc(double arc)
	{
		arc=simplizeArc(arc);
		this.arc=arc;
	}
	
	//other methods
	public VectorClass addition(VectorClass v)
	{
		double vx=absoluteValue*Math.cos(arc)+v.getAbsoluteValue()*Math.cos(v.getArc());
		double vy=absoluteValue*Math.sin(arc)+v.getAbsoluteValue()*Math.sin(v.getArc());
		
		if(0==vx&&0==vy)
		{
			return new VectorClass(0,0);
		}
		else
		{
			double newAbsoluteValue=Math.pow(vx*vx+vy*vy,0.5);
			double sinArc=vy/newAbsoluteValue;
			double newArc;
			
			if(vx>=0)
			{
				newArc=Math.asin(sinArc);
			}
			else
			{
			    newArc=Math.PI-Math.asin(sinArc);
			}
			
			return new VectorClass(newAbsoluteValue,newArc);
		}
		
		
	}
	public VectorClass subtract(VectorClass v)  //自身减去新来的向量
	{
   		v=new VectorClass(v.getAbsoluteValue(),v.getArc()+Math.PI);
   		return addition(v);
	}
	
	public VectorClass decompositionBy(double arc) //沿着arc指定的方向向量分解
	{
		
		double dertArc=this.arc-arc;
		double value=absoluteValue*Math.cos(dertArc);
		
		if(value>=0)
		{
			return new VectorClass(value,arc);
		}
		else
		{
			return new VectorClass(-value,arc+Math.PI);
		}
	}
	
	public double valueDecompositionBy(double arc) //沿着arc指定的方向求投影
	{
		
		double dertArc=this.arc-arc;
		double value=absoluteValue*Math.cos(dertArc);
		return value;
	}
	
	public VectorClass mutiplyBy(double factor)
	{
		if(0==factor)
		{
			return new VectorClass(0,0);
		}
		else
		{
			if(factor>0)
			{
				return new VectorClass(absoluteValue*factor,
						arc);
				
			}
			else
			{
				return new VectorClass(absoluteValue*(-factor),
						arc+Math.PI);
			}
		}
			
	}
	
	public static double simplizeArc(double thisArc)
	{
		while(!((thisArc>=0)&&(thisArc<2*Math.PI)))
		{
			if(thisArc<0)
			{
				thisArc+=Math.PI*2;
			}
			else
			{
				thisArc-=Math.PI*2;
			}
		}
		
		
		return simplizeNum(thisArc);
	}

	public String toString()
	{
		return "absoluteValue : "+simplizeNum(((Math.abs(absoluteValue)<1e-6)?0:absoluteValue))+" ; "
		    +"arc : "+simplizeNum((Math.abs(arc/Math.PI)<1e-6?0:arc/Math.PI))+"PI";
	}

	public static double simplizeNum(double num)
	{
		
		
		return num;
		
	}
	
	//这个方法返回向量AB的关于极坐标系的方向角
	public static double arcOfPoints(MyPoint A,MyPoint B)
	{
		double dertX=B.x-A.x;
		double dertY=B.y-A.y;
		dertY=-dertY;
		double distance=Math.pow(dertX*dertX+dertY*dertY,0.5);
		
		if (0!=distance) 
		{
			double sinArc = dertY / distance;
			double arc;
			if (dertX >= 0) {
				arc = Math.asin(sinArc);
			} else {
				arc = Math.PI - Math.asin(sinArc);
			}
			return arc;
		}
		else
		{
			return 0;
		}
	}
	public static double arcOfPoints(Point A,Point B)
	{
		double dertX=B.x-A.x;
		double dertY=B.y-A.y;
		dertY=-dertY;
		double distance=Math.pow(dertX*dertX+dertY*dertY,0.5);
		
		if(0!=distance)
		{
		
	    	double sinArc=dertY/distance;
	        double arc;
	    	if(dertX>=0)
		    {
		    	arc=Math.asin(sinArc);
		    }
		    else
		    {
		    	arc=Math.PI-Math.asin(sinArc);
		    }
		    return arc;
		    
	    }
		else
		{
			return 0;
		}
    }
}  

