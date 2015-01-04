package weapon;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import basicConstruction.*;
import utilities.*;

//这个指有戏中的防弹衣
public class Vest implements Item
{
	//date field
	private double rate=0.6;  //穿上防弹衣后减血要乘以这个比率
	
	private int health=200;   //指防弹衣的耐久度，单位和human的health一样，为零后就自动消失
	
	
	public Vest(double rate,int health)
	{
		if((rate<=1)&&(rate>=0))
		{
			if(health>0)
			{
				this.rate=rate;
				this.health=health;
			}
			else
			{
				this.rate=0.6;
				this.health=200;
			}
		}
		else
		{
			this.rate=0.6;
			this.health=200;
		}
	}
	
	public Vest()
	{
		this.rate=0.6;
		this.health=200;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	 
	public boolean Buy(Human buyer) 
	{
		boolean succBuy=false;
		
		//检查buyer是否有足够的钱
		boolean isEnoughMoney=(buyer.getMoney()>=getPrice())?true:false;
		
		//处理购买
		if(isEnoughMoney)
		{
			buyer.setVest(this);
			buyer.setMoney(buyer.getMoney()-getPrice());
			succBuy=true;
		}
		
		if(!isEnoughMoney)
		{
			JOptionPane.showMessageDialog(null,"Sorry, not enough money!");
		}
		return succBuy;
	}

	 
	public String getInfo() 
	{
		String output="==== VEST ====\n";
		output+="    This Item helps you to reduce the damage by bullets and grenades. ";
		output+="Some of them more while others may less. ";
		output+="Take care that the vest would be broken when its health points decrease to 0. \n";
		
		output+="\n"+toString();
		
		return output;
	}

	 
	public ImageIcon getMapPic()
	{
		return new ImageIcon("pic/showPic/picOnMap/vest.jpg");
	}

	 
	public ImageIcon getPic() 
	{
		return new ImageIcon("pic/showPic/Vest.jpg");
	}

	 
	public int getPrice() 
	{
		return (int)(health*10+3000/rate);
	}

	 
	public boolean PickUp(Human picker) 
	{
		picker.setVest(this);
		return true;
	}
	
	public String toString()
	{
		String output="";
		output+="Damage decrease by: "+(int)(100*(1-rate))+"%\n";
		output+="HealthPoint: "+health+"\n";
	
		return output;
	}
	
	public String getName()
	{
		return "Vest";
	}
	
}