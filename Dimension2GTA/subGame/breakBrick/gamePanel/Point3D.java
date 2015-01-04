package subGame.breakBrick.gamePanel;

public class Point3D 
{
	double x=0;
	double y=0;
	double z=0;
	
	public Point3D()
	{
		
	}
	public Point3D(double x,double y,double z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		
	}
	
	public String toString()
	{
		String output="";
		output+="Point3D: "+"\n";
		output+="x == "+x+"\n";
		output+="y == "+y+"\n";
		output+="z == "+z+"\n";
		
		return output;
	}

}
