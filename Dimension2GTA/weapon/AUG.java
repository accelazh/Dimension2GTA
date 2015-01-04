package weapon;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import nameConstants.NameConstants;

import basicConstruction.*;

public class AUG extends Gun
{
	public AUG(Human owner)
	{
		super("AUG",owner,85,3.3,0.10,2.3,30,90,NameConstants.WEAPON_GUN_AUG);
	}

	@Override
	public boolean Buy(Human buyer)
	{
		boolean succBuy=true;
		//���buyer�Ƿ������ǹ
		boolean isHas=false;
		for(int i=0;i<buyer.getWeaponList().length;i++)
		{
			if((buyer.getWeaponList())[i] instanceof AUG)
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

	@Override
	public int getPrice() 
	{
		
		return 3800;
	}

	@Override
	public boolean PickUp(Human picker) 
	{
		//���buyer�Ƿ������ǹ
		boolean isHas=false;
		Gun gun=null;
		for(int i=0;i<picker.getWeaponList().length;i++)
		{
			if((picker.getWeaponList())[i] instanceof AUG)
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

	@Override
	public ImageIcon getMapPic() {
		// TODO Auto-generated method stub
		return new ImageIcon("pic/showPic/picOnMap/aug.jpg");
	}
	
	public String getName()
	{
		return getGunName();
	}
}
