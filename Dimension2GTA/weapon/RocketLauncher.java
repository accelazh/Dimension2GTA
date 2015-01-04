package weapon;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import basicConstruction.Human;
import utilities.*;

public class RocketLauncher 
implements Weapon,ActionListener,Item
{
	

	private int numOfRocket;
	private Timer timer;
	private final int TIMER_INTERVAL=1000;
	private int timeCounter;
	private final int INUSEABLE_TIME=(int)(1.0*1000/TIMER_INTERVAL);
	private boolean useable;   //这个用来看此时能不能从袋子里拿出手雷
	
	public RocketLauncher(int num)
	{
		this.numOfRocket =num;
		timer=new Timer(TIMER_INTERVAL,this);
		timeCounter=0;
	    useable=true;
	}
	
	public RocketLauncher()
	{
		this(0);
	}

	public int getNumOfRocket() {
		return numOfRocket;
	}

	public void setNumOfRocket(int numOfRocket) {
		this.numOfRocket = numOfRocket;
	}
	
	public boolean isUseable() 
	{
		return useable;
	}

	public void setUseableFalse() 
	{
		this.useable = false;
		timeCounter=0;
		timer.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(INUSEABLE_TIME==timeCounter)
		{
			useable=true;
			timer.stop();
			timeCounter=0;
		}
		else
		{
		    timeCounter++;
		}
	}
	 
	public boolean Buy(Human buyer) 
	{
		boolean succBuy=false;
		//检查buyer是否有了这个
		boolean isHas=false;
		for(int i=0;i<buyer.getWeaponList().length;i++)
		{
			if((buyer.getWeaponList())[i] instanceof RocketLauncher)
			{
				isHas=true;
			}
		}
		//检查buyer是否有足够的钱
		boolean isEnoughMoney=(buyer.getMoney()>=getPrice())?true:false;
		
		//处理购买
		if((!isHas)&&(isEnoughMoney))
		{
			buyer.addWeapon(this);
			buyer.setMoney(buyer.getMoney()-getPrice());
			succBuy=true;
		}
		if(isHas)
		{
			JOptionPane.showMessageDialog(null,"Sorry, you've already have it!");
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
		return new ImageIcon("pic/showPic/picOnMap/rocketLauncher.jpg");
	}

	 
	public ImageIcon getPic() 
	{
		return new ImageIcon("pic/showPic/RocketLauncher.jpg");
	}

	 
	public int getPrice() {
		// TODO Auto-generated method stub
		return 35000;
	}

	 
	public boolean PickUp(Human picker)
	{
		//检查buyer是否有了这个
		boolean isHas=false;
		RocketLauncher rocketLauncher=null;
		for(int i=0;i<picker.getWeaponList().length;i++)
		{
			if((picker.getWeaponList())[i] instanceof RocketLauncher)
			{
				isHas=true;
				rocketLauncher=(RocketLauncher)(picker.getWeaponList()[i]);
			}
		}
		
		//处理建起
		if(!isHas)
		{
			picker.addWeapon(this);
		}
		else
		{
			rocketLauncher.setNumOfRocket(rocketLauncher.getNumOfRocket()+1);
		}
		return true;
	}
	public String getName()
	{
		return "Rocket Launcher";
	}
}
