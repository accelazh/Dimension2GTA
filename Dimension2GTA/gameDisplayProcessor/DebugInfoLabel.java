package gameDisplayProcessor;

import basicConstruction.*;
import javax.swing.*;


public class DebugInfoLabel extends JLabel
{
	private Player player;
	private MapContainer landMapContainer;
	
	public DebugInfoLabel(Player player,MapContainer landMapContainer)
	{
		this.player=player;
		this.landMapContainer=landMapContainer;
	}
	
	public DebugInfoLabel()
	{
		this(null,null);
		
	}
	
	public void refresh()
	{
		double arc=player.getFaceToArc();
		setText("|currentMapID: "+landMapContainer.getMapID());
	}
	
	public void setLandMapContainer(MapContainer landMapContainer)
	{
		this.landMapContainer=landMapContainer;
	}
	

}
