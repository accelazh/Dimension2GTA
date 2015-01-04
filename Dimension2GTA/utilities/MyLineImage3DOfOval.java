package utilities;

public class MyLineImage3DOfOval extends MyLineImage3D
{
	//for debug
	private static final boolean debug=false;
	
	private double a=100;
	private double b=150;
	private double c=60;

	@Override
	protected double imageRadius(double arcThita, double arcFai) 
	{
		if(debug)
		{
			System.out.println("====in imageRadius of MyLineImage3DOfOval====");
		}
		
		double right=a*a*b*b*c*c;
		double left=Math.pow(Math.sin(arcFai)*Math.cos(arcThita)*b*c,2)
		    +Math.pow(Math.sin(arcFai)*Math.sin(arcThita)*a*c,2)
		    +Math.pow(Math.cos(arcFai)*a*b,2);
		
		if(debug)
		{
			System.out.println("right: "+right);
			System.out.println("left: "+left);
			
			System.out.println("left item1: "+Math.pow(Math.sin(arcFai)*Math.cos(arcThita)*b*c,2));
			System.out.println("left item2: "+Math.pow(Math.sin(arcFai)*Math.sin(arcThita)*a*c,2));
			System.out.println("left item3: "+Math.pow(Math.cos(arcFai)*a*b,2));
		}
		
		if(debug)
		{
			System.out.println("====end of imageRadius of MyLineImage3DOfOval====");
		}
		
		return Math.pow(right/left,0.5);
    }

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}

		
}
