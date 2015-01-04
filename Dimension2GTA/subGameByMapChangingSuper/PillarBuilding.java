package subGameByMapChangingSuper;

import java.awt.Point;

import javax.swing.Icon;

import utilities.MyPoint;

import basicConstruction.*;

public class PillarBuilding extends BuildingSolid
{
	public PillarBuilding(Icon surfaceImage, MyPoint location) 
	{
		super(surfaceImage,location,100,true);
		
	}
	
	public PillarBuilding()
	{
		super();
	}
	
	public boolean isAccesible(MyPoint point) // 这里的点指相对图片左上角的位置，反映给定的一点能不能进入
	{
		double radius=getTotalSize().getWidth()/2.0;
		MyPoint center=new MyPoint(radius,radius);
		double distance=center.distance(point);
		if(distance<=radius)
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
	
	public boolean isAccesible(Point point) // 这里的点指相对图片左上角的位置，反映给定的一点能不能进入
	{
		double radius=getTotalSize().getWidth()/2.0;
		MyPoint center=new MyPoint(radius,radius);
		double distance=center.distance(point);
		if(distance<=radius)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public boolean isAccesibleFromMap(MyPoint point)  //这里的点指相对大地图左上角的位置
	{
		double distance=getDoubleCenterPoint().distance(point);
		double radius=getTotalSize().getWidth()/2.0;
		if(distance<=radius)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public boolean isAccesibleFromMap(Point point)
	{
		double distance=getDoubleCenterPoint().distance(point);
		double radius=getTotalSize().getWidth()/2.0;
		if(distance<=radius)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	

}
