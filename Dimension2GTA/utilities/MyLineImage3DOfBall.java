package utilities;

public class MyLineImage3DOfBall extends MyLineImage3D
{
	//for debug
	private static final boolean debug=true;
	
	private double radius=120;

	@Override
	protected double imageRadius(double arcThita, double arcFai) 
	{
		if(debug)
		{
			System.out.println("====in imageRadius of MyLineImage3DOfBall====");
		    System.out.println("radius: "+getRadius());
		}
		
		if(debug)
		{
			System.out.println("====end of imageRadius of MyLineImage3DOfBall====");
		}
			
		return radius;
    }

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	
}
