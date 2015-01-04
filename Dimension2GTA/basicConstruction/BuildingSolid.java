package basicConstruction;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import utilities.MyPoint;
import weapon.*;

public class BuildingSolid extends Solid implements Building
{
	public BuildingSolid() 
	{
		this(new ImageIcon("pic/default1.jpg"),new MyPoint(100,100),100,true);
	}

	public BuildingSolid(Icon surfaceImage, MyPoint location, int healthPoint,
			boolean solidVisible) 
	{
		super(surfaceImage,location,healthPoint,solidVisible);
		
	}
	
	public BuildingSolid(Icon surfaceImage, MyPoint location) 
	{
		super(surfaceImage,location,100,true);
		
	}
	public void hittedByBullet(Bullet bullet) 
	{
		
	}
	
	public void hurtByHegrenade(MyPoint explosionPoint)
	{
		
	}
	public void hittedByFists(Fists fists)
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
