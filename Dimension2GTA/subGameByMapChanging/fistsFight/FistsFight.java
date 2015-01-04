package subGameByMapChanging.fistsFight;

import subGameByMapChangingSuper.*;
import basicConstruction.*;
import gameDisplayProcessor.*;
import utilities.ImageViewer;
import weapon.*;
import nameConstants.*;

import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

public class FistsFight extends SubGameByMapChanging {

	//for debug
	private boolean debug=true;
	
	public FistsFight(Player player,Human opponent,MainGameWindow master)
	{
		super(player,opponent,master);
	}
	
	@Override
	public void winByOpponent() {
		// TODO Auto-generated method stub

	}

	@Override
	public void winByPlayer() {
		getPlayer().setMoney(getPlayer().getMoney()+60+(int)(60*Math.random()));
		

	}

	@Override
	public void editWeaponList()
	{
	    getPlayer().setCurrentWeapon(0);
	    
	    getOpponent().setCurrentWeapon(0);
	}
}
