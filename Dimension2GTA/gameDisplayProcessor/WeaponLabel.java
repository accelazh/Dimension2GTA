package gameDisplayProcessor;

import javax.swing.*;
import java.awt.*;

import weapon.*;
import java.awt.*;
import basicConstruction.*;

public class WeaponLabel extends JLabel
{
	private Weapon weapon;
	private Player player;
	
	
	public WeaponLabel(Weapon weapon,Player player)
	{
		this.weapon=weapon;
		this.player=player;
		setFont(new Font("Times",Font.BOLD,24));
		setText("Weapon: none");
		
	}
	public WeaponLabel()
	{
		this(null,null);
	}

	public Weapon getWeapon() 
	{
		return weapon;
	}

	public void setWeapon(Weapon weapon) 
	{
		this.weapon = weapon;
	}
	
	public void refresh(Weapon weapon)
	{
		this.weapon=weapon;
		if(weapon instanceof Fists)
		{
			setText(" | Fists");
		}
		if(weapon instanceof Gun)
		{
			String text=" | ";
			text+=("Weapon: "+((Gun)weapon).getGunName()+" "+
					((Gun)weapon).getNumOfLoaded()+"/"+((Gun)weapon).getNumOfLeft());
			if(((Gun)weapon).getCurrentState()==Gun.RELOADING)
			{
				text+="  RELOADING";
			}
			if(((Gun)weapon).getCurrentState()==Gun.OUT_OF_BULLET)
			{
				text+="  OUT OF BULLET";
			}
			setText(text);
		}
		if(weapon instanceof HegrenadeBell)
		{
            String text=" | ";
            text+="Hegrenade";
            text+="  ";
            text+=player.getNumOfHegrenade();
            
            setText(text);
            
		}
		if(weapon instanceof RocketLauncher)
		{

            String text=" | ";
            text+="RocketLauncher";
            text+="  ";
            text+=((RocketLauncher)weapon).getNumOfRocket();
            
            setText(text);
		}
		repaint();
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	
	
	

}
