package weapon;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import nameConstants.NameConstants;

import basicConstruction.*;

public class DesertEagle extends Gun
{
	public DesertEagle(Human owner)
	{
		super("Desert-Eagle",owner,65,2.5,0.35,1.3,7,35,NameConstants.WEAPON_GUN_DESERT_EAGLE);
	}
	
	@Override
	public boolean Buy(Human buyer)
	{
		boolean succBuy=false;
		//���buyer�Ƿ������ǹ
		boolean isHas=false;
		for(int i=0;i<buyer.getWeaponList().length;i++)
		{
			if((buyer.getWeaponList())[i] instanceof DesertEagle)
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
		
		return 700;
	}

	@Override
	public boolean PickUp(Human picker) 
	{
		//���buyer�Ƿ������ǹ
		boolean isHas=false;
		Gun gun=null;
		for(int i=0;i<picker.getWeaponList().length;i++)
		{
			if((picker.getWeaponList())[i] instanceof DesertEagle)
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
		return new ImageIcon("pic/showPic/picOnMap/desertEagle.jpg");
	}
	
	public String getName()
	{
		return getGunName();
	}

}
