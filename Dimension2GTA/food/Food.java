package food;

import javax.swing.*;

import basicConstruction.Human;

import java.awt.*;
import utilities.*;
import weapon.AK47;
import nameConstants.*;


public class Food implements Item
{

	private String name="";
	private ImageIcon imageIcon=null;
	private int price=0;
	private int points=0;   //恢复生命的多少,并增加一般多的脂肪
	
	private int nameCode=-1;
	
	public Food(int nameCode)
	{
		this.nameCode=nameCode;
		
		switch(nameCode)
		{
		case NameConstants.FOOD_0 :{
		    name="Humburger Small";
		    imageIcon=new ImageIcon("pic/food/food0.jpg");
		    price=5;
		    points=20;
		    break;
		}
		
		 case NameConstants.FOOD_1 :{
			    name="Humburger Small extra";
			    imageIcon=new ImageIcon("pic/food/food1.jpg");
			    price=8;
			    points=35;
			    break;
			}

			 case NameConstants.FOOD_2 :{
			    name="Humburger Middle";
			    imageIcon=new ImageIcon("pic/food/food2.jpg");
			    price=10;
			    points=50;
			    break;
			}

			 case NameConstants.FOOD_3 :{
			    name="Humburger Big";
			    imageIcon=new ImageIcon("pic/food/food3.jpg");
			    price=20;
			    points=100;
			    break;
			}

			 case NameConstants.FOOD_4 :{
			    name="Humburger Huge";
			    imageIcon=new ImageIcon("pic/food/food4.jpg");
			    price=45;
			    points=225;
			    break;
			}

			 case NameConstants.FOOD_5 :{
			    name="Chicken Small ";
			    imageIcon=new ImageIcon("pic/food/food5.jpg");
			    price=5;
			    points=20;
			    break;
			}

			 case NameConstants.FOOD_6 :{
			    name="Chicken Small Smart";
			    imageIcon=new ImageIcon("pic/food/food6.jpg");
			    price=8;
			    points=35;
			    break;
			}

			 case NameConstants.FOOD_7 :{
			    name="Chicken Middle";
			    imageIcon=new ImageIcon("pic/food/food7.jpg");
			    price=15;
			    points=55;
			    break;
			}

			 case NameConstants.FOOD_8 :{
			    name="Chicken Big";
			    imageIcon=new ImageIcon("pic/food/food8.jpg");
			    price=30;
			    points=75;
			    break;
			}

			 case NameConstants.FOOD_9 :{
			    name="Chicken Huge";
			    imageIcon=new ImageIcon("pic/food/food9.jpg");
			    price=55;
			    points=275;
			    break;
			}
			 
			 default :
			 {
				 JOptionPane.showMessageDialog(null,"Error in Class Food: Constructor Error!");
	             break;
			 }

		
		}
	}

	 
	public boolean Buy(Human buyer) 
	{
		
		
		
		//检查buyer是否有足够的钱
		boolean isEnoughMoney=(buyer.getMoney()>=getPrice())?true:false;
		
		//处理购买
		if(isEnoughMoney)
		{
			buyer.setHealthPoint(buyer.getHealthPoint()+points);
			buyer.setMoney(buyer.getMoney()-getPrice());
			buyer.setFat(buyer.getFat()+points/2);
		    return true;
		}
		else
		{
			if(!isEnoughMoney)
		    {
			    JOptionPane.showMessageDialog(null,"Sorry, not enough money!");
		    }
			return false;
		}
		
		
	}

	 
	public String getInfo() 
	{
		
		return "Food: "+name+"\n"+"Tips: food can bring back some of your health points\n";
	}

	 
	//这个方法不再允许使用，因为的图上不设置捡取clothes的地方
	public ImageIcon getMapPic() 
	{
		JOptionPane.showMessageDialog(null,"Error, Class Food's method getMapPic is not allowed to use.");
		return null;
	}

	 
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	 
	public ImageIcon getPic() {
		// TODO Auto-generated method stub
		return imageIcon;
	}

	 
	public int getPrice() {
		// TODO Auto-generated method stub
		return price;
	}

	 
	 //这个方法不再允许使用，因为的图上不设置捡取clothes的地方
	public boolean PickUp(Human picker)
	{
		JOptionPane.showMessageDialog(null,"Error, Class Food's method PickUp is not allowed to use.");
		return false;
	}
}
