package subGame.shootingPractice;

import utilities.MyPoint;
import basicConstruction.VectorClass;

//以数学之交坐标系为准
public class Vector3D
{
	double x=0;
	double y=0;
	double z=0;
	
	public Vector3D()
	{
		
	}
	public Vector3D(double x,double y,double z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		
	}
	
	public double length2D()
	{
		double L1=Math.pow(y*y+z*z,0.5);
	    double L2=Math.pow(y*y+z*z+x*x,0.5);
	    return L1/L2;
	}
	
	public double get2DArc()
	{
		double arc=VectorClass.arcOfPoints(new MyPoint(0,0),new MyPoint(y,-z));
	
		return arc;
	}
	
	public String toString()
	{
		String output="";
		output+="Vector3D: "+"\n";
		output+="x == "+x+"\n";
		output+="y == "+y+"\n";
		output+="z == "+z+"\n";
		
		output+="get2DArc == "+get2DArc()/Math.PI+"PI"+"\n";
		output+="length2D == "+length2D()+"\n";
		
		return output;
	}
	
}	