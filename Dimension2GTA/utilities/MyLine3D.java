package utilities;

import gameDisplayProcessor.ControlSetting;

import java.awt.*;

public class MyLine3D 
{
	//for debug
	private static final boolean debug23=ControlSetting.debug23;
	
	private MyPoint3D p1;
	private MyPoint3D p2;
	private Color color=Color.BLUE;
	
	public MyLine3D(MyPoint3D p1,MyPoint3D p2)
	{
		this.p1=p1;
		this.p2=p2;
	}
	
	public void move(MyVector3D v)
	{
		if(null==v)
		{
			return;
		}
		
		if(p1!=null)
		{
			if(p2!=null)
			{
				p1.move(v);
				p2.move(v);
			}
		}
		
		
	}
	
	public void paint (Graphics g, PlaneCoordinateSystemIn3D plane)
	{
		if((null==p1)||(null==p2))
		{
			return;
		}
		
		if(debug23)
		{
			System.out.println("\n====in method paint of MyLine3D====");
			System.out.println("p1: "+p1);
			System.out.println("p2: "+p2);
		}
		
		g.setColor(color);
		Point point1=p1.to2DPoint(plane);
		Point point2=p2.to2DPoint(plane);
	
		if(debug23)
		{
			System.out.println("point1: "+point1);
			System.out.println("point2: "+point2);
		
		}
		g.drawLine(point1.x,point1.y, point2.x,point2.y);
		
		if(debug23)
		{
			System.out.println("====end of method paint of MyLine3D====\n");
		}
	}

	public MyPoint3D getP1() {
		return p1;
	}

	public void setP1(MyPoint3D p1) {
		this.p1 = p1;
	}

	public MyPoint3D getP2() {
		return p2;
	}

	public void setP2(MyPoint3D p2) {
		this.p2 = p2;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	

}
