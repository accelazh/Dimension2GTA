package weapon;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import nameConstants.NameConstants;

import basicConstruction.*;

public class SIG552 extends Gun
{
	public SIG552(Human owner)
	{
		super("SIG552",owner,99,5.3,0.06,2.5,30,90,NameConstants.WEAPON_GUN_SIG552);
	}

	@Override
	public boolean Buy(Human buyer)
	{
		boolean succBuy=false;
		//���buyer�Ƿ������ǹ
		boolean isHas=false;
		for(int i=0;i<buyer.getWeaponList().length;i++)
		{
			if((buyer.getWeaponList())[i] instanceof SIG552)
			{
				isHas=true;
			}
		}
		//���buyer�Ƿ����㹻��Ǯ
		boolean isEnoughMoney=(buyer.getMoney()>=getPrice())?true:false;
		
		//������
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
		
		return 3125;
	}

	@Override
	public boolean PickUp(Human picker) 
	{
		//���buyer�Ƿ������ǹ
		boolean isHas=false;
		Gun gun=null;
		for(int i=0;i<picker.getWeaponList().length;i++)
		{
			if((picker.getWeaponList())[i] instanceof SIG552)
			{
				isHas=true;
				gun=(Gun)((picker.getWeaponList())[i]);
			}
		}
		
		//�������
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
		return new ImageIcon("pic/showPic/picOnMap/sig552.jpg");
	}
	
	public String getName()
	{
		return getGunName();
	}
}
