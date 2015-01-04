package gameDisplayProcessor;

import java.awt.Font;

import javax.swing.*;

import basicConstruction.*;

public class FMCLabel extends JLabel  //footMovingConditionLabel
{

	private Player player;
	public FMCLabel()
	{
		this(null);
	}
	public FMCLabel(Player player)
	{
		this.player=player;
		setFont(new Font("Times",Font.BOLD,24));
	}
	
	public void refresh()
	{
		if(player.getFootMovingCondition()==Human.WALK)
		{
			setText(" | FootMovingContidion : WALK");
			
		}
		if(player.getFootMovingCondition()==Human.RUN)
		{
			setText(" | FootMovingContidion : RUN");
			
		}
		if(player.getFootMovingCondition()==Human.SPRING)
		{
			setText(" | FootMovingContidion : SPRING");
			
		}
	}
	
	
}
