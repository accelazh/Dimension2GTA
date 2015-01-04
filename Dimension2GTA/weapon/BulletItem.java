package weapon;

import javax.swing.ImageIcon;

import basicConstruction.Human;
import utilities.*;
import javax.swing.*;
import gameDisplayProcessor.*;

public class BulletItem implements Item
{

	public boolean Buy(Human buyer) 
	{
		
		if((buyer.getWeaponList()[buyer.getCurrentWeapon()]) instanceof Gun)
		{
		    Gun gun=(Gun)(buyer.getWeaponList()[buyer.getCurrentWeapon()]);
		    
		    
		    if(buyer.getMoney()>=getPrice())
		    {
		    	gun.setNumOfLeft(gun.getNumOfLeft()+90);
		    	return true;
		    }
		    else
		    {
		    	JOptionPane.showMessageDialog(null,"Sorry, not enough money!");
		        return false;
 		    }
		
		}
		else
		{
			return false;
		}
		
	}

	 
	public String getInfo() 
	{
		return "    When buying, your current gun will get 90 bullets. " +
				"If you want to buy bullets for another gun, you can switch your current weapon, "
		+"either using mouse wheels or press \'"+ControlSetting.getSwitchWeapon()+"\'.";
	}

	 
	public ImageIcon getMapPic() 
	{
		
		return new ImageIcon("pic/showPic/picOnMap/bullet.jpg");
	}

	 
	public  String getName()
	{
		return "Bullet";
	}

	 
	public ImageIcon getPic() 
	{
		
		return new ImageIcon("pic/showPic/Bullet.jpg");
	}

	 
	public int getPrice() 
	{
		
		return 450;
	}

	 
	public boolean PickUp(Human picker) 
	{
		if((picker.getWeaponList()[picker.getCurrentWeapon()]) instanceof Gun)
		{
		    Gun gun=(Gun)(picker.getWeaponList()[picker.getCurrentWeapon()]);
		   
		    gun.setNumOfLeft(gun.getNumOfLeft()+30);
		   
		   return true;
		
		}
		else
		{
			return false;
		}
	}

}
