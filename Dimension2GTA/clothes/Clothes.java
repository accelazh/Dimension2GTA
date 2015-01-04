package clothes;

import javax.swing.*;

import basicConstruction.Human;

import java.awt.*;
import utilities.*;
import weapon.AK47;
import nameConstants.*;


public class Clothes implements Item
{
	private String name="";
	private ImageIcon imageIcon=null;
	private int price=0;
	
	private int nameCode=-1;

	public int getNameCode() {
		return nameCode;
	}

	public Clothes(int nameCode)
	{
		this.nameCode=nameCode;
		
		switch(nameCode)
		{
		    case NameConstants.CLOTHES_PLAYER_STANDARD:
		    {
			    name="Player Standard Suit";
			    imageIcon=new ImageIcon("pic/clothes/playerStandard.jpg");
			    price=100;
			    break;
		    }
		    case NameConstants.CLOTHES_NPC_STANDARD:
		    {
			    name="NPC Standard Suit";
			    imageIcon=new ImageIcon("pic/clothes/NPCStandard.jpg");
			    price=100;
			    break;
		    }
		    case NameConstants.CLOTHES_1: 
		    {
		        name="Clothes 1";
		        imageIcon=new ImageIcon("pic/clothes/c1.jpg");
		        price=200;
		        break;
		    }
		    case NameConstants.CLOTHES_2: 
		    {
		        name="Clothes 2";
		        imageIcon=new ImageIcon("pic/clothes/c2.jpg");
		        price=300;
		        break;
		    }
		    case NameConstants.CLOTHES_3: 
		    {
		        name="Clothes 3";
		        imageIcon=new ImageIcon("pic/clothes/c3.jpg");
		        price=450;
		        break;
		    }
		    case NameConstants.CLOTHES_4: 
		    {
		        name="Clothes 4";
		        imageIcon=new ImageIcon("pic/clothes/c4.jpg");
		        price=200;
		        break;
		    }
		    case NameConstants.CLOTHES_5: 
		    {
		        name="Clothes 5";
		        imageIcon=new ImageIcon("pic/clothes/c5.jpg");
		        price=200;
		        break;
		    }
		    case NameConstants.CLOTHES_6: 
		    {
		        name="Clothes 6";
		        imageIcon=new ImageIcon("pic/clothes/c6.jpg");
		        price=300;
		        break;
		    }
		    case NameConstants.CLOTHES_7: 
		    {
		        name="Clothes 7";
		        imageIcon=new ImageIcon("pic/clothes/c7.jpg");
		        price=200;
		        break;
		    }
		    case NameConstants.CLOTHES_8: 
		    {
		        name="Clothes 8";
		        imageIcon=new ImageIcon("pic/clothes/c8.jpg");
		        price=200;
		        break;
		    }
		    case NameConstants.CLOTHES_9: 
		    {
		        name="Clothes 9";
		        imageIcon=new ImageIcon("pic/clothes/c9.jpg");
		        price=200;
		        break;
		    }
		    case NameConstants.CLOTHES_10: 
		    {
		        name="Clothes 10";
		        imageIcon=new ImageIcon("pic/clothes/c10.jpg");
		        price=100;
		        break;
		    }
		    case NameConstants.CLOTHES_11: 
		    {
		        name="Clothes 11";
		        imageIcon=new ImageIcon("pic/clothes/c11.jpg");
		        price=100;
		        break;
		    }
		    case NameConstants.CLOTHES_12: 
		    {
		        name="Clothes 12";
		        imageIcon=new ImageIcon("pic/clothes/c12.jpg");
		        price=100;
		        break;
		    }
		    case NameConstants.CLOTHES_13: 
		    {
		        name="Clothes 13";
		        imageIcon=new ImageIcon("pic/clothes/c13.jpg");
		        price=100;
		        break;
		    }
		    case NameConstants.CLOTHES_14: 
		    {
		        name="Clothes 14";
		        imageIcon=new ImageIcon("pic/clothes/c14.jpg");
		        price=100;
		        break;
		    }
		    case NameConstants.CLOTHES_15: 
		    {
		        name="Clothes 15";
		        imageIcon=new ImageIcon("pic/clothes/c15.jpg");
		        price=650;
		        break;
		    }
            default:
            {
            	JOptionPane.showMessageDialog(null,"Error in Class Clothes: Constructor Error!");
            	break;
            }
		    
		}
	}
	
	 
	public boolean Buy(Human buyer) 
	{
		boolean succBuy=false;
		//检查buyer是否有这件衣服
		boolean isHas=false;
		for(int i=0;i<buyer.getClothesList().length;i++)
		{
			if((buyer.getClothesList())[i]!=null)
			{
			    if((buyer.getClothesList())[i].getNameCode()==nameCode)
			    {
				    isHas=true;
			    }
			}
		}
		//检查buyer是否有足够的钱
		boolean isEnoughMoney=(buyer.getMoney()>=getPrice())?true:false;
		
		//处理购买
		if((!isHas)&&(isEnoughMoney))
		{
			buyer.addClothes(this);
			buyer.setClothes(this);
			buyer.setMoney(buyer.getMoney()-getPrice());
		    succBuy=true;
		}
		else
		{
			succBuy=false;
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
		
		return "    Clothes "+getName()+"\n"+
		"    Choosing a suit that fit well contributes.";
	}

	//这个方法不再允许使用，因为的图上不设置捡取clothes的地方
	public ImageIcon getMapPic() 
	{
		JOptionPane.showMessageDialog(null,"Error, Class Clothes's method getMapPic is not allowed to use.");
		return null;
	}

	 
	public String getName()
	{
		return name;
	}

	 
	public ImageIcon getPic() 
	{
		
		return imageIcon;
	}

	 
	public int getPrice()
	{
		return price;
	}
	

    //这个方法不再允许使用，因为的图上不设置捡取clothes的地方
	public boolean PickUp(Human picker)
	{
		JOptionPane.showMessageDialog(null,"Error, Class Clothes's method PickUp is not allowed to use.");
		return false;
	}

}
