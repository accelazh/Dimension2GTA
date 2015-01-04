package basicConstruction;

import java.awt.*;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import utilities.MyPoint;
import weapon.Bullet;
import weapon.Fists;

import gameDisplayProcessor.*;

public abstract class FunctionZone extends Solid implements Building
{
	//这是父类，子类放在包functionZones中，用以实现不同的功能
	//functionZone最重要的属性就是: 位置，大小，图片，可见与否,是否要useRequest才能触发
	
	//data field
	private boolean isUseNeeded=true;   //表示在human发出useRequest的时候才生效
	
	
	//methods
	public FunctionZone() 
	{
		this(new ImageIcon("pic/default1.jpg"),new MyPoint(700,200),100,true);
	}

	public FunctionZone(Icon surfaceImage, MyPoint location, int healthPoint,
			boolean solidVisible)
	{
		super(surfaceImage,location,healthPoint,true);
	}
	
	
	public FunctionZone(Icon surfaceImage, MyPoint location, Dimension totalSize, boolean isVisible, boolean isUseNeeded)
	{
		super(surfaceImage,location,100,isVisible);
		setTotalSize(totalSize);
		this.isUseNeeded=isUseNeeded;
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
	
	//将MainGameWindow的引用传给functionPerformed，以使其有最高权限
	public abstract void functionPerformed(Human whoTriggers, MainGameWindow master);
	
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
	public boolean isUseNeeded() {
		return isUseNeeded;
	}

	public void setUseNeeded(boolean isUseNeeded) {
		this.isUseNeeded = isUseNeeded;
	}

}
