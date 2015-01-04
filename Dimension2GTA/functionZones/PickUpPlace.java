package functionZones;

import gameDisplayProcessor.MainGameWindow;

import java.awt.*;

import javax.swing.*;

import basicConstruction.*;
import nameConstants.*;

import utilities.*;
import weapon.*;

public class PickUpPlace extends FunctionZone
{
	//for debug
	private final static boolean localDebug=true;
	//data field
	private Item item;  
	
	public PickUpPlace(Item item, MyPoint location)
	{
		super(new ImageIcon("pic/default1.jpg"),location,new Dimension(40,40),true,true);
	    this.item=item;
	    setSurfaceImage(item.getMapPic());
	}
	
	public PickUpPlace(int itemName,MyPoint location)
	{
		super(new ImageIcon("pic/default1.jpg"),location,new Dimension(20,20),true,true);
		
		switch (itemName) {
		case NameConstants.WEAPON_GUN_AK47: {
			item=new AK47(null);
			break;
		}
		case NameConstants.WEAPON_GUN_AUG: {
			item=new AUG(null);
			break;
		}
		case NameConstants.WEAPON_GUN_AWP: {
			item=new AWP(null);
			break;
		}
		case NameConstants.WEAPON_GUN_DESERT_EAGLE: {
			item=new DesertEagle(null);
			break;
		}
		case NameConstants.WEAPON_GUN_M249: {
			item=new M249(null);
			break;
		}
		case NameConstants.WEAPON_GUN_M4: {
			item=new M4(null);
			break;
		}
		case NameConstants.WEAPON_GUN_METAL_STORM: {
			item=new MetalStorm(null);
			break;
		}
		case NameConstants.WEAPON_GUN_MP5: {
			item=new MP5(null);
			break;
		}
		case NameConstants.WEAPON_GUN_SIG552: {
			item=new SIG552(null);
			break;
		}
		case NameConstants.WEAPON_HEGRENADE: {
			item=new Hegrenade(new MyPoint(-10,-10),new VectorClass(0,0));
			break;
		}
		case NameConstants.WEAPON_VEST: {
			item=new Vest(0.8-0.5*Math.random(),75);
			break;
		}
		case NameConstants.WEAPON_GUN_CHEAP_GUN: {
			item=new CheapGun(null);
			break;
		}
		
		case NameConstants.WEAPON_GUN_ROCKET_LAUNCHER: {
			item=new RocketLauncher(1);
			break;
		}
		case NameConstants.WEAPON_GUN_ROCKET: {
			item=new Rocket(new MyPoint(-10,-10),new VectorClass(0,0),0);
		    break;
		}
		
		case NameConstants.WEAPON_BULLET_ITEM: {
			item=new BulletItem();
		    break;
		}
		default:
		{
			if(localDebug)
			{
			    System.out.println("itemName == "+itemName);
			}
			break;
		}
	    }
		
		if(item!=null)
		{
	        setSurfaceImage(item.getMapPic());
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Error! PickUpPlace creating failure");
			setSurfaceImage(new ImageIcon("pic/default1.jpg"));
		}
	}

	@Override
	public void functionPerformed(Human whoTriggers, MainGameWindow master)
	{
		boolean succPickUp=false;
		if(whoTriggers instanceof Player)
		{
			succPickUp=item.PickUp(whoTriggers);
		}
		if(succPickUp)
		{
			master.getLandMapContainer().removeSolid(this);
		}
		
	}
	
}
