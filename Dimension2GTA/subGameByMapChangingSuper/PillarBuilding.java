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
	
	public boolean isAccesible(MyPoint point) // ����ĵ�ָ���ͼƬ���Ͻǵ�λ�ã���ӳ������һ���ܲ��ܽ���
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
	
	public boolean isAccesible(Point point) // ����ĵ�ָ���ͼƬ���Ͻǵ�λ�ã���ӳ������һ���ܲ��ܽ���
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

	public boolean isAccesibleFromMap(MyPoint point)  //����ĵ�ָ��Դ��ͼ���Ͻǵ�λ��
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
