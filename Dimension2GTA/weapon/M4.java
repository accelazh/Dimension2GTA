package weapon;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import nameConstants.NameConstants;

import basicConstruction.*;

public class M4 extends Gun
{
	public M4(Human owner)
	{
		super("M4A1",owner,65,4,0.06,2.3,30,90,NameConstants.WEAPON_GUN_M4);
	}

	@Override
	public boolean Buy(Human buyer)
	{
		boolean succBuy=false;
		//检查buyer是否有这把枪
		boolean isHas=false;
		for(int i=0;i<buyer.getWeaponList().length;i++)
		{
			if((buyer.getWeaponList())[i] instanceof M4)
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
			this.setOwner(buyer);
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

	@Override
	public int getPrice() 
	{
		
		return 3410;
	}

	@Override
	public boolean PickUp(Human picker) 
	{
		//检查buyer是否有这把枪
		boolean isHas=false;
		Gun gun=null;
		for(int i=0;i<picker.getWeaponList().length;i++)
		{
			if((picker.getWeaponList())[i] instanceof M4)
			{
				isHas=true;
				gun=(Gun)((picker.getWeaponList())[i]);
			}
		}
		
		//处理捡起
		if(!isHas)
		{
			picker.addWeapon(this);
			this.setOwner(picker);
		}
		else
		{
			gun.setNumOfLeft(gun.getNumOfLeft()+this.getNumOfLeft());
		}
		return true;
	}
	
	public ImageIcon getMapPic() 
	{
		return new ImageIcon("pic/showPic/picOnMap/m4.jpg");
	}
	
	public String getName()
	{
		return getGunName();
	}
}
