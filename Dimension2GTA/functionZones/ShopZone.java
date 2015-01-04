package functionZones;

import gameDisplayProcessor.MainGameWindow;

import java.awt.*;

import javax.swing.*;

import basicConstruction.*;
import shop.*;
import utilities.*;

public class ShopZone extends FunctionZone
{
	
	private Shop shop=null;
	private int shopType=-1;
	private int[] itemNames=null;
	private Item[] items=null;
	
	private int constructType=-1;
	private static final int SHOP_TYPE=0;
	private static final int ITEM_NAME=1;
	private static final int ITEMS=2;
	
	public ShopZone(MyPoint location, Dimension totalSize,int shopType)
	{
		super(new ImageIcon("pic/default1.jpg"),location,totalSize,false,true);
	    this.shopType=shopType;
	    constructType=SHOP_TYPE;
	}
	
	public ShopZone(MyPoint location, Dimension totalSize,int[] itemNames)
	{
		super(new ImageIcon("pic/default1.jpg"),location,totalSize,false,true);
	    this.itemNames=itemNames;
	    constructType=ITEM_NAME;
	}
	
	public ShopZone(MyPoint location, Dimension totalSize,Item[] items)
	{
		super(new ImageIcon("pic/default1.jpg"),location,totalSize,false,true);
	    this.items=items;
	    constructType=ITEMS;
	}

	@Override
	public void functionPerformed(Human whoTriggers, MainGameWindow master) 
	{
		if(whoTriggers instanceof Player)
		{
			
			Player player=(Player)whoTriggers;
			switch(constructType)
			{
			    case SHOP_TYPE:
			    {
			    	Shop shop=new Shop(player,master,shopType);
			    	break;
			    }
			    case ITEM_NAME:
			    {
			    	Shop shop=new Shop(player,master,itemNames);
			    	break;
			    }
			    case ITEMS:
			    {
			    	Shop shop=new Shop(player,master,items);
			    	break;
			    }
			    default :
			    {
			    	break;
			    }
			}
		}
		
	}

}
