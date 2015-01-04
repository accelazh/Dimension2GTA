package weapon;

import javax.swing.*;

import java.awt.*;
import basicConstruction.*;
import gameDisplayProcessor.*;
import java.awt.event.*;
import java.io.File;
import java.util.Scanner;

import utilities.*;

public class Hegrenade extends Solid 
implements Weapon,ActionListener,Item
{
	//for debug
	private static boolean debug11=ControlSetting.debug11;
	
	//data field
	public static final int hegrenadeSize=10;
	
	private Timer timer;  //���Timer�����������׵Ķ���
	private final int TIMER_INTERVAL=100;
	
	private int rotationCounter;
	private final int rotationCounterSpan=2;
	private int currentRotationState;
	
	private final int EXPLODE_TIME=3*1000/TIMER_INTERVAL;  //���������¼��ը�ĵ���ʱ��ʱ��
	private int explodeCounter;
	
    private MapContainer landMapContainer;
	
    private final double  accelerationAdjustment=2;  //����������������ٶ�
    
    public static final int killRadius=100;
    public static final int killLevel=150;
    
    private final double sparkInterval=5;
    private final int sparkSize=10;
	//methods
	public Hegrenade( MyPoint location,VectorClass velocity )
	{
		super(new ImageIcon("pic/weapon/hegrenade/he0.jpg"),location,9999,true);
		setMass(2);
		setSolidName("Hegrenade");
		
		setVelocity(velocity);
		
		timer=new Timer(TIMER_INTERVAL,this);
		
		rotationCounter=0;
		currentRotationState=0;
		
		explodeCounter=0;
		
	}
	
	
	
	public void initialize(MapContainer landMapContainer)  //���������MainGameWindow����
	{
		this.landMapContainer=landMapContainer;
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		VectorClass newAcceleration=getVelocity().mutiplyBy(-accelerationAdjustment);
		setAcceleration(newAcceleration);
		
		 
	    if(debug11)
	    {
	    	System.out.println("====in method actionPerformed in Hegrenade====");
	    	System.out.println("currentVelocity : ");
	    	System.out.println(getVelocity());
	    	
	    }
		
		rotationCounter++;
		if(rotationCounterSpan==rotationCounter)
		{
			if(0==currentRotationState)
			{
				setSurfaceImage(new ImageIcon("pic/weapon/hegrenade/he1.jpg"));
				currentRotationState=1;
			}
			else
			{
				setSurfaceImage(new ImageIcon("pic/weapon/hegrenade/he2.jpg"));
				currentRotationState=0;
			}
			rotationCounter=0;
			
		}
		
		//�����ж��Ƿ�ñ�ը
		explodeCounter++;
		if(EXPLODE_TIME==explodeCounter)
		{
			explodeCounter=0;
			setSolidVisible(false);
			timer.stop();
			
			//�������ױ�ը�Ļ�
		    Rectangle explodeRect=new Rectangle();  //�õ�ɱ�����ľ���
		    explodeRect.setSize(2*killRadius,2*killRadius);
		    explodeRect.setLocation(new Point((int)(getDoubleCenterPoint().x)-killRadius,
		    		(int)(getDoubleCenterPoint().y)-killRadius));
		    
		    MyPoint sparkLocation=new MyPoint(explodeRect.getLocation());
		    int columnNum=(int)(explodeRect.getWidth()/(sparkSize+sparkInterval))+1;
		    int rowNum=(int)(explodeRect.getHeight()/(sparkSize+sparkInterval))+1;
		    
		    if(debug11)
		    {
		    	
		    	System.out.println("when exploding : ");
		    	
		    }
		    
		    for(int n=0;n<50;n++)
		    {
		    	int j=(int)(Math.random()*rowNum);
		        int k=(int)(Math.random()*columnNum);
		    	
		        sparkLocation=new MyPoint(explodeRect.getLocation().x+k*(sparkSize+sparkInterval),
		    				explodeRect.getLocation().y+j*(sparkSize+sparkInterval));
		    		
		    	landMapContainer.addSpark1(sparkLocation);
		    	
		      
		    }
			//����ը�˵�solids
			
		    Solid[] damagedSolids=landMapContainer.SolidsInRectangle(explodeRect);
		    for(int j=0;j<damagedSolids.length;j++) 
		    {
		    	if((damagedSolids[j]!=null)&&(damagedSolids[j]!=this)) 
		    	{
		    		
		    		
		    		damagedSolids[j].hurtByHegrenade(getDoubleCenterPoint());
		    	}
		    }
		    
			//��landMapContainer��solidList�а�����ɾ��
			int i;
			for(i=0;(i<landMapContainer.getSolidList().length)&&
			(this!=(landMapContainer.getSolidList())[i]);i++)
				;
			if(i<landMapContainer.getSolidList().length)
			{
				landMapContainer.removeSolid(i);
			}
		}
	}
	
	public String toString()
	{
		String output=super.toString();
		return output;
		
	}

	 
	public boolean Buy(Human buyer) 
	{
		boolean succBuy=false;
		//���buyer�Ƿ����㹻��Ǯ
		boolean isEnoughMoney=(buyer.getMoney()>=getPrice())?true:false;
		
		//������
		if(isEnoughMoney)
		{
			buyer.setNumOfHegrenade(buyer.getNumOfHegrenade()+1);
			buyer.setMoney(buyer.getMoney()-getPrice());
			succBuy=true;
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Sorry, not enough money!");
		    succBuy=false;
		}
		return succBuy;
				
	}

	 
	public String getInfo() 
	{
		try 
		{
			File info = new File("pic/showPic/Hegrenade.txt");
			Scanner input = new Scanner(info);
			String output = "";
			while (input.hasNext()) 
			{
				output += input.nextLine();
			}
			return output;
		} 
		catch (Exception ex)
		{
			return "";
		}
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

	 
	public ImageIcon getMapPic() 
	{
		return new ImageIcon("pic/showPic/picOnMap/hegrenade.jpg");
	}

	 
	public ImageIcon getPic() 
	{
		return new ImageIcon("pic/showPic/Hegrenade.jpg");
	}

	 
	public int getPrice() 
	{
		return 300;
	}

	 
	public boolean PickUp(Human picker) 
	{
		picker.setNumOfHegrenade(picker.getNumOfHegrenade()+1);
	    return true;
	}
	
	public String getName()
	{
		return "Hegrenade";
	}
}
