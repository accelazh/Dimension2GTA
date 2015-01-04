package subGameByMapChanging.gunFight;

import basicConstruction.*;
import gameDisplayProcessor.*;
import subGameByMapChangingSuper.*;

public class GunDuel extends SubGameByMapChanging
{
	
	
	public GunDuel(Player player,Human opponent,MainGameWindow master)
	{
		super(player,opponent,master);
	}
	public GunDuel(Player player,Human opponent,MainGameWindow master,int gunName)
	{
		super(player,opponent,master,gunName);
	}

	
	public void winByOpponent() 
	{
		// TODO Auto-generated method stub
		
	}

	
	public void winByPlayer() 
	{
		getPlayer().setMoney(getPlayer().getMoney()+100+(int)(60*Math.random()));
		
		
	}
	

}
