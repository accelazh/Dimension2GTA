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
	//���Ǹ��࣬������ڰ�functionZones�У�����ʵ�ֲ�ͬ�Ĺ���
	//functionZone����Ҫ�����Ծ���: λ�ã���С��ͼƬ���ɼ����,�Ƿ�ҪuseRequest���ܴ���
	
	//data field
	private boolean isUseNeeded=true;   //��ʾ��human����useRequest��ʱ�����Ч
	
	
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
	
	public boolean isAccesibleFromMap(MyPoint point)  //����ĵ�ָ��Դ��ͼ���Ͻǵ�λ��
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
	
	//��MainGameWindow�����ô���functionPerformed����ʹ�������Ȩ��
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
