package weapon;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import utilities.Item;
import utilities.MyPoint;

import basicConstruction.Building;
import basicConstruction.Human;
import basicConstruction.MapContainer;
import basicConstruction.Solid;
import basicConstruction.VectorClass;

public class Rocket extends Solid
implements Weapon,ActionListener,Item
{
	//data field
	public static final int rocketSize=10;
	
	private Timer timer;  //���Timer�����������׵Ķ���
	private final int TIMER_INTERVAL=100;
	
	private MapContainer landMapContainer;
	
    private final double  acceleration=4000;  //�����ļ��ٶȳ���
    private final double dertA=0;   // �����ļ��ٶȵ�����
    
    public static final int killRadius=100;
    public static final int killLevel=300;
    
    private boolean isCollided=false;
    
    private final int DELETE_TIMES=20;
    private int deleteCounter=0;
    
    
    
    //methods
	public Rocket( MyPoint location,VectorClass velocity,double arc )
	{
		super(new ImageIcon("pic/weapon/rocketLauncher/rocket.jpg"),location,9999,true);
		setMass(2);
		setSolidName("Rocket");
		setVelocity(velocity);
		setAcceleration(new VectorClass(acceleration,arc));
		
		timer=new Timer(TIMER_INTERVAL,this);
	
	}
	
	
	
	public void initialize(MapContainer landMapContainer)  //���������MainGameWindow����
	{
		this.landMapContainer=landMapContainer;
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		
		//�������ٶ�
		setAcceleration(new VectorClass(getAcceleration().getAbsoluteValue()+dertA,getAcceleration().getArc()));
		
		//�����켣��
		
		landMapContainer.addSpark1(this.getDoubleCenterPoint());
	
		
		//�����ж��Ƿ�ñ�ը
		
		if(isCollided)
		{
			setSolidVisible(false);
			timer.stop();
			
			//������ը�Ļ�
		    Rectangle explodeRect=new Rectangle();  //�õ�ɱ�����ľ���
		    explodeRect.setSize(2*killRadius,2*killRadius);
		    explodeRect.setLocation(new Point((int)(getDoubleCenterPoint().x)-killRadius,
		    		(int)(getDoubleCenterPoint().y)-killRadius));
		    
		    MyPoint sparkLocation=null;
		   	    
		    for(int n=0;n<30;n++)
		    {
		    	double l=Math.random()*killRadius*((Math.random()<0.5)?1:(-1));
		        double h=Math.random()*killRadius*((Math.random()<0.5)?1:(-1));
		    	
		        sparkLocation=new MyPoint(getDoubleCenterPoint().x+l,getDoubleCenterPoint().y+h);
		    		
		    	landMapContainer.addSpark1(sparkLocation);
		    	
		      
		    }
			//����ը�˵�solids
			
		    Solid[] damagedSolids=landMapContainer.SolidsInRectangle(explodeRect);
		    for(int j=0;j<damagedSolids.length;j++) 
		    {
		    	if((damagedSolids[j]!=null)&&(damagedSolids[j]!=this)) 
		    	{
		    		damagedSolids[j].hurtByRocket(getDoubleCenterPoint());
		    	}
		    }
			//��landMapContainer��solidList�а��Լ�ɾ��
		  
		    landMapContainer.removeSolid(this);
		}
		//�������ʱ����ôɾ���Լ�
		deleteCounter++;
		if(DELETE_TIMES<=deleteCounter)
		{
			timer.stop();
			landMapContainer.removeSolid(this);
		}
		
		
	}
	
	
	
	public boolean isCollided() {
		return isCollided;
	}



	public void setCollided(boolean isCollided) {
		this.isCollided = isCollided;
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

	public String toString()
	{
		String output=super.toString();
		return output;
		
	}
	
	 
	public boolean Buy(Human buyer) 
	{
		
		boolean succBuy=false;
		//���buyer�Ƿ���rocketLauncher
		boolean isHasLauncher=false;
		RocketLauncher rocketLauncher=null;
		for(int i=0;i<buyer.getWeaponList().length;i++)
		{
			if(buyer.getWeaponList()[i] instanceof RocketLauncher)
			{
				isHasLauncher=true;
				rocketLauncher=(RocketLauncher)(buyer.getWeaponList()[i]);
			}
		}
		
		//���buyer�Ƿ����㹻��Ǯ
		boolean isEnoughMoney=(buyer.getMoney()>=getPrice())?true:false;
		
		//������
		if(isHasLauncher&&isEnoughMoney)
		{
			rocketLauncher.setNumOfRocket(rocketLauncher.getNumOfRocket()+1);
			buyer.setMoney(buyer.getMoney()-getPrice());
			succBuy=true;
		}
		else
		{
			succBuy=false;
		}
		if(!isHasLauncher)
		{
			JOptionPane.showMessageDialog(null,"Sorry, you haven't a rocket launcher yet!");
		}
		if(!isEnoughMoney)
		{
			JOptionPane.showMessageDialog(null,"Sorry, not enough money!");
		}
		return succBuy;
		
		
	}

	 
	public String getInfo() 
	{
		String output="";
		output+="    The fameous killer weapon, rocket launcher, no more comment needed.";
		output+="The price worth.\n";
		output+="    This weapon can be used when you have rockets.";
		
		return output;
	}

	 
	public ImageIcon getMapPic() 
	{
		return new ImageIcon("pic/showPic/picOnMap/rocket.jpg");
	}

	 
	public ImageIcon getPic() 
	{
		return new ImageIcon("pic/showPic/Rocket.jpg");
	}

	 
	public int getPrice() {
		// TODO Auto-generated method stub
		return 1000;
	}

	 
	public boolean PickUp(Human picker)
	{
		//���buyer�Ƿ���rocketLauncher
		boolean isHasLauncher=false;
		RocketLauncher rocketLauncher=null;
		for(int i=0;i<picker.getWeaponList().length;i++)
		{
			if(picker.getWeaponList()[i] instanceof RocketLauncher)
			{
				isHasLauncher=true;
				rocketLauncher=(RocketLauncher)(picker.getWeaponList()[i]);
			}
		}
		//�������
		if(isHasLauncher)
		{
			rocketLauncher.setNumOfRocket(rocketLauncher.getNumOfRocket()+1);
			return true;
		}
		else
		{
			return false;
		}
		
		
		
	}
	public String getName()
	{
		return "Rocket";
	}

}
