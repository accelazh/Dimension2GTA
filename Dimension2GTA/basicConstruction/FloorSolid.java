package basicConstruction;

import gameDisplayProcessor.MainGameWindow;

import java.awt.Point;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import utilities.MyPoint;
import weapon.Bullet;
import weapon.Fists;

public class FloorSolid extends Solid implements Building
{

	public FloorSolid() 
	{
		this(new ImageIcon("pic/default1.jpg"),new MyPoint(700,200));
	}

	public FloorSolid(Icon surfaceImage, MyPoint location)
	{
		super(surfaceImage,location,100,true);
	}
	
	public boolean isAccesible(MyPoint point) 
	{
		return true;
	}
	
	public boolean isAccesible(Point point) 
	{
		return true;
	}
	
	public boolean isAccesibleFromMap(MyPoint point)  //这里的点指相对大地图左上角的位置
	{
		return true;
	}
	
	public boolean isAccesibleFromMap(Point point)
	{
		return true;
	}
	
	public void hurtByHegrenade(MyPoint explosionPoint)
	{
		
	}
	
	
	public void hittedByFists(Fists fists)
	{
		
	}
	public void hittedByBullet(Bullet bullet) 
	{
		
	}
	
	public void hurtByValue(int value)
	{
		
	}
	public void hurtByMine(MyPoint point,int hurtValue)
	{
		
	}
	public void hurtByRocket(MyPoint point) 
	{
		
		
	}
}
